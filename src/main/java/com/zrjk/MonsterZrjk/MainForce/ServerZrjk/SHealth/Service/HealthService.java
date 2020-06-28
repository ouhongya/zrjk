package com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SHealth.Service;

import com.github.pagehelper.PageInfo;
import com.zrjk.MonsterZrjk.Common.Beans.ParamBean;
import com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SHealth.Beans.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface HealthService {
    boolean addThealthTheme(HealthTheme healthTheme) throws Exception;

    boolean updateHealthTheme(HealthTheme healthTheme) throws Exception;

    void deleteHealthTheme(String id) throws Exception;

    PageInfo<HealthTheme> findHealthTheme(ParamBean paramBean) throws Exception;

    boolean addArticle(Article article) throws Exception;

    boolean updateArtcle(Article article) throws Exception;

    void upArtcle(String id) throws Exception;

    void downArtcle(String id) throws Exception;

    boolean deleteArtcle(String id) throws Exception;

    PageInfo<Article> findArtcle(String id,ParamBean paramBean) throws Exception;

    boolean addVideo(Video video) throws Exception;

    void upVideo(String id) throws Exception;

    void downVideo(String id) throws Exception;

    boolean deleteVideo(String id) throws Exception;

    PageInfo<Video> findVideo(String healthThemeId, ParamBean paramBean) throws Exception;

    Article findArtcleById(String id) throws Exception;

    boolean updateVideo(Video video) throws Exception;

    Video findVideoById(String id) throws Exception;

    void addDoctor(Doctor doctor) throws Exception;

    void updateDoctor(Doctor doctor) throws Exception;

    void deleteDoctor(String id) throws Exception;

    PageInfo<Doctor> findDoctor(String healthThemeId, ParamBean paramBean) throws Exception;

    Doctor findDoctorById(String id) throws Exception;

    PageInfo<Questions> findQuestions(HttpServletRequest request, ParamBean paramBean) throws Exception;

    void answering(Answer answer) throws Exception;

    List<Answer> findAnswerByQid(String qid) throws Exception;

    void deleteAnswer(String id) throws Exception;

    void updateAnswer(Answer answer) throws Exception;

    Answer findAnswerById(String id) throws Exception;
}
