package com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SFile.Beans;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "接收不到集合封装的对象")
public class ListBean {
    @ApiModelProperty(name = "ids",value = "需要删除的文件或文件夹id集合")
    private List<String> ids;
    @ApiModelProperty(name = "state",value = "要删除的属性类型  1文件夹  2文件")
    private Integer state;
}
