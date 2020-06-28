package com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SWXUser.Mapper;

import com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SHealth.Beans.Article;
import com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SHealth.Beans.Doctor;
import com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SHealth.Beans.HealthTheme;
import com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SHealth.Beans.Video;
import com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SWXUser.Beans.Column;
import com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SWXUser.Beans.User;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface WXUserMapper {
    List<User> findWXUser(@Param("search") String search);

    List<HealthTheme> findFollowHealthThme(@Param("uid") String uid, @Param("search") String search);

    List<Article> findFollowArticle(@Param("uid") String uid,@Param("search") String search);

    List<Video> findFollowVideo(@Param("uid") String uid,@Param("search") String search);

    List<Doctor> findFollowDoctor(@Param("uid") String uid,@Param("search") String search);

    Column findColumnByName(String name);

    void addColumn(@Param("id") String uuid,@Param("name") String name,@Param("date") Date date);

    List<Column> findAllColumn();

    String findColumnContent(@Param("uid") String uid,@Param("cid") String cid);

    void addColumnContent(@Param("id") String uuid,@Param("uid") String uid,@Param("cid") String cid,@Param("content") String content,@Param("date") Date date);

    void updateColumContent(@Param("uid") String uid,@Param("cid") String cid,@Param("content") String content);

    List<Column> findColumnAndContentByUid(@Param("uid") String id);

    void updateColumnStatus(@Param("cid") String cid,@Param("status") int i);

    void updateColumn(@Param("id") String id,@Param("name") String name);
}
