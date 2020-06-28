package com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SDatacenter.Beans;

import com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SHealth.Beans.Doctor;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "统计医生对象")
public class DataDoctor {
    @ApiModelProperty(name = "doctor",value = "医生对象")
    private Doctor doctor;
    @ApiModelProperty(name = "num",value = "关注数")
    private int num;
}
