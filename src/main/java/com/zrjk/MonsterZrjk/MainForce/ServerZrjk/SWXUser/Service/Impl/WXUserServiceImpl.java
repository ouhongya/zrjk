package com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SWXUser.Service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zrjk.MonsterZrjk.Common.Beans.ParamBean;
import com.zrjk.MonsterZrjk.Common.Utils.UUIDGenerator;
import com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SHealth.Beans.Article;
import com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SHealth.Beans.Doctor;
import com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SHealth.Beans.HealthTheme;
import com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SHealth.Beans.Video;
import com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SWXUser.Beans.Column;
import com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SWXUser.Beans.User;
import com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SWXUser.Mapper.WXUserMapper;
import com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SWXUser.Service.WXUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class WXUserServiceImpl implements WXUserService {

    @Autowired
    private WXUserMapper wxUserMapper;


    /**
     * 查询微信用户列表
     * @param paramBean
     * @return
     */
    @Override
    public PageInfo<User> findWXUser(ParamBean paramBean) throws Exception {
       //分页排序
        PageHelper.startPage(paramBean.getPage(),paramBean.getSize(),paramBean.getSortField()+" "+paramBean.getSortWay());

        List<User> users=wxUserMapper.findWXUser(paramBean.getSearch());

        //查询用户列和列内容
        for (User user : users) {
            List<Column> columns=wxUserMapper.findColumnAndContentByUid(user.getId());
            user.setColumns(columns);
        }

        PageInfo<User> pageInfo = new PageInfo<>(users);

        return pageInfo;

    }


    /**
     * 查询用户关注的主题列表
     * @param uid
     * @return
     */
    @Override
    public PageInfo<HealthTheme> findFollowHealthTheme(String uid,ParamBean paramBean) throws Exception {
        //分页排序
        PageHelper.startPage(paramBean.getPage(),paramBean.getSize(),paramBean.getSortField()+" "+paramBean.getSortWay());
        List<HealthTheme> healthThemes=wxUserMapper.findFollowHealthThme(uid,paramBean.getSearch());
        PageInfo<HealthTheme> pageInfo = new PageInfo<>(healthThemes);
        return pageInfo;
    }

    /**
     * 查询用户关注的文章列表
     * @param uid
     * @param paramBean
     * @return
     */
    @Override
    public PageInfo<Article> findFollowArticle(String uid, ParamBean paramBean) throws Exception {
        //分页排序
        PageHelper.startPage(paramBean.getPage(),paramBean.getSize(),paramBean.getSortField()+ " "+paramBean.getSortWay());
        List<Article> articles=wxUserMapper.findFollowArticle(uid,paramBean.getSearch());
        PageInfo<Article> pageInfo = new PageInfo<>(articles);
        return pageInfo;
    }

    /**
     * 查询用户关注的视频列表
     * @param uid
     * @param paramBean
     * @return
     */
    @Override
    public PageInfo<Video> findFollowVideo(String uid, ParamBean paramBean) throws Exception {
        //分页 排序
        PageHelper.startPage(paramBean.getPage(),paramBean.getSize(),paramBean.getSortField()+" "+paramBean.getSortWay());
        List<Video> videos=wxUserMapper.findFollowVideo(uid,paramBean.getSearch());
        PageInfo<Video> pageInfo = new PageInfo<>(videos);
        return pageInfo;
    }

    /**
     * 查询用户关注的医生列表
     * @param uid
     * @param paramBean
     * @return
     */
    @Override
    public PageInfo<Doctor> findFollowDoctor(String uid, ParamBean paramBean) throws Exception {
        //分页 排序
        PageHelper.startPage(paramBean.getPage(),paramBean.getSize(),paramBean.getSortField()+" "+paramBean.getSortWay());
        List<Doctor> doctors=wxUserMapper.findFollowDoctor(uid,paramBean.getSearch());
        PageInfo<Doctor> pageInfo = new PageInfo<>(doctors);
        return pageInfo;
    }

    /**
     * 添加列
     * @param name
     * @return
     */
    @Override
    public boolean addColumn(String name) throws Exception {
        //查询列名是否重复
        Column column=wxUserMapper.findColumnByName(name);

        if(column==null){
            //添加列
            wxUserMapper.addColumn(UUIDGenerator.uuid(),name,new Date());
            return true;
        }else {
            return false;
        }
    }


    /**
     * 查询所有列
     * @return
     */
    @Override
    public List<Column> findAllColumn() throws Exception {
       return wxUserMapper.findAllColumn();
    }

    /**
     * 添加或编辑列内容
     * @param uid
     * @param cid
     * @param content
     */
    @Override
    public void addColumnContent(String uid, String cid, String content) throws Exception {
        //查询该用户在该列是否有内容
        String userContent=wxUserMapper.findColumnContent(uid,cid);

        if(userContent==null||"".equals(userContent)){
            //添加
            wxUserMapper.addColumnContent(UUIDGenerator.uuid(),uid,cid,content,new Date());
        }else {
            //编辑
            wxUserMapper.updateColumContent(uid,cid,content);
        }
    }

    /**
     * 删除列
     * @param cid
     */
    @Override
    public void deleteColumn(String cid) throws Exception {
        wxUserMapper.updateColumnStatus(cid,-1);
    }

    /**
     * 编辑列名
     * @param id
     * @param name
     * @return
     */
    @Override
    public boolean updateColumn(String id, String name) throws Exception {
        //查询该列名是否重复
        Column column = wxUserMapper.findColumnByName(name);
        if(column==null){
            //编辑
            wxUserMapper.updateColumn(id,name);
            return true;
        }else {
            return false;
        }
    }
}
