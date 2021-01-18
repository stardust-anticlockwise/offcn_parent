package com.offcn.webui.vo.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel
@Data
public class UserAddressVo implements Serializable {

    @ApiModelProperty("地址id")
    private Integer id = 1;

    @ApiModelProperty("会员地址")
    private String address = "朝阳门外大街31号";
}