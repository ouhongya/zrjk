package com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SHealth.Beans;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel(value = "健康文章对象")
@Data
public class Article {
    @ApiModelProperty(name = "id",value = "id")
    private String id;
    @ApiModelProperty(name = "name",value = "文章标题")
    private String name;
    @ApiModelProperty(name = "url",value = "文章缩略图url")
    private String url;
    @ApiModelProperty(name = "content",value = "文章内容")
    private String content;
    @ApiModelProperty(name = "healthThemeId",value = "文章所属主题id")
    private String healthThemeId;
    @ApiModelProperty(name = "healthThemeName",value = "文章所属主题名称")
    private String healthThemeName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createdTime;
    @JsonIgnore
    private Integer status;
    @ApiModelProperty(name = "state",value = "1上架   2下架")
    private Integer state;
    @ApiModelProperty(name = "doctorName",value = "医生名")
    private String doctorName;
    @ApiModelProperty(name = "department",value = "部门")
    private String department;


}
