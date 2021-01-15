package com.offcn.webui.service;

import com.offcn.response.AppResponse;
import com.offcn.webui.config.FeignConfig;
import com.offcn.webui.service.impl.ProjectServiceFeignException;
import com.offcn.webui.vo.resp.ProjectVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(value = "SCW-PROJECT",configuration = FeignConfig.class,fallback = ProjectServiceFeignException.class)
public interface ProjectServiceFeign {

    @GetMapping("/project/all")
    public AppResponse<List<ProjectVo>> all();
}
