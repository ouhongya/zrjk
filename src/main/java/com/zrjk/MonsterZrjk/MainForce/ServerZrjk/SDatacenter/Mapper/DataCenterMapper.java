package com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SDatacenter.Mapper;

import com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SDatacenter.Beans.DataArticle;
import com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SDatacenter.Beans.DataDoctor;
import com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SDatacenter.Beans.DataHealthTheme;
import com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SDatacenter.Beans.DataVideo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DataCenterMapper {
    List<DataDoctor> findDataDoctor(@Param("search") String search);

    List<DataArticle> findDataArticle(@Param("search") String search);

    List<DataVideo> findDataVideo(@Param("search") String search);

    List<DataHealthTheme> findDataHealthTheme(@Param("search") String search);
}
