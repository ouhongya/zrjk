package com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SAccount.Beans;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Set;

@ApiModel(value = "角色对象")
@Data
public class Role {
    @ApiModelProperty(name = "id",value = "id")
    private String id;
    @ApiModelProperty(name = "roleName",value = "角色名称")
    private String roleName;
    @ApiModelProperty(name = "functionIds",value = "功能id集合")
    private Set<String> functionIds;
    @ApiModelProperty(name = "buttonIds",value = "按钮id集合")
    private Set<String> buttonIds;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createdTime;
    @ApiModelProperty(name = "parentFunctions",value = "角色下的功能集合")
    private List<ParentFunction> parentFunctions;
}
