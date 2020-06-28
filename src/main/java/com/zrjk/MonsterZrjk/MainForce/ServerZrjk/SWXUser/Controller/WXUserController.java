package com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SWXUser.Controller;

import com.github.pagehelper.PageInfo;
import com.zrjk.MonsterZrjk.Common.Beans.ParamBean;
import com.zrjk.MonsterZrjk.Common.Result.CommonCode;
import com.zrjk.MonsterZrjk.Common.Result.ResponseResult;
import com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SHealth.Beans.Article;
import com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SHealth.Beans.Doctor;
import com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SHealth.Beans.HealthTheme;
import com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SHealth.Beans.Video;
import com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SWXUser.Beans.Column;
import com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SWXUser.Beans.User;
import com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SWXUser.Service.WXUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Api(tags = "WX用户后台接口")
@RestController
@RequestMapping(value = "/server/WXUser")
@Log4j2
public class WXUserController {

    @Autowired
    private WXUserService wxUserService;

    private SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    @ApiOperation(value = "查询wx用户列表",httpMethod = "GET")
    @GetMapping(value = "/findWXUser")
    public ResponseResult findWXUser(ParamBean paramBean){
        try {
            PageInfo<User> pageInfo=wxUserService.findWXUser(paramBean);
            return new ResponseResult(CommonCode.SUCCESS,pageInfo);
        }catch (Exception e){
            log.error("查询wx用户列表异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new ResponseResult(CommonCode.SYSTEMBUSY);
        }
    }

    @ApiOperation(value = "查询用户关注的主题列表",httpMethod = "GET")
    @ApiImplicitParam(name = "uid",value = "用户id")
    @GetMapping(value = "/findFollowHealthTheme")
    public ResponseResult findFollowHealthTheme(String uid,ParamBean paramBean){
        try {
            PageInfo<HealthTheme> pageInfo=wxUserService.findFollowHealthTheme(uid,paramBean);
            return new ResponseResult(CommonCode.SUCCESS,pageInfo);
        }catch (Exception e){
            log.error("查询用户关注的主题列表异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new ResponseResult(CommonCode.SYSTEMBUSY);
        }
    }

    @ApiOperation(value = "查询用户关注的文章列表",httpMethod = "GET")
    @ApiImplicitParam(name = "uid",value = "用户id")
    @GetMapping(value = "/findFollowArticle")
    public ResponseResult findFollowArticle(String uid,ParamBean paramBean){
        try {
            PageInfo<Article> pageInfo=wxUserService.findFollowArticle(uid,paramBean);
            return new ResponseResult(CommonCode.SUCCESS,pageInfo);
        }catch (Exception e){
            log.error("查询用户关注的文章列表异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new ResponseResult(CommonCode.SYSTEMBUSY);
        }
    }

    @ApiOperation(value = "查询用户关注的视频列表",httpMethod = "GET")
    @ApiImplicitParam(name = "uid",value = "用户id")
    @GetMapping(value = "/findFollowVideo")
    public ResponseResult findFollowVideo(String uid,ParamBean paramBean){
        try {
            PageInfo<Video> pageInfo=wxUserService.findFollowVideo(uid,paramBean);
            return new ResponseResult(CommonCode.SUCCESS,pageInfo);
        }catch (Exception e){
            log.error("查询用户关注的视频列表");
            log.error(e);
            log.error(sf.format(new Date()));
            return new ResponseResult(CommonCode.SYSTEMBUSY);
        }
    }

    @ApiOperation(value = "查询用户关注的医生了列表",httpMethod = "GET")
    @ApiImplicitParam(name = "uid",value = "用户id")
    @GetMapping(value = "/findFollowDoctor")
    public ResponseResult findFollowDoctor(String uid,ParamBean paramBean){
        try {
            PageInfo<Doctor> pageInfo=wxUserService.findFollowDoctor(uid,paramBean);
            return new ResponseResult(CommonCode.SUCCESS,pageInfo);
        }catch (Exception e){
            log.error("查询用户关注的医生列表异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new ResponseResult(CommonCode.SYSTEMBUSY);
        }
    }

    @ApiOperation(value = "添加列",httpMethod = "POST")
    @ApiImplicitParam(name = "name",value = "列名")
    @PostMapping(value = "/addColumn")
    public ResponseResult addColumn(String name){
        try {
            if(wxUserService.addColumn(name)){
                return new ResponseResult(CommonCode.SUCCESS);
            }else {
                return new ResponseResult(CommonCode.COLUMN_NAME_REPEAT);
            }
        }catch (Exception e){
            log.error("添加列异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new ResponseResult(CommonCode.SYSTEMBUSY);
        }
    }

    @ApiOperation(value = "查询所有列",httpMethod = "GET")
    @GetMapping(value = "/findColumn")
    public ResponseResult findColum(){
        try {
           List<Column> columns= wxUserService.findAllColumn();
           return new ResponseResult(CommonCode.SUCCESS,columns);
        }catch (Exception e){
            log.error("查询所有列异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new ResponseResult(CommonCode.SYSTEMBUSY);
        }
    }

    @ApiOperation(value = "给用户的列添加或编辑内容  当前列无内容就添加  有内容就是编辑   如果是删除content传null就行",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uid",value = "用户id"),
            @ApiImplicitParam(name = "cid",value = "列id"),
            @ApiImplicitParam(name = "content",value = "内容")
    })
    @PostMapping(value = "/addColumnContent")
    public ResponseResult addColumnContent(String uid,String cid,String content){
        try {
            wxUserService.addColumnContent(uid,cid,content);
            return new ResponseResult(CommonCode.SUCCESS);
        }catch (Exception e){
            log.error("给用户的列添加内容异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new ResponseResult(CommonCode.SYSTEMBUSY);
        }
    }

    @ApiOperation(value = "删除列",httpMethod = "GET")
    @ApiImplicitParam(name = "cid",value = "列id")
    @GetMapping(value = "/deleteColumn")
    public ResponseResult deleteColumn(String cid){
        try {
            wxUserService.deleteColumn(cid);
            return new ResponseResult(CommonCode.SUCCESS);
        }catch (Exception e){
            log.error("删除列异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new ResponseResult(CommonCode.SYSTEMBUSY);
        }
    }

    @ApiOperation(value = "编辑列名",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "列id"),
            @ApiImplicitParam(name = "name",value = "列名")
    })
    @PostMapping(value = "/updateColumn")
    public ResponseResult updateColumn(String id,String name){
        try {
            if(wxUserService.updateColumn(id,name)){
                return new ResponseResult(CommonCode.SUCCESS);
            }else {
                return new ResponseResult(CommonCode.COLUMN_NAME_REPEAT);
            }
        }catch (Exception e){
            log.error("编辑列名异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new ResponseResult(CommonCode.SYSTEMBUSY);
        }
    }


}
