package com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SAccount.Beans;

import com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SHealth.Beans.Doctor;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel(value = "一级功能对象")
@Data
public class ParentFunction {
    @ApiModelProperty(name = "pfName",value = "一级功能名称")
    private String pfName;
    @ApiModelProperty(name = "functions",value = "二级功能集合")
    private List<Function> functions;
    @ApiModelProperty(name = "doctors",value = "医生集合")
    private List<Doctor> doctors;
}
