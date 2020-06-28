package com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SAccount.Beans;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@ApiModel(value = "功能对象")
@Data
public class Function {
    @ApiModelProperty(name = "id",value = "id")
    private String id;
    @ApiModelProperty(name = "functionName",value = "功能名称")
    private String functionName;
    @JsonIgnore
    @ApiModelProperty(name = "functionUrl",value = "功能url")
    private String functionUrl;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createdTime;
    @ApiModelProperty(name = "buttons",value = "按钮集合")
    private List<Button> buttons;
}
