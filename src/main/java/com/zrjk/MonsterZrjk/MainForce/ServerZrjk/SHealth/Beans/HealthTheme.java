package com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SHealth.Beans;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(value = "健康主题对象")
public class HealthTheme {
    @ApiModelProperty(name = "id",value = "id")
    private String id;
    @ApiModelProperty(name = "name",value = "主题名称")
    private String name;
    @ApiModelProperty(name = "url",value = "缩略图url")
    private String url;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createdTime;
}
