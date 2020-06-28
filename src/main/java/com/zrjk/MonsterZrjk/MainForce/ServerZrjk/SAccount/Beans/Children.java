package com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SAccount.Beans;

import lombok.Data;

import java.util.List;

@Data
public class Children {
    private String path;

    private String name;

    private String component;

    private Meta meta;

    private List<String> buttonAlias;
}
