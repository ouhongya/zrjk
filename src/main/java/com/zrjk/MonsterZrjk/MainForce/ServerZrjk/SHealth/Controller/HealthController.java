package com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SHealth.Controller;


import com.github.pagehelper.PageInfo;
import com.zrjk.MonsterZrjk.Common.Beans.ParamBean;
import com.zrjk.MonsterZrjk.Common.Result.CommonCode;
import com.zrjk.MonsterZrjk.Common.Result.ResponseResult;
import com.zrjk.MonsterZrjk.Common.Utils.FileUpLoad;
import com.zrjk.MonsterZrjk.Common.Utils.UUIDGenerator;
import com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SHealth.Beans.*;
import com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SHealth.Service.HealthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/server/health")
@Api(tags = "健康主题-后台")
@Log4j2
public class HealthController {

    private SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private HealthService healthService;

    @Autowired
    private FileUpLoad fileUpLoad;

    @ApiOperation(value = "添加健康主题",httpMethod = "POST")
    @PostMapping(value = "/addHealthTheme")
    public ResponseResult addHealthTheme(@RequestBody HealthTheme healthTheme){
        try {
                if(healthService.addThealthTheme(healthTheme)){
                    return new ResponseResult(CommonCode.SUCCESS);
                }else {
                    return new ResponseResult(CommonCode.THEME_NAME_REPEAT);
                }
        }catch (Exception e){
            log.error("添加健康主题异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new ResponseResult(CommonCode.SYSTEMBUSY);
        }
    }

    @ApiOperation(value = "编辑健康主题",httpMethod = "POST")
    @PostMapping(value = "/updateHealthTheme")
    public ResponseResult updateHealthTheme(@RequestBody HealthTheme healthTheme){
        try {
            if(healthService.updateHealthTheme(healthTheme)){
                return new ResponseResult(CommonCode.SUCCESS);
            }else {
                return new ResponseResult(CommonCode.THEME_NAME_REPEAT);
            }
        }catch (Exception e){
            log.error("编辑健康主题异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new ResponseResult(CommonCode.SYSTEMBUSY);
        }
    }

    @ApiOperation(value = "删除健康主题",httpMethod = "GET")
    @ApiImplicitParam(name = "id",value = "主题id")
    @GetMapping("/deleteHealthTheme")
    public ResponseResult deleteHealthTheme(String id){
        try {
            healthService.deleteHealthTheme(id);
            return new ResponseResult(CommonCode.SUCCESS);
        }catch (Exception e){
            log.error("删除健康主题");
            log.error(e);
            log.error(sf.format(new Date()));
            return new ResponseResult(CommonCode.SYSTEMBUSY);
        }
    }

    @ApiOperation(value = "查询健康主题列表",httpMethod = "GET")
    @GetMapping(value = "/findHealthTheme")
    public ResponseResult findHealthTheme(ParamBean paramBean){
        try {
            PageInfo<HealthTheme> pageInfo=healthService.findHealthTheme(paramBean);
            return new ResponseResult(CommonCode.SUCCESS,pageInfo);
        }catch (Exception e){
            log.error("查询健康主题列表异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new ResponseResult(CommonCode.SYSTEMBUSY);
        }
    }

    @ApiOperation(value = "为健康主题添加文章",httpMethod = "POST")
    @PostMapping(value = "/addArticle")
    public ResponseResult addArticle(@RequestBody Article article){
        try {
            if(healthService.addArticle(article)){
                return new ResponseResult(CommonCode.SUCCESS);
            }else {
                return new ResponseResult(CommonCode.ARTCLE_NAME_REPEAT);
            }
        }catch (Exception e){
            log.error("为健康主题添加文章异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new ResponseResult(CommonCode.SYSTEMBUSY);
        }
    }

    @ApiOperation(value = "编辑文章",httpMethod = "POST")
    @PostMapping(value = "/updateArtcle")
    public ResponseResult updateArtcle(@RequestBody Article article){
        try {
            if(healthService.updateArtcle(article)){
                return new ResponseResult(CommonCode.SUCCESS);
            }else {
                return new ResponseResult(CommonCode.ARTCLE_NAME_REPEAT);
            }
        }catch (Exception e){
            log.error("编辑文章异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new ResponseResult(CommonCode.SYSTEMBUSY);
        }
    }

    @ApiOperation(value = "文章上架",httpMethod = "GET")
    @ApiImplicitParam(name = "id",value = "文章id")
    @GetMapping(value = "/upArtcle")
    public ResponseResult upArtcle(String id){
        try {
            healthService.upArtcle(id);
            return new ResponseResult(CommonCode.SUCCESS);
        }catch (Exception e){
            log.error("文章上架异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new ResponseResult(CommonCode.SYSTEMBUSY);
        }
    }


    @ApiOperation(value = "文章下架",httpMethod = "GET")
    @ApiImplicitParam(name = "id",value = "文章id")
    @GetMapping(value = "/downArtcle")
    public ResponseResult downArtcle(String id){
        try {
            healthService.downArtcle(id);
            return new ResponseResult(CommonCode.SUCCESS);
        }catch (Exception e){
            log.error("文章下架异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new ResponseResult(CommonCode.SYSTEMBUSY);
        }
    }

    @ApiOperation(value = "文章删除",httpMethod = "GET")
    @ApiImplicitParam(name = "id",value = "文章id")
    @GetMapping(value = "/deleteArtcle")
    public ResponseResult deleteArtcle(String id){
        try {
           if(healthService.deleteArtcle(id)){
               return new ResponseResult(CommonCode.SUCCESS);
           }else {
               return new ResponseResult(CommonCode.PLEASE_SIGNDOWN_ARTCLE);
           }
        }catch (Exception e){
            log.error("文章删除异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new ResponseResult(CommonCode.SYSTEMBUSY);
        }
    }

    @ApiOperation(value = "通过主题id查询文章基本信息",httpMethod = "GET")
    @ApiImplicitParam(name = "id",value = "健康主题id")
    @GetMapping(value = "/findArtcle")
    public ResponseResult findArtcle(String id,ParamBean paramBean){
        try {
            PageInfo<Article> pageInfo=healthService.findArtcle(id,paramBean);
            return new ResponseResult(CommonCode.SUCCESS,pageInfo);
        }catch (Exception e){
            log.error("通过主题id查询文章基本信息异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new ResponseResult(CommonCode.SYSTEMBUSY);
        }
    }

    @ApiOperation(value = "通过id查询文章详细信息",httpMethod = "GET")
    @ApiImplicitParam(name = "id",value = "文章id")
    @GetMapping(value = "/findArtcleById")
    public ResponseResult findArtcleById(String id){
        try {
           Article article= healthService.findArtcleById(id);
           return new ResponseResult(CommonCode.SUCCESS,article);
        }catch (Exception e){
            log.error("通过id查询文章详细信息异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new ResponseResult(CommonCode.SYSTEMBUSY);
        }
    }

    @ApiOperation(value = "上传视频/图片",httpMethod = "POST")
    @PostMapping(value = "/videoUpload")
    public ResponseResult videoUpLoad(@RequestParam MultipartFile multipartFile){
        try {
            String look="inline;filename=";
            String url = fileUpLoad.upload(multipartFile.getInputStream(), UUIDGenerator.uuid()+multipartFile.getOriginalFilename(), null, look, 2);
            if(!"滚".equals(url)){
                return new ResponseResult(CommonCode.SUCCESS,url);
            }else {
                return new ResponseResult(CommonCode.FILE_NAME_REPEAT);
            }
        }catch (Exception e){
            log.error("上传视频异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new ResponseResult(CommonCode.SYSTEMBUSY);
        }
    }

    @ApiOperation(value = "添加主题视频",httpMethod = "POST")
    @PostMapping(value = "/addVideo")
    public ResponseResult addVideo(@RequestBody Video video){
        try {
            if(healthService.addVideo(video)){
                return new ResponseResult(CommonCode.SUCCESS);
            }else {
                return new ResponseResult(CommonCode.VIDEO_NAME_REPEAT);
            }
        }catch (Exception e){
            log.error("添加主题视频异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new ResponseResult(CommonCode.SYSTEMBUSY);
        }
    }

    @ApiOperation(value = "视频上架",httpMethod = "GET")
    @ApiImplicitParam(name = "id",value = "视频id")
    @GetMapping(value = "/upVideo")
    public ResponseResult upVideo(String id){
        try {
            healthService.upVideo(id);
            return new ResponseResult(CommonCode.SUCCESS);
        }catch (Exception e){
            log.error("视频上架异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new ResponseResult(CommonCode.SYSTEMBUSY);
        }
    }

    @ApiOperation(value = "视频下架",httpMethod = "GET")
    @ApiImplicitParam(name = "id",value = "视频id")
    @GetMapping(value = "/downVideo")
    public ResponseResult downVideo(String id){
        try {
            healthService.downVideo(id);
            return new ResponseResult(CommonCode.SUCCESS);
        }catch (Exception e){
            log.error("视频下架异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new ResponseResult(CommonCode.SYSTEMBUSY);
        }
    }

    @ApiOperation(value = "视频删除",httpMethod = "GET")
    @ApiImplicitParam(name = "id",value = "视频id")
    @GetMapping(value = "/deleteVideo")
    public ResponseResult deleteVideo(String id){
        try {
            if(healthService.deleteVideo(id)){
                return new ResponseResult(CommonCode.SUCCESS);
            }else {
                return new ResponseResult(CommonCode.PLEASE_SIGNDOWN_VIDEO);
            }
        }catch (Exception e){
            log.error("视频删除异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new ResponseResult(CommonCode.SYSTEMBUSY);
        }
    }

    @ApiOperation(value = "通过主题id查询视频",httpMethod = "GET")
    @ApiImplicitParam(name = "healthThemeId",value = "主题id")
    @GetMapping(value = "/findVideo")
    public ResponseResult findVideo(String healthThemeId,ParamBean paramBean){
        try {
            PageInfo<Video> pageInfo=healthService.findVideo(healthThemeId,paramBean);
            return new ResponseResult(CommonCode.SUCCESS,pageInfo);
        }catch (Exception e){
         log.error("通过主题id查询视频异常");
         log.error("e");
         log.error(sf.format(new Date()));
         return new ResponseResult(CommonCode.SYSTEMBUSY);
        }
    }
    @ApiOperation(value = "编辑视频",httpMethod = "POST")
    @PostMapping(value = "/updateVideo")
    public ResponseResult updateVideo(@RequestBody Video video){
        try {
            if(healthService.updateVideo(video)){
                return new ResponseResult(CommonCode.SUCCESS);
            }else {
                return new ResponseResult(CommonCode.VIDEO_NAME_REPEAT);
            }
        }catch (Exception e){
            log.error("编辑视频");
            log.error(e);
            log.error(sf.format(new Date()));
            return new ResponseResult(CommonCode.SYSTEMBUSY);
        }
    }

    @ApiOperation(value = "通过视频id查询视频信息",httpMethod = "GET")
    @ApiImplicitParam(name = "id",value = "视频id")
    @GetMapping(value = "/findVideoById")
    public ResponseResult findVideoById(String id){
        try {
            Video video=healthService.findVideoById(id);
            return new ResponseResult(CommonCode.SUCCESS,video);
        }catch (Exception e){
            log.error("通过视频id查询视频信息异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new ResponseResult(CommonCode.SYSTEMBUSY);
        }
    }

    @ApiOperation(value = "添加医生",httpMethod = "POST")
    @PostMapping(value = "/addDoctor")
    public ResponseResult addDoctor(@RequestBody Doctor doctor){
        try {
            healthService.addDoctor(doctor);
            return new ResponseResult(CommonCode.SUCCESS);
        }catch (Exception e){
            log.error("添加医生异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new ResponseResult(CommonCode.SYSTEMBUSY);
        }
    }

    @ApiOperation(value = "编辑医生",httpMethod = "POST")
    @PostMapping(value = "/updateDoctor")
    public ResponseResult updateDoctor(@RequestBody Doctor doctor){
        try {
            healthService.updateDoctor(doctor);
            return new ResponseResult(CommonCode.SUCCESS);
        }catch (Exception e){
            log.error("编辑医生异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new ResponseResult(CommonCode.SYSTEMBUSY);
        }
    }

    @ApiOperation(value = "删除医生",httpMethod = "GET")
    @ApiImplicitParam(name = "id",value = "医生id")
    @GetMapping(value = "/deleteDoctor")
    public ResponseResult deleteDoctor(String id){
        try {
            healthService.deleteDoctor(id);
            return new ResponseResult(CommonCode.SUCCESS);
        }catch (Exception e){
            log.error("删除医生异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new ResponseResult(CommonCode.SYSTEMBUSY);
        }
    }

    @ApiOperation(value = "查询医生列表",httpMethod = "GET")
    @ApiImplicitParam(name = "healthThemeId",value = "健康主题id  如果为default 查询所有医生")
    @GetMapping(value = "/findDoctor")
    public ResponseResult findDoctor(String healthThemeId,ParamBean paramBean){
        try {
            PageInfo<Doctor> pageInfo=healthService.findDoctor(healthThemeId,paramBean);
            return new ResponseResult(CommonCode.SUCCESS,pageInfo);
        }catch (Exception e){
            log.error("查询医生列表异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new ResponseResult(CommonCode.SYSTEMBUSY);
        }
    }

    @ApiOperation(value = "通过医生id查询医生详细信息",httpMethod = "GET")
    @ApiImplicitParam(name = "id",value = "医生id")
    @GetMapping(value = "/findDoctorById")
    public ResponseResult findDoctorById(String id){
        try {
           Doctor doctor= healthService.findDoctorById(id);
           return new ResponseResult(CommonCode.SUCCESS,doctor);
        }catch (Exception e){
            log.error("通过医生id查询医生详细信息异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new ResponseResult(CommonCode.SYSTEMBUSY);
        }
    }

    @ApiOperation(value = "查询需要回答的问题",httpMethod = "POST",notes = "dids集合中必须有值  随便写死两个进去就行")
    @PostMapping(value = "/findQuestions")
    public ResponseResult findQuestions(HttpServletRequest request,ParamBean paramBean){
        try {
            PageInfo<Questions> pageInfo=healthService.findQuestions(request,paramBean);
            return new ResponseResult(CommonCode.SUCCESS,pageInfo);
        }catch (Exception e){
            log.error("查询需要回答的问题异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new ResponseResult(CommonCode.SYSTEMBUSY);
        }
    }

    @ApiOperation(value = "回答问题",httpMethod = "POST")
    @PostMapping(value = "/answering")
    public ResponseResult answering(@RequestBody Answer answer){
        try {
            healthService.answering(answer);
            return new ResponseResult(CommonCode.SUCCESS);
        }catch (Exception e){
            log.error("回答问题异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new ResponseResult(CommonCode.SYSTEMBUSY);
        }
    }

    @ApiOperation(value = "通过问题id查询回答",httpMethod = "GET")
    @ApiImplicitParam(name = "qid",value = "问题id")
    @GetMapping(value = "/findAnswerByQid")
    public ResponseResult findAnswerByQid(String qid){
        try {
            List<Answer> answers=healthService.findAnswerByQid(qid);
            return new ResponseResult(CommonCode.SUCCESS,answers);
        }catch (Exception e){
            log.error("通过问题id查询回答异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new ResponseResult(CommonCode.SYSTEMBUSY);
        }
    }

    @ApiOperation(value = "删除回答",httpMethod = "GET")
    @ApiImplicitParam(name = "id",value = "回答id")
    @GetMapping(value = "/deleteAnswer")
    public ResponseResult deleteAnswer(String id){
        try {
            healthService.deleteAnswer(id);
            return new ResponseResult(CommonCode.SUCCESS);
        }catch (Exception e){
            log.error("删除回答");
            log.error(e);
            log.error(sf.format(new Date()));
            return new ResponseResult(CommonCode.SYSTEMBUSY);
        }
    }


    @ApiOperation(value = "编辑回答",httpMethod = "POST")
    @PostMapping(value = "updateAnswer")
    public ResponseResult updateAnswer(@RequestBody Answer answer){
        try {
            healthService.updateAnswer(answer);
            return new ResponseResult(CommonCode.SUCCESS);
        }catch (Exception e){
            log.error("编辑回答异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new ResponseResult(CommonCode.SYSTEMBUSY);
        }
    }

    @ApiOperation(value = "通过回答id查询回答",httpMethod = "GET")
    @ApiImplicitParam(name = "id",value = "回答id")
    @GetMapping(value = "/findAnswerById")
    public ResponseResult findAnswerById(String id){
        try {
            Answer answer=healthService.findAnswerById(id);
            return new ResponseResult(CommonCode.SUCCESS,answer);
        }catch (Exception e){
            log.error("通过回答id查询回答");
            log.error(e);
            log.error(sf.format(new Date()));
            return new ResponseResult(CommonCode.SYSTEMBUSY);
        }
    }

}
