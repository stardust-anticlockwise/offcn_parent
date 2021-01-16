package com.offcn.webui.controller;


import com.offcn.response.AppResponse;
import com.offcn.webui.service.ProjectServiceFeign;
import com.offcn.webui.vo.resp.ProjectDetailVo;
import com.offcn.webui.vo.resp.ReturnPayConfirmVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    private ProjectServiceFeign projectServiceFeign;

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
}
