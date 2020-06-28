package com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SHealth.Beans;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(value = "视频对象")
public class Video {
    @ApiModelProperty(name = "id",value = "id")
    private String id;
    @ApiModelProperty(name = "name",value = "视频标题")
    private String name;
    @ApiModelProperty(name = "url",value = "视频地址")
    private String url;
    @ApiModelProperty(name = "introduce",value = "视频简介")
    private String introduce;
    @ApiModelProperty(name = "healthThemeId",value = "视频所属主题id")
    private String healthThemeId;
    @ApiModelProperty(name = "healthThemeName",value = "视频所属主题名称")
    private String healthThemeName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:ss:mm",timezone = "GMT+8")
    private Date createdTime;
    @JsonIgnore
    private Integer status;
    @ApiModelProperty(name = "state",value = "1上架   2下架")
    private Integer state;
    @ApiModelProperty(name = "doctorName",value = "医生名")
    private String doctorName;
    @ApiModelProperty(name = "department",value = "部门")
    private String department;
    @ApiModelProperty(name = "coverUrl",value = "视频第一帧封面")
    private String coverUrl;
}
