package com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SAccount.Beans;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "登录成功之后返回")
public class LoginAccount {
    @ApiModelProperty(name = "id",value = "id")
    private String id;
    @ApiModelProperty(name = "trueName",value = "姓名")
    private String trueName;
    @ApiModelProperty(name = "phoneNum",value = "电话")
    private String phoneNum;
    @ApiModelProperty(name = "roleName",value = "角色名")
    private String roleName;
    @ApiModelProperty(name = "token",value = "token")
    private String token;
}
