package com.zrjk.MonsterZrjk.Common.Beans;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("通用辅助参数对象")
public class ParamBean {
    @ApiModelProperty(name = "page",value = "当前页")
    private int page;
    @ApiModelProperty(name = "size",value = "每页显示记录数")
    private int size;
    @ApiModelProperty(name = "search",value = "搜索条件")
    private String search;
    //排序字段
    @ApiModelProperty(name = "sortField",value = "排序字段")
    private String  sortField;
    //排序方式
    @ApiModelProperty(name = "sortWay",value = "排序方式")
    private String sortWay;
}
