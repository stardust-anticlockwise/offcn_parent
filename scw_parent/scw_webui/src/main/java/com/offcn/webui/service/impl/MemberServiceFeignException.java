package com.offcn.webui.service.impl;

import com.offcn.response.AppResponse;
import com.offcn.webui.service.MemberServiceFeign;
import com.offcn.webui.vo.resp.UserRespVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MemberServiceFeignException implements MemberServiceFeign {
    @Override
    public AppResponse<UserRespVo> login(String loginacct, String password) {
        AppResponse<UserRespVo> fail =AppResponse.fail(null);
        fail.setMessage("调用远程服务器失败【登录】");
        return fail;
    }
}
