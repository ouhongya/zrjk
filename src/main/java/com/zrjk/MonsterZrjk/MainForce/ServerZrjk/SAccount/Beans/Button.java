package com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SAccount.Beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "按钮对象")
@Data
public class Button {
    @ApiModelProperty(name = "id",value = "id")
    private String id;
    @ApiModelProperty(name = "name",value = "按钮名称")
    private String name;
    @ApiModelProperty(name = "alias",value = "按钮别名")
    private String alias;
    @JsonIgnore
    @ApiModelProperty(name = "url",value = "按钮url")
    private String url;
}
