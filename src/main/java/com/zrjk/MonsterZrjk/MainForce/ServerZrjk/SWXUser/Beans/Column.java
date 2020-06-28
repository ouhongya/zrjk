package com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SWXUser.Beans;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(value = "列对象")
public class Column {
    @ApiModelProperty(name = "id",value = "id")
    private String id;
    @ApiModelProperty(name = "name",value = "列名")
    private String name;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createdTime;
    @ApiModelProperty(name = "content",value = "内容")
    private String content;
}
