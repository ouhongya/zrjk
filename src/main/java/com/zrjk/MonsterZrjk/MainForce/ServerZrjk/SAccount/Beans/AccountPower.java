package com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SAccount.Beans;

import lombok.Data;

import java.util.List;

@Data
public class AccountPower {

    private String name;

    private String component = "Layout";

    private String redirect="";

    private String path;

    private Boolean alwaysShow = true;

    private Meta meta;

    private List<Children> childrens;
}
