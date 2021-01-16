package com.offcn.webui.controller;

import com.offcn.response.AppResponse;
import com.offcn.webui.service.MemberServiceFeign;
import com.offcn.webui.service.ProjectServiceFeign;
import com.offcn.webui.vo.resp.ProjectVo;
import com.offcn.webui.vo.resp.UserRespVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Controller
public class DispathcherController {

    @Autowired
    private MemberServiceFeign memberServiceFeign;

    @Autowired
    private ProjectServiceFeign projectServiceFeign;

    @Autowired
    private RedisTemplate redisTemplate;


    @RequestMapping("/doLogin")
    public String doLogin(String loginacct, String userpswd, HttpSession session) {
        System.out.println("登陆账号："+loginacct+"密码为："+userpswd);
        //处理登录业务: 远程调用scw-user的映射方法
        AppResponse<UserRespVo> login =memberServiceFeign.login(loginacct,userpswd);
        UserRespVo data =login.getData();
        System.out.println("数据为:"+data);
        if (data == null){
            return "/login";
        }
        //登录成功, 将userVo对象共享到session中
        session.setAttribute("sessionMember",data);
        String preUrl =(String)session.getAttribute("preUrl");
        if (StringUtils.isEmpty(preUrl)){
            return "redirect:/";
        }
        return "redirect:/" + preUrl;
    }

    @RequestMapping("/")
    public String toIndex(Model model) {
        List<ProjectVo> data= (List<ProjectVo>) redisTemplate.opsForValue().get("projectStr");
        if (data == null){
            AppResponse<List<ProjectVo>> allProject =projectServiceFeign.all();
            data = allProject.getData();
            redisTemplate.opsForValue().set("projectStr",data,1, TimeUnit.HOURS);
        }
        System.out.println(data);
        model.addAttribute("projectList",data);
        return "index";
    }
}
