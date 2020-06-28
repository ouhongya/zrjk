package com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SHealth.Beans;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;

@Data
@ApiModel(value = "回答对象")
public class Answer {
    @ApiModelProperty(name = "id",value = "id")
    private String id;
    @ApiModelProperty(name = "answersContent",value = "回答内容")
    private String answersContent;
    @ApiModelProperty(name = "questionsId",value = "问题id")
    private String questionsId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createdTime;
}
