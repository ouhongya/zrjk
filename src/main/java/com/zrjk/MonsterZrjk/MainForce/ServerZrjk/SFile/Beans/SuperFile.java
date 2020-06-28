package com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SFile.Beans;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "文件+文件夹")
public class SuperFile {
    private List<Folder> folders;
    private List<File> files;
}
