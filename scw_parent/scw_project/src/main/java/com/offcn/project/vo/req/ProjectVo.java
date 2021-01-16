package com.offcn.project.vo.req;

import com.offcn.vo.BaseVoNoToken;
import lombok.Data;


@Data
public class ProjectVo extends BaseVoNoToken {

// 会员id
         private Integer memberid;

         private Integer id;
 // 项目名称
         private String name;
 // 项目简介
         private String remark;
 // 项目头部图片
         private String headerImage;

}