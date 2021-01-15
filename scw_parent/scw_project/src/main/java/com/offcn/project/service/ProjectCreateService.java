package com.offcn.project.service;

import com.offcn.enums.ProjectStatusEnume;
import com.offcn.project.vo.req.ProjectRedisStorageVo;

public interface ProjectCreateService {
    String initCreateProject(Integer memberId);

    public void saveProjectInfo(ProjectStatusEnume auth, ProjectRedisStorageVo project);
}
