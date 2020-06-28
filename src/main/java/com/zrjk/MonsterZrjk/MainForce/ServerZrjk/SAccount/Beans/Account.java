package com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SAccount.Beans;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@ApiModel(value = "后台账户对象")
@Data
public class Account {
    @ApiModelProperty(name = "id",value = "id")
    private String id;
    @ApiModelProperty(name = "username",value = "用户名")
    private String username;
    @ApiModelProperty(name = "password",value = "密码")
    private String password;
    @ApiModelProperty(name = "trueName",value = "真实姓名")
    private String trueName;
    @ApiModelProperty(name = "phoneNum",value = "电话号码")
    private String phoneNum;
    @ApiModelProperty(name = "roleId",value = "角色id")
    private String roleId;
    @ApiModelProperty(name = "roleName",value = "角色名称")
    private String roleName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createdTime;
    @ApiModelProperty(name = "buttonIds",value = "按钮id集合-添加")
    private List<String> buttonIds;
    @ApiModelProperty(name = "functionIds",value = "功能id集合")
    private List<String> functionIds;
    @ApiModelProperty(name = "doctorIds",value = "医生id集合")
    private List<String> doctorIds;



    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    @JsonIgnore
    public List<String> getButtonIds() {
        return buttonIds;
    }

    @JsonProperty
    public void setButtonIds(List<String> buttonIds) {
        this.buttonIds = buttonIds;
    }

    @JsonIgnore
    public List<String> getFunctionIds() {
        return functionIds;
    }

    @JsonProperty
    public void setFunctionIds(List<String> functionIds) {
        this.functionIds = functionIds;
    }
}
