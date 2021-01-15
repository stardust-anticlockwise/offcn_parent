package com.offcn.project.service.impl;

import com.offcn.project.mapper.*;
import com.offcn.project.po.*;
import com.offcn.project.service.ProjectInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProjectInfoServiceImpl implements ProjectInfoService {

    @Autowired
    TReturnMapper tReturnMapper;

    @Autowired
    TProjectMapper projectMapper;

    @Autowired
    TProjectImagesMapper projectImagesMapper;

    @Autowired
    TTagMapper tagMapper;

    @Autowired
    TTypeMapper tTypeMapper;

    @Override
    public List<TReturn> getReturnList(Integer projectId) {
        TReturnExample example =new TReturnExample();
        example.createCriteria().andProjectidEqualTo(projectId);
        return tReturnMapper.selectByExample(example);
    }

    @Override
    public List<TProject> getAllProjects() {
        return projectMapper.selectByExample(null);
    }

    @Override
    public List<TProjectImages> getProjectImages(Integer id) {
        TProjectImagesExample example =new TProjectImagesExample();
        TProjectImagesExample.Criteria criteria = example.createCriteria();
        criteria.andProjectidEqualTo(id);
        List<TProjectImages> tProjectImages = projectImagesMapper.selectByExample(example);
        return tProjectImages;
    }

    @Override
    public TProject findProjectInfo(Integer projectId) {
        TProject project =projectMapper.selectByPrimaryKey(projectId);
        return project;
    }

    @Override
    public List<TTag> findAllTag() {
        return tagMapper.selectByExample(null);
    }

    @Override
    public List<TType> findAllType() {
        return tTypeMapper.selectByExample(null);
    }

    @Override
    public TReturn findReturnInfo(Integer returnId) {
        return tReturnMapper.selectByPrimaryKey(returnId);
    }


}
