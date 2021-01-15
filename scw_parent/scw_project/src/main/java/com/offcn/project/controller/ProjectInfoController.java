package com.offcn.project.controller;

import com.offcn.project.po.*;
import com.offcn.project.service.ProjectInfoService;
import com.offcn.project.vo.req.ProjectVo;
import com.offcn.project.vo.resp.ProjectDetailVo;
import com.offcn.response.AppResponse;
import com.offcn.util.OssTemplate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags="项目基本功能模块（文件上传、项目信息获取等）")
@Slf4j
@RequestMapping("/project")
@RestController
public class ProjectInfoController {
    @Autowired
    private OssTemplate ossTemplate;

    @Autowired
    private ProjectInfoService projectInfoService;

    @ApiOperation("文件上传功能")
    @PostMapping("/upload")
    public AppResponse<Map<String,Object>> upload (@RequestParam("file")MultipartFile[] files) throws IOException {
        Map<String, Object> map = new HashMap<>();
        List<String> list = new ArrayList<>();
        if (files!=null && files.length>0){
            for (MultipartFile item:files){
                if (!item.isEmpty()){
                    String upload =ossTemplate.upload(item.getInputStream(),item.getOriginalFilename());
                    list.add(upload);
                }
            }
        }
        map.put("urls",list);
        log.debug("ossTemplate信息：{},文件上传成功访问路径{}",ossTemplate,list);
        return AppResponse.ok(map);
    }

    @ApiOperation("获取项目回报列表")
    @GetMapping("/details/returns/{projectId}")
    public AppResponse<List<TReturn>> getReturnList(@PathVariable("projectId") Integer projectId) {
        List<TReturn> returns =projectInfoService.getReturnList(projectId);
        return AppResponse.ok(returns);
    }

    @ApiOperation("获取系统所有的项目")
    @GetMapping("/all")
    public AppResponse<List<ProjectVo>> findAllProject() {
        // 1、创建集合存储全部项目的VO
        List<ProjectVo> projectVos =new ArrayList<>();
        // 2、查询全部项目
        List<TProject> projectList =projectInfoService.getAllProjects();
        //3、遍历项目集合
        for (TProject project :projectList){
            //获取项目编号
            Integer id =project.getId();
            //根据项目编号获取项目配图
            List<TProjectImages> tProjectImages =projectInfoService.getProjectImages(id);
            ProjectVo projectVo =new ProjectVo();
            BeanUtils.copyProperties(project,projectVo);
            //遍历项目配图集合
            for (TProjectImages projectImages :tProjectImages){
                //如果图片类型是头部图片，则设置头部图片路径到项目VO
                if (projectImages.getImgtype() == 0){
                    projectVo.setHeaderImage(projectImages.getImgurl());
                }
            }
            //把项目vo添加到项目vo集合
            projectVos.add(projectVo);
        }
        return AppResponse.ok(projectVos);
    }

    @ApiOperation("获取项目信息详情")
    @GetMapping("/findProjectInfo/{projectId}")
    public AppResponse<ProjectDetailVo> findProjectInfo(@PathVariable("projectId")Integer projectId){
        TProject projectInfo = projectInfoService.findProjectInfo(projectId);
        ProjectDetailVo projectDetailVo =new ProjectDetailVo();
        List<TProjectImages> projectImages =projectInfoService.getProjectImages(projectInfo.getId());
        List<String> detailImage =projectDetailVo.getDetailsImage();
        if (detailImage == null){
            detailImage = new ArrayList<>();
        }
        for (TProjectImages projectImages1 :projectImages){
            if (projectImages1.getImgtype() == 0){
                projectDetailVo.setHeaderImage(projectImages1.getImgurl());
            }else {
               detailImage.add(projectImages1.getImgurl());
            }
        }
        projectDetailVo.setDetailsImage(detailImage);
        // 2、项目的所有支持回报；
        List<TReturn> returnList = projectInfoService.getReturnList(projectInfo.getId());
        projectDetailVo.setProjectReturns(returnList);
        BeanUtils.copyProperties(projectInfo,projectDetailVo);
        return AppResponse.ok(projectDetailVo);
    }

    @ApiOperation("获取系统所有的项目标签")
    @GetMapping("/findAllTag")
    public AppResponse<List<TTag>> findAllTag() {
        List<TTag> tags = projectInfoService.findAllTag();
        return AppResponse.ok(tags);
    }

    @ApiOperation("获取系统所有的项目分类")
    @GetMapping("/findAllType")
    public AppResponse<List<TType>> findAllType() {
        List<TType> types = projectInfoService. findAllType();
        return AppResponse.ok(types);
    }

    @ApiOperation("获取回报信息")
    @GetMapping("/returns/info/{returnId}")
    public AppResponse<TReturn> findReturnInfo(@PathVariable("returnId") Integer returnId){
        TReturn tReturn = projectInfoService. findReturnInfo(returnId);
        return AppResponse.ok(tReturn);
    }
}
