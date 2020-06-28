package com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SFile.Beans;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(value = "日志对象")
public class Journal {
    @ApiModelProperty(name = "id",value = "id")
    private String id;
    @ApiModelProperty(name = "userId",value = "用户id")
    private String userId;
    @ApiModelProperty(name = "username",value = "用户名")
    private String username;
    @ApiModelProperty(name = "trueName",value = "用户真名")
    private String trueName;
    @ApiModelProperty(name = "types",value = "1上传   2下载   3创建文件夹  4删除文件   5删除文件夹")
    private Integer types;
    @ApiModelProperty(name = "fid",value = "文件或者文件夹id")
    private String fid;
    @ApiModelProperty(name = "fName",value = "文件或者文件夹名称")
    private String fName;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date createdTime;

}
