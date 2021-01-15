package com.offcn.user.controller;

import com.offcn.response.AppResponse;
import com.offcn.user.component.SmsTemplate;
import com.offcn.user.po.TMember;
import com.offcn.user.service.UserService;
import com.offcn.user.vo.req.UserRegistVo;
import com.offcn.user.vo.req.UserRespVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Api(tags = "用户登录/注册模块（包括忘记密码等）")
@Slf4j
public class UserLoginController {


    @Autowired
    private SmsTemplate smsTemplate;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private UserService userService;

    @ApiOperation("获取注册的验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phoneNo", value = "手机号", required = true)
    })//@ApiImplicitParams：描述所有参数；@ApiImplicitParam描述某个参数
    @PostMapping("/sendCode")
    public AppResponse<Object>sendCode(String phoneNo){
        String code = UUID.randomUUID().toString().substring(0,4);
        System.out.println(code);
        redisTemplate.opsForValue().set(phoneNo,code,60*5, TimeUnit.SECONDS);
        Map<String,String> querys =new HashMap<>();
        querys.put("param", "code:"+code);
        querys.put("mobile", phoneNo);
        querys.put("tpl_id", "TP1711063");
        String sendcode =smsTemplate.sendcode(querys);
        if (sendcode.equals("") || sendcode.equals("fail")){
            return AppResponse.fail("短信发送失败");
        }else {
            return AppResponse.ok(sendcode);
        }
    }

    @ApiOperation("用户注册")
    @PostMapping("/regist")
    public AppResponse<Object> regist(UserRegistVo registVo){
        //1、校验验证码
        String code =redisTemplate.opsForValue().get(registVo.getLoginacct());
        if(!StringUtils.isEmpty(code)){
            //redis中有验证码
            boolean b =code.equalsIgnoreCase(registVo.getCode());
            if (b){
                //2、将vo转业务能用的数据对象
                TMember member =new TMember();
                BeanUtils.copyProperties(registVo,member);
                //3、将用户信息注册到数据库
                try {
                    userService.registerUser(member);
                    log.debug("用户信息注册成功：{}", member.getLoginacct());
                    //4、注册成功后，删除验证码
                    redisTemplate.delete(registVo.getLoginacct());
                    return AppResponse.ok("注册成功...");
                }catch (Exception e){
                    log.error("用户信息注册失败：{}", member.getLoginacct());
                    return AppResponse.fail(e.getMessage());
                }
            }else {
                return AppResponse.fail("验证码错误");
            }
        }else {
            return AppResponse.fail("验证码过期请重新获取");
        }
    }

    @ApiOperation("用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true),
            @ApiImplicitParam(name = "password", value = "密码", required = true)
    })//@ApiImplicitParams：描述所有参数；@ApiImplicitParam描述某个参数
    @PostMapping("/login")
    public AppResponse<UserRespVo> login(String username , String password){
        //1、尝试登录
        TMember tMember =userService.login(username,password);
        if (tMember == null){
            //登陆失败
            AppResponse<UserRespVo> fail =AppResponse.fail(null);
            fail.setMessage("用户名或密码错误");
            return fail;
        }
        //2、登录成功;生成令牌
        String token =UUID.randomUUID().toString().replace("-","");
        UserRespVo vo =new UserRespVo();
        BeanUtils.copyProperties(tMember,vo);
        vo.setAccessToken(token);

        //3、经常根据令牌查询用户的id信息
        redisTemplate.opsForValue().set(token,tMember.getId()+"",2,TimeUnit.HOURS);
        return AppResponse.ok(vo);
    }


    @ApiOperation("获取会员信息")
    @GetMapping("/findUser/{id}")
    public AppResponse<UserRespVo> findUser(@PathVariable("id") Integer id){
        TMember member =userService.findTmemberById(id);
        UserRespVo vo =new UserRespVo();
        BeanUtils.copyProperties(member,vo);
        return AppResponse.ok(vo);
    }
}
