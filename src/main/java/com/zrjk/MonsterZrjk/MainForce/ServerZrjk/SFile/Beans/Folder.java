package com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SFile.Beans;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@ApiModel(value = "文件夹对象")
public class Folder {
    @ApiModelProperty(name = "id",value = "id")
    private String id;
    @ApiModelProperty(name = "folderName",value = "文件夹名称")
    private String folderName;
    @ApiModelProperty(name = "parentId",value = "父文件夹id")
    private String parentId;
    @ApiModelProperty(name = "parentName",value = "父目录名称")
    private String parentName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createdTime;
    @ApiModelProperty(name = "isDelete",value = "能否删除  true能  false不能")
    private Boolean isDelete=false;
}
