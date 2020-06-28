package com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SHealth.Beans;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
@ApiModel(value = "问题对象")
public class Questions {
    @ApiModelProperty(name = "id",value = "id")
    private String id;
    @ApiModelProperty(name = "questionsContent",value = "问题内容")
    private String questionsContent;
    @ApiModelProperty(name = "healthThemeName",value = "主题名称")
    private String healthThemeName;
    @ApiModelProperty(name = "doctorName",value = "医生名称")
    private String doctorName;
    @ApiModelProperty(name = "npm ",value = "提问者的昵称")
    private String nickName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createdTime;
    @ApiModelProperty(name = "answers",value = "问题答案集合")
    private List<Answer> answers;
}
