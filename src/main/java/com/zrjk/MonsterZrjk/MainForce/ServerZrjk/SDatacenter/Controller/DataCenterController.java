package com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SDatacenter.Controller;


import com.github.pagehelper.PageInfo;
import com.zrjk.MonsterZrjk.Common.Beans.ParamBean;
import com.zrjk.MonsterZrjk.Common.Result.CommonCode;
import com.zrjk.MonsterZrjk.Common.Result.ResponseResult;
import com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SDatacenter.Beans.DataArticle;
import com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SDatacenter.Beans.DataDoctor;
import com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SDatacenter.Beans.DataHealthTheme;
import com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SDatacenter.Beans.DataVideo;
import com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SDatacenter.Service.DataCenterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping(value = "/server/datacenter")
@Log4j2
@Api(tags = "数据中心接口-后台")
public class DataCenterController {
    @Autowired
    private DataCenterService dataCenterService;

    private SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @ApiOperation(value = "统计医生列表",httpMethod = "GET")
    @GetMapping(value = "/findDoctor")
    public ResponseResult findDoctor(ParamBean paramBean){
        try {
                PageInfo<DataDoctor> pageInfo=dataCenterService.findDoctor(paramBean);
                return new ResponseResult(CommonCode.SUCCESS,pageInfo);
        }catch (Exception e){
            log.error("统计医生列表异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new ResponseResult(CommonCode.SYSTEMBUSY);
        }
    }

    @ApiOperation(value = "统计文章列表",httpMethod = "GET")
    @GetMapping(value = "/findArticle")
    public ResponseResult findArticle(ParamBean paramBean){
        try {
            PageInfo<DataArticle> pageInfo=dataCenterService.findArticle(paramBean);
            return new ResponseResult(CommonCode.SUCCESS,pageInfo);
        }catch (Exception e){
            log.error("统计文章列表异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new ResponseResult(CommonCode.SYSTEMBUSY);
        }
    }

    @ApiOperation(value = "统计视频列表",httpMethod = "GET")
    @GetMapping(value = "/findVideo")
    public ResponseResult findVideo(ParamBean paramBean){
        try {
            PageInfo<DataVideo> pageInfo=dataCenterService.findVideo(paramBean);
            return new ResponseResult(CommonCode.SUCCESS,pageInfo);
        }catch (Exception e){
            log.error("统计视频列表异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new ResponseResult(CommonCode.SYSTEMBUSY);
        }
    }

    @ApiOperation(value = "统计主题列表",httpMethod = "GET")
    @GetMapping(value = "/findHealthTheme")
    public ResponseResult findHealthTheme(ParamBean paramBean){
        try {
            PageInfo<DataHealthTheme> pageInfo=dataCenterService.findHealthTheme(paramBean);
            return new ResponseResult(CommonCode.SUCCESS,pageInfo);
        }catch (Exception e){
            log.error("统计主题列表异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new ResponseResult(CommonCode.SYSTEMBUSY);
        }
    }

}
