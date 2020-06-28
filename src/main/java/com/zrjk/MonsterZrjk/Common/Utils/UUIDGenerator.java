package com.zrjk.MonsterZrjk.Common.Utils;

import java.util.UUID;

public class UUIDGenerator {
    /**
     * UUID生成
     * @return
     */
    public static String uuid(){
        return UUID.randomUUID().toString().toUpperCase().replace("-","");
    }
}
