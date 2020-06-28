package com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SWXUser.Beans;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@ApiModel("WX用户对象-后台")
@Data
public class User {
    @ApiModelProperty(name = "id",value = "id")
    private String id;
    @ApiModelProperty(name = "nickName",value = "用户昵称")
    private String nickName;
    @ApiModelProperty(name = "coverUrl",value = "头像URL")
    private String coverUrl;
    @ApiModelProperty(name = "wxKey",value = "微信KEY")
    private String wxKey;
    @ApiModelProperty(name = "phoneNum",value = "电话号码")
    private String phoneNum;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createdTime;
    @ApiModelProperty(name = "columns",value = "列对象集合")
    private List<Column> columns;
}
