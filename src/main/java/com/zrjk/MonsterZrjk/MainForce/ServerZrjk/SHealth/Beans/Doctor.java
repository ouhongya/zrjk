package com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SHealth.Beans;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(value = "医生对象")
public class Doctor {
    @ApiModelProperty(name = "id",value = "id")
    private String id;
    @ApiModelProperty(name = "name",value = "医生名")
    private String name;
    @ApiModelProperty(name = "url",value = "头像url")
    private String url;
    @ApiModelProperty(name = "hospitalName",value = "医院名")
    private String hospitalName;
    @ApiModelProperty(name = "adeptDisease",value = "擅长主治")
    private String adeptDisease;
    @ApiModelProperty(name = "introduce",value = "个人简介")
    private String introduce;
    @ApiModelProperty(name = "address",value = "医院地址")
    private String address;
    @ApiModelProperty(name = "healthThemeId",value = "所属主题id")
    private String healthThemeId;
    @ApiModelProperty(name = "healthThemeName",value = "所属主题名称")
    private String healthThemeName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createdTime;
}
