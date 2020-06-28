package com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SDatacenter.Beans;

import com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SHealth.Beans.Article;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "统计文章对象")
public class DataArticle {
    @ApiModelProperty(name = "article",value = "文章对象")
    private Article article;
    @ApiModelProperty(name = "num",value = "关注数")
    private int num;
}
