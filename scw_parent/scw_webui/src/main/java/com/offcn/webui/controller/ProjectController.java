package com.offcn.webui.controller;


import com.offcn.response.AppResponse;
import com.offcn.webui.service.MemberServiceFeign;
import com.offcn.webui.service.ProjectServiceFeign;
import com.offcn.webui.vo.resp.ProjectDetailVo;
import com.offcn.webui.vo.resp.ReturnPayConfirmVo;
import com.offcn.webui.vo.resp.UserAddressVo;
import com.offcn.webui.vo.resp.UserRespVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    private ProjectServiceFeign projectServiceFeign;

    @Autowired
    private MemberServiceFeign memberServiceFeign;

    @RequestMapping("/projectInfo")
    public String index(Integer id, Model model, HttpSession session){
        AppResponse<ProjectDetailVo> vo =projectServiceFeign.detailInfo(id);
        ProjectDetailVo data =vo.getData();
        model.addAttribute("DetailVo",data);
        session.setAttribute("DetailVo",data);
        return "project/project";
    }

    @RequestMapping("/confirm/returns/{projectId}/{returnId}")
    public String returnInfo(
            @PathVariable("projectId") Integer projectId,
            @PathVariable("returnId") Integer returnId,
            Model model, HttpSession session) {
        ProjectDetailVo projectDetailVo = (ProjectDetailVo) session.getAttribute("DetailVo");

        AppResponse<ReturnPayConfirmVo> returnInfo =projectServiceFeign.returnInfo(returnId);

        ReturnPayConfirmVo data =returnInfo.getData();
        data.setProjectId(projectId);
        data.setProjectName(projectDetailVo.getName());
        model.addAttribute("returnConfirm",data);
        session.setAttribute("returnConfirm",data);
        return "project/pay-step-1";
    }


    @GetMapping("/confirm/order/{num}")
    public String confirmOrder(@PathVariable("num") Integer num,Model model, HttpSession session){
        UserRespVo data = (UserRespVo) session.getAttribute("sessionMember");
        if(data == null){
            session.setAttribute("preUrl","project/confirm/order/"+num);
            return "redirect:/login.html";
        }
        String accessToken =data.getAccessToken();
        AppResponse<List<UserAddressVo>> addressResponse =memberServiceFeign.address(accessToken);
        List<UserAddressVo> addressVos = addressResponse.getData();
        model.addAttribute("addresses",addressVos);
        ReturnPayConfirmVo vo = (ReturnPayConfirmVo) session.getAttribute("returnConfirm");
        vo.setNum(num);
        vo.setTotalPrice(new BigDecimal(num * vo.getSupportmoney() + vo.getFreight()));
        session.setAttribute("returnConfirmSession",vo);
        return "project/pay-step-2";
    }
}
