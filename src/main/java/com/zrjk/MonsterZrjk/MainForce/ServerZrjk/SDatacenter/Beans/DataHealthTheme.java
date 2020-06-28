package com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SDatacenter.Beans;

import com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SHealth.Beans.HealthTheme;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "统计主题对象")
public class DataHealthTheme {
    @ApiModelProperty(name = "healthTheme",value = "主题对象")
    private HealthTheme healthTheme;
    @ApiModelProperty(name = "num",value = "关注数")
    private int num;
}
