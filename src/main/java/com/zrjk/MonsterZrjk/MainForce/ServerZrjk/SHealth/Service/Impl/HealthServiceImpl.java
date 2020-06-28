package com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SHealth.Service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zrjk.MonsterZrjk.Common.Beans.ParamBean;
import com.zrjk.MonsterZrjk.Common.Utils.TokenUtil;
import com.zrjk.MonsterZrjk.Common.Utils.UUIDGenerator;
import com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SAccount.Mapper.AccountMapper;
import com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SHealth.Beans.*;
import com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SHealth.Mapper.HealthMapper;
import com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SHealth.Service.HealthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class HealthServiceImpl implements HealthService {

    @Autowired
    private HealthMapper healthMapper;

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private AccountMapper accountMapper;

    /**
     * 添加健康主题
     * @param healthTheme
     * @return
     */
    @Override
    public boolean addThealthTheme(HealthTheme healthTheme) throws Exception {
        //查询该名称是否存在
        HealthTheme healthTheme1=healthMapper.findHealthThemeByName(healthTheme.getName());
        if(healthTheme1!=null){
            return false;
        }
        //添加健康主题
        healthTheme.setId(UUIDGenerator.uuid());
        healthTheme.setCreatedTime(new Date());
        healthMapper.addThealthTheme(healthTheme);
        return true;
    }


    /**
     * 编辑健康主题
     * @param healthTheme
     * @return
     */
    @Override
    public boolean updateHealthTheme(HealthTheme healthTheme) throws Exception {
        //查询主题名称是否存在
        HealthTheme healthTheme1 = healthMapper.findHealthThemeByName(healthTheme.getName());
        if(healthTheme1!=null&&!healthTheme.getId().equals(healthTheme1.getId())){
            return false;
        }
        //编辑健康主题
        healthMapper.updateHealthTheme(healthTheme);

        return true;
    }

    /**
     * 健康主题删除
     * @param id
     */
    @Override
    public void deleteHealthTheme(String id) throws Exception {
        //删除主题
        healthMapper.updateHealthThemeStatus(id,-1);
    }

    /**
     * 查询健康主题列表
     * @param paramBean
     * @return
     */
    @Override
    public PageInfo<HealthTheme> findHealthTheme(ParamBean paramBean) throws Exception {
        //分页  排序
        PageHelper.startPage(paramBean.getPage(),paramBean.getSize(),paramBean.getSortField()+" "+paramBean.getSortWay());
        List<HealthTheme> healthThemes= healthMapper.findAllHealthTheme(paramBean.getSearch());
        PageInfo<HealthTheme> pageInfo = new PageInfo<>(healthThemes);
        return pageInfo;
    }


    /**
     * 添加文章
     * @param article
     * @return
     */
    @Override
    public boolean addArticle(Article article) throws Exception {
        //查询文章标题是否存在
        Article article1=healthMapper.findArtcleByName(article.getName());
        if(article1!=null){
            return false;
        }
        //添加文章
        article.setId(UUIDGenerator.uuid());
        article.setCreatedTime(new Date());
        healthMapper.addArtcle(article);
        return true;
    }


    /**
     * 编辑文章
     * @param article
     * @return
     */
    @Override
    public boolean updateArtcle(Article article) throws Exception {
       //查询文章名称是否存在
        Article article1 = healthMapper.findArtcleByName(article.getName());
        if(article1!=null&&!article1.getId().equals(article.getId())){
            return false;
        }
        //编辑文章
        healthMapper.updateArtcle(article);
        return true;
    }


    /**
     * 文章上架
     * @param id
     */
    @Override
    public void upArtcle(String id) throws Exception {
        healthMapper.updateArtcleStatus(id,1);
    }

    /**
     * 文章下架
     * @param id
     */
    @Override
    public void downArtcle(String id) throws Exception {
        healthMapper.updateArtcleStatus(id,0);
    }


    /**
     * 文章删除
     * @param id
     * @return
     */
    @Override
    public boolean deleteArtcle(String id) throws Exception {
        int i = healthMapper.deleteArtcle(id);

        if(i==0){
            return false;
        }else {
            return true;
        }
    }

    /**
     * 通过主题id查询文章基本信息
     * @param id
     * @return
     */
    @Override
    public PageInfo<Article> findArtcle(String id,ParamBean paramBean) throws Exception {
        List<Article> articles;
        //分页 排序
        PageHelper.startPage(paramBean.getPage(),paramBean.getSize(),paramBean.getSortField()+" "+paramBean.getSortWay());
        if("default".equals(id)){
            //查询所有
             articles=healthMapper.findAllArtcle(paramBean.getSearch());
        }else {
             articles=healthMapper.findArtcle(id,paramBean.getSearch());
        }

        //设置该文章上下架状态
        for (Article article : articles) {
            if(article.getStatus()==1){
                article.setState(1);
            }
            if(article.getStatus()==0){
                article.setState(2);
            }
        }
        PageInfo<Article> pageInfo = new PageInfo<>(articles);
        return pageInfo;
    }

    /**
     * 添加主题视频
     * @param video
     * @return
     */
    @Override
    public boolean addVideo(Video video) throws Exception {
       //查询该视频标题是否存在
        Video video1=healthMapper.findVideoByName(video.getName());
        if(video1!=null){
            return false;
        }
        //添加视频
        video.setId(UUIDGenerator.uuid());
        video.setCreatedTime(new Date());
        healthMapper.addVideo(video);
        return true;
    }

    /**
     * 视频上架
     * @param id
     */
    @Override
    public void upVideo(String id) throws Exception {
        healthMapper.updateVideoStatus(id,1);
    }

    /**
     * 视频下架
     * @param id
     */
    @Override
    public void downVideo(String id) throws Exception {
        healthMapper.updateVideoStatus(id,0);
    }


    /**
     * 视频删除
     * @param id
     * @return
     */
    @Override
    public boolean deleteVideo(String id) throws Exception {
        int state=healthMapper.deleteVideo(id);
        if(state==1){
            return true;
        }
        return false;
    }

    /**
     * 通过主题id查询视频
     * @param healthThemeId
     * @param paramBean
     * @return
     */
    @Override
    public PageInfo<Video> findVideo(String healthThemeId, ParamBean paramBean) throws Exception {
        List<Video> videos;
        //分页 排序
        PageHelper.startPage(paramBean.getPage(),paramBean.getSize(),paramBean.getSortField()+" "+paramBean.getSortWay());
        if("default".equals(healthThemeId)){
            //查询所有
           videos=healthMapper.findAllVideo(paramBean.getSearch());
        }else {
           videos=healthMapper.findVideo(healthThemeId,paramBean.getSearch());
        }

        //设置该视频上下架状态
        for (Video video : videos) {
            if(video.getStatus()==1){
                video.setState(1);
            }
            if(video.getStatus()==0){
                video.setState(2);
            }
        }
        PageInfo<Video> pageInfo = new PageInfo<>(videos);
        return pageInfo;
    }


    /**
     * 通过id查询文章详细信息
     * @return
     */
    @Override
    public Article findArtcleById(String id) throws Exception{
        Article artcle = healthMapper.findArtcleById(id);
        if(artcle.getStatus()==1){
            artcle.setState(1);
        }
        if(artcle.getStatus()==0){
            artcle.setState(2);
        }
        return artcle;
    }

    /**
     * 编辑视频
     * @param video
     * @return
     */
    @Override
    public boolean updateVideo(Video video) throws Exception {
        //查询视频名称是否存在
        Video video1 = healthMapper.findVideoByName(video.getName());
        if(video1!=null&&!video1.getId().equals(video.getId())){
            return false;
        }
        //编辑视频
        healthMapper.updateVideo(video);
        return true;
    }

    /**
     * 通过id查询视频信息
     * @param id
     * @return
     */
    @Override
    public Video findVideoById(String id) throws Exception {
        Video video=healthMapper.findVideoById(id);
        //设置该视频上下架状态
        if(video.getStatus()==1){
            video.setState(1);
        }
        if(video.getStatus()==0){
            video.setState(2);
        }
        return video;
    }


    /**
     * 添加医生
     * @param doctor
     */
    @Override
    public void addDoctor(Doctor doctor) throws Exception {
        //添加医生
        doctor.setId(UUIDGenerator.uuid());
        doctor.setCreatedTime(new Date());
        healthMapper.addDoctor(doctor);
    }


    /**
     * 编辑医生
     * @param doctor
     */
    @Override
    public void updateDoctor(Doctor doctor) throws Exception {
        //编辑医生
        healthMapper.updateDoctor(doctor);
    }

    /**
     * 删除医生
     * @param id
     */
    @Override
    public void deleteDoctor(String id) throws Exception {
        //删除医生
        healthMapper.updateDoctorStatus(id,-1);
    }

    /**
     * 查询医生列表
     * @param healthThemeId
     * @param paramBean
     * @return
     */
    @Override
    public PageInfo<Doctor> findDoctor(String healthThemeId, ParamBean paramBean) throws Exception {
        List<Doctor> doctors;
        //分页  排序
        PageHelper.startPage(paramBean.getPage(),paramBean.getSize(),paramBean.getSortField()+" "+paramBean.getSortWay());
        if("default".equals(healthThemeId)){
            //查询所有
            doctors=healthMapper.findAllDoctor(paramBean.getSearch());
        }else {
            //通过主题id查询
            doctors=healthMapper.findDoctor(healthThemeId,paramBean.getSearch());
        }

        PageInfo<Doctor> pageInfo = new PageInfo<>(doctors);
        return pageInfo;
    }


    /**
     * 通过医生id查询医生详细信息
     * @param id
     * @return
     */
    @Override
    public Doctor findDoctorById(String id) throws Exception {
       return healthMapper.findDoctorById(id);
    }

    /**
     * 查询需要回答的问题
     * @return
     */
    @Override
    public PageInfo<Questions> findQuestions(HttpServletRequest request, ParamBean paramBean) throws Exception {
        //在request获取cookie
        String token=null;
        for (Cookie cookie : request.getCookies()) {
            if("token".equals(cookie.getName())){
                token=cookie.getValue();
                break;
            }
        }
        //从token中获取uid
        if(token!=null){
            String uid = tokenUtil.getUidFromToken(token);
            //分页  排序
            PageHelper.startPage(paramBean.getPage(),paramBean.getSize(),paramBean.getSortField()+" "+paramBean.getSortWay());
            List<Questions> questions=healthMapper.findQuestions(uid,paramBean.getSearch());
            //查询回答
            for (Questions question : questions) {
                List<Answer> answers = healthMapper.findAnswerByQid(question.getId());
                question.setAnswers(answers);
            }
            PageInfo<Questions> pageInfo = new PageInfo<>(questions);
            return pageInfo;
        }else {
            return null;
        }
    }

    /**
     * 回答问题
     * @param answer
     */
    @Override
    public void answering(Answer answer) throws Exception {
        //添加回答数据
        answer.setId(UUIDGenerator.uuid());
        answer.setCreatedTime(new Date());
        healthMapper.addAnswer(answer);
    }

    /**
     * 通过问题id查询答案
     * @param qid
     * @return
     */
    @Override
    public List<Answer> findAnswerByQid(String qid) throws Exception {
        List<Answer> answers=healthMapper.findAnswerByQid(qid);
        return answers;
    }

    /**
     * 删除回答
     * @param id
     */
    @Override
    public void deleteAnswer(String id) throws Exception {
        healthMapper.updateAnswerStatus(id,-1);
    }

    /**
     * 编辑回答
     * @param answer
     */
    @Override
    public void updateAnswer(Answer answer) throws Exception {
        healthMapper.updateAnswer(answer);
    }

    /**
     * 通过回答id查询回答
     * @param id
     * @return
     */
    @Override
    public Answer findAnswerById(String id) throws Exception {
       return healthMapper.findAnswerById(id);
    }
}
