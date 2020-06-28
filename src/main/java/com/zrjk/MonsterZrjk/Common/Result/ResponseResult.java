package com.zrjk.MonsterZrjk.Common.Result;

import lombok.Data;

@Data
public class ResponseResult {
    private CommonCode queue;
    private Object data;

    public ResponseResult(CommonCode queue, Object data) {
        this.queue = queue;
        this.data = data;
    }

    public ResponseResult(CommonCode queue) {
        this.queue = queue;
    }
}
