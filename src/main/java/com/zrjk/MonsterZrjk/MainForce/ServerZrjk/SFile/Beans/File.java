package com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SFile.Beans;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel(value = "文件对象")
@Data
public class File {
    @ApiModelProperty(name = "id",value = "id")
    private String id;
    @ApiModelProperty(name = "fileName",value = "文件名称")
    private String fileName;
    @JsonIgnore
    @ApiModelProperty(name = "lookFileName",value = "图片显示的文件名称")
    private String lookFileName;
    @ApiModelProperty(name = "folderId",value = "文件夹id")
    private String folderId;
    @ApiModelProperty(name = "lookUrl",value = "用于页面显示的url")
    private String lookUrl;
    @ApiModelProperty(name = "downUrl",value = "用于下载的url")
    private String downUrl;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createdTime;
}
