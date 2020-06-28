package com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SHealth.Mapper;

import com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SHealth.Beans.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HealthMapper {
    HealthTheme findHealthThemeByName(String name);

    void addThealthTheme(HealthTheme healthTheme);

    void updateHealthTheme(HealthTheme healthTheme);

    void updateHealthThemeStatus(@Param("id") String id,@Param("status") int i);

    List<HealthTheme> findAllHealthTheme(@Param("search") String search);

    Article findArtcleByName(String name);

    void addArtcle(Article article);

    void updateArtcle(Article article);

    int updateArtcleStatus(@Param("id") String id,@Param("status") int i);

    List<Article> findArtcle(@Param("healthThemeId") String id, @Param("search") String search);

    Video findVideoByName(String name);

    void addVideo(Video video);

    void updateVideoStatus(@Param("id") String id,@Param("status") int i);

    int deleteVideo(String id);

    int deleteArtcle(String id);

    List<Video> findVideo(@Param("healthThemeId") String healthThemeId, @Param("search") String search);

    Article findArtcleById(String id);

    void updateVideo(Video video);

    Video findVideoById(String id);

    List<Article> findAllArtcle(@Param("search") String search);

    List<Video> findAllVideo(@Param("search")String search);

    void addDoctor(Doctor doctor);

    void updateDoctor(Doctor doctor);

    void updateDoctorStatus(@Param("id") String id,@Param("status") int i);

    List<Doctor> findAllDoctor(@Param("search") String search);

    List<Doctor> findDoctor(@Param("healthThemeId") String healthThemeId,@Param("search") String search);

    Doctor findDoctorById(String id);

    List<Questions> findQuestions(@Param("uid") String uid,@Param("search") String search);

    void addAnswer(Answer answer);

    List<Answer> findAnswerByQid(String qid);

    void updateAnswerStatus(@Param("id") String id,@Param("status") int i);

    void updateAnswer(Answer answer);

    Answer findAnswerById(String id);
}
