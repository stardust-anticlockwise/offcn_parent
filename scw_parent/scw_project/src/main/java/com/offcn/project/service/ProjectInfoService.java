package com.offcn.project.service;

import com.offcn.project.po.*;

import java.util.List;

public interface ProjectInfoService {

    List<TReturn> getReturnList(Integer projectId);

    List<TProject> getAllProjects();

    List<TProjectImages> getProjectImages(Integer id);

    TProject findProjectInfo(Integer projectId);

    List<TTag> findAllTag ();

    List<TType> findAllType();

    TReturn findReturnInfo(Integer returnId);
}
