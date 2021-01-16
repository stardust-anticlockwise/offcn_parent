package com.offcn.webui.service.impl;


import com.offcn.response.AppResponse;
import com.offcn.webui.service.ProjectServiceFeign;
import com.offcn.webui.vo.resp.ProjectDetailVo;
import com.offcn.webui.vo.resp.ProjectVo;
import com.offcn.webui.vo.resp.ReturnPayConfirmVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class ProjectServiceFeignException implements ProjectServiceFeign {
    @Override
    public AppResponse<List<ProjectVo>> all() {
        AppResponse<List<ProjectVo>> response = AppResponse.fail(null);
        response.setMessage("远程调用失败【查询首页热点项目】");
        return response;
    }

    @Override
    public AppResponse<ProjectDetailVo> detailInfo(Integer projectId) {
        AppResponse<ProjectDetailVo> response = AppResponse.fail(null);
        response.setMessage("远程调用失败【查询项目详情】");
        return response;
    }

    @Override
    public AppResponse<ReturnPayConfirmVo> returnInfo(Integer returnId) {
        AppResponse<ReturnPayConfirmVo> response = AppResponse.fail(null);
        response.setMessage("远程调用失败【项目回报】");
        return response;
    }
}