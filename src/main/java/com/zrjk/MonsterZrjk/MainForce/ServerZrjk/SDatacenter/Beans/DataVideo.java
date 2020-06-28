package com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SDatacenter.Beans;

import com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SHealth.Beans.Video;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "统计视频对象")
public class DataVideo {
    @ApiModelProperty(name = "video",value = "视频对象")
    private Video video;
    @ApiModelProperty(name = "num",value = "关注数")
    private int num;
}
