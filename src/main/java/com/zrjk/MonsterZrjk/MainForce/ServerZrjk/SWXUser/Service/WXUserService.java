package com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SWXUser.Service;

import com.github.pagehelper.PageInfo;
import com.zrjk.MonsterZrjk.Common.Beans.ParamBean;
import com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SHealth.Beans.Article;
import com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SHealth.Beans.Doctor;
import com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SHealth.Beans.HealthTheme;
import com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SHealth.Beans.Video;
import com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SWXUser.Beans.Column;
import com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SWXUser.Beans.User;

import java.util.List;

public interface WXUserService {
    PageInfo<User> findWXUser(ParamBean paramBean) throws Exception;

    PageInfo<HealthTheme> findFollowHealthTheme(String uid, ParamBean paramBean) throws Exception;

    PageInfo<Article> findFollowArticle(String uid, ParamBean paramBean) throws Exception;

    PageInfo<Video> findFollowVideo(String uid, ParamBean paramBean) throws Exception;

    PageInfo<Doctor> findFollowDoctor(String uid, ParamBean paramBean) throws Exception;

    boolean addColumn(String name) throws Exception;

    List<Column> findAllColumn() throws Exception;

    void addColumnContent(String uid, String cid, String content) throws Exception;

    void deleteColumn(String cid) throws Exception;

    boolean updateColumn(String id, String name) throws Exception;
}
