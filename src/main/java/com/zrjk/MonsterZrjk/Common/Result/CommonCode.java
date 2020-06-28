package com.zrjk.MonsterZrjk.Common.Result;


import com.fasterxml.jackson.annotation.JsonFormat;


@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum CommonCode {
    SUCCESS(1,"操作成功"),
    SYSTEMBUSY(2,"系统繁忙，请稍后再试。"),
    ROLE_NAME_REPEAT(5,"角色名称重复！"),
    CANNOT_DELETE_ACCOUNT(6,"无法删除账户，因为该账户绑定了权限！"),
    CANNOT_DELETE_ROLE(7,"无法删除角色,因为该角色绑定了账户！"),
    PHONE_NUM_REPEAT(8,"该手机号已被绑定！"),
    USERNAME_REPEAT(9,"该用户名已存在！"),
    PHONE_NUM_ERROR(10,"请输入正确的手机号"),
    FILE_NAME_REPEAT(3,"文件名称已存在！"),
    FOLDER_NAME_REPEAT(4,"文件夹名称已存在！"),
    INVAlLID_NAME(11,"文件或文件夹名称中不能包含 /"),
    THEME_NAME_REPEAT(12,"主题名称已存在！"),
    ARTCLE_NAME_REPEAT(13,"文章标题已存在！"),
    PLEASE_SIGNDOWN_ARTCLE(14,"请先下架该文章，再进行删除！"),
    VIDEO_NAME_REPEAT(15,"该视频标题已存在！"),
    PLEASE_SIGNDOWN_VIDEO(16,"请先下架该视频，再进行删除！"),
    COLUMN_NAME_REPEAT(17,"该列名已存在！")

    ;

    private Integer code;
    private String message;


    CommonCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
