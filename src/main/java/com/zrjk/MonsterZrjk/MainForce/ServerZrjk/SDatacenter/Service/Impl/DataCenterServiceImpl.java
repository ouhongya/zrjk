package com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SDatacenter.Service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zrjk.MonsterZrjk.Common.Beans.ParamBean;
import com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SDatacenter.Beans.DataArticle;
import com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SDatacenter.Beans.DataDoctor;
import com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SDatacenter.Beans.DataHealthTheme;
import com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SDatacenter.Beans.DataVideo;
import com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SDatacenter.Mapper.DataCenterMapper;
import com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SDatacenter.Service.DataCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DataCenterServiceImpl implements DataCenterService {
    @Autowired
    private DataCenterMapper dataCenterMapper;


    /**
     * 统计医生列表
     * @param paramBean
     * @return
     */
    @Override
    public PageInfo<DataDoctor> findDoctor(ParamBean paramBean) throws Exception {
       //分页
        PageHelper.startPage(paramBean.getPage(),paramBean.getSize());
        List<DataDoctor> dataDoctors = dataCenterMapper.findDataDoctor(paramBean.getSearch());
        PageInfo<DataDoctor> pageInfo = new PageInfo<>(dataDoctors);
        return pageInfo;
    }

    /**
     * 统计文章列表
     * @param paramBean
     * @return
     */
    @Override
    public PageInfo<DataArticle> findArticle(ParamBean paramBean) throws Exception {
        //分页
        PageHelper.startPage(paramBean.getPage(),paramBean.getSize());
        List<DataArticle> dataArticles=dataCenterMapper.findDataArticle(paramBean.getSearch());
        //设置文章上下架状态
        for (DataArticle dataArticle : dataArticles) {
            if(dataArticle.getArticle().getStatus()==1){
                dataArticle.getArticle().setState(1);
            }
            if(dataArticle.getArticle().getStatus()==0){
                dataArticle.getArticle().setState(2);
            }
        }
        PageInfo<DataArticle> pageInfo = new PageInfo<>(dataArticles);
        return pageInfo;
    }

    /**
     * 统计文章列表
     * @param paramBean
     * @return
     */
    @Override
    public PageInfo<DataVideo> findVideo(ParamBean paramBean) throws Exception {
        //分页
        PageHelper.startPage(paramBean.getPage(),paramBean.getSize());
        List<DataVideo> dataVideos=dataCenterMapper.findDataVideo(paramBean.getSearch());
        //设置视频上下架状态
        for (DataVideo dataVideo : dataVideos) {
            if(dataVideo.getVideo().getStatus()==1){
                dataVideo.getVideo().setState(1);
            }
            if(dataVideo.getVideo().getStatus()==0){
                dataVideo.getVideo().setState(2);
            }
        }
        PageInfo<DataVideo> pageInfo = new PageInfo<>(dataVideos);
        return pageInfo;
    }

    /**
     * 统计主题列表
     * @param paramBean
     * @return
     */
    @Override
    public PageInfo<DataHealthTheme> findHealthTheme(ParamBean paramBean) throws Exception {
        //分页
        PageHelper.startPage(paramBean.getPage(),paramBean.getSize());
        List<DataHealthTheme> dataHealthThemes=dataCenterMapper.findDataHealthTheme(paramBean.getSearch());
        PageInfo<DataHealthTheme> pageInfo = new PageInfo<>(dataHealthThemes);
        return pageInfo;
    }
}
