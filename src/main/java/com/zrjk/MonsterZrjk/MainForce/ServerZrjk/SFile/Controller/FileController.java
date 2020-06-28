package com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SFile.Controller;

import com.github.pagehelper.PageInfo;
import com.zrjk.MonsterZrjk.Common.Beans.ParamBean;
import com.zrjk.MonsterZrjk.Common.Result.CommonCode;
import com.zrjk.MonsterZrjk.Common.Result.ResponseResult;
import com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SFile.Beans.*;
import com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SFile.Service.FileService;
import io.swagger.annotations.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/server/file")
@Log4j2
@Api(tags = "腾讯云-文件-接口")
public class FileController {


    @Autowired
    private FileService fileService;

    private SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @ApiOperation(value = "文件上传",httpMethod = "POST")
    @ApiImplicitParam(name = "folderId",value = "上传的文件属于哪个二级文件夹   在一级文件夹下就传 1文件  2图片  3视频")
    @PostMapping(value = "/fileUpload")
    public ResponseResult fileUpload(@RequestParam MultipartFile salaryBill,String folderId,HttpServletRequest request){
        try {
            if(salaryBill.getOriginalFilename().contains("/")){
                return new ResponseResult(CommonCode.INVAlLID_NAME);
            }
           String statusStr= fileService.fileUpload(salaryBill,folderId,request);

            if("滚".equals(statusStr)){
                return new ResponseResult(CommonCode.FILE_NAME_REPEAT);
            }else {
                return new ResponseResult(CommonCode.SUCCESS);
            }
        }catch (Exception e){
            log.error("文件上传异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new ResponseResult(CommonCode.SYSTEMBUSY);
        }
    }
    @ApiOperation(value = "图片上传",httpMethod = "POST")
    @ApiImplicitParam(name = "folderId",value = "上传的文件属于哪个二级文件夹   在一级文件夹下就传 1文件  2图片  3视频")
    @PostMapping(value = "/imageUpload")
    public ResponseResult imageUpload(@RequestParam MultipartFile file,String folderId,HttpServletRequest request){
        try {
            if(file.getOriginalFilename().contains("/")){
                return new ResponseResult(CommonCode.INVAlLID_NAME);
            }
            String statusStr= fileService.fileUpload(file,folderId,request);

            if("滚".equals(statusStr)){
                return new ResponseResult(CommonCode.FILE_NAME_REPEAT);
            }else {
                return new ResponseResult(CommonCode.SUCCESS,statusStr);
            }
        }catch (Exception e){
            log.error("图片上传异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new ResponseResult(CommonCode.SYSTEMBUSY);
        }
    }

//    @ApiOperation(value = "查询云上文件",httpMethod = "GET")
//    @ApiImplicitParam(name = "state",value = "1查询文件  2查询图片  3查询视频")
//    @GetMapping(value = "/findFile")
//    public ResponseResult findFile(ParamBean paramBean,Integer state){
//        try {
//            SuperFile superFile = fileService.findFile(paramBean,state);
//            return new ResponseResult(CommonCode.SUCCESS,superFile);
//        }catch (Exception e){
//            log.error("查询云上文件异常");
//            log.error(e);
//            log.error(sf.format(new Date()));
//            return new ResponseResult(CommonCode.SYSTEMBUSY);
//        }
//    }

    @ApiOperation(value = "创建文件夹",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name",value = "文件夹名称"),
            @ApiImplicitParam(name = "state",value = "在哪个目录下创建   1文件   2图片  3视频")
    })
    @GetMapping(value = "/addFolder")
    public ResponseResult addFolder(String name, Integer state, HttpServletRequest request){
        try {
            if(name.contains("/")){
                return new ResponseResult(CommonCode.INVAlLID_NAME);
            }
            Integer status =fileService.addFolder(name,state,request);

            if(1==status){
                return new ResponseResult(CommonCode.SUCCESS);
            }
            if(2==status){
                return new ResponseResult(CommonCode.FOLDER_NAME_REPEAT);
            }

            return new ResponseResult(CommonCode.SYSTEMBUSY);
        }catch (Exception e){
            log.error("创建文件夹异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new ResponseResult(CommonCode.SYSTEMBUSY);
        }
    }

    @ApiOperation(value = "文件 文件夹删除",httpMethod = "POST")
    @ApiImplicitParam(name = "listBean",value = "文件或者文件夹的id集合对象")
    @PostMapping (value = "/deleteFile")
    public ResponseResult deleteFile(@RequestBody ListBean listBean,HttpServletRequest request){
        try {
            fileService.deleteFile(listBean,request);
            return new ResponseResult(CommonCode.SUCCESS);
        }catch (Exception e){
            log.error("文件，文件夹删除异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new ResponseResult(CommonCode.SYSTEMBUSY);
        }
    }

    @ApiOperation(value = "日志查询",httpMethod = "GET")
    @ApiImplicitParam(name = "state",value = "查询日志类型  1文件  2图片  3视频")
    @GetMapping(value = "/findJournal")
    public ResponseResult findJournal(ParamBean paramBean,Integer state){
        try {
           PageInfo<Journal> pageInfo =fileService.findJournal(paramBean,state);
           return new ResponseResult(CommonCode.SUCCESS,pageInfo);
        }catch (Exception e){
            log.error("日志查询异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new ResponseResult(CommonCode.SYSTEMBUSY);
        }
    }

    @ApiOperation(value = "查询文件夹",httpMethod = "GET")
    @ApiImplicitParam(name = "state",value = "1查询文件  2查询图片  3查询视频")
    @GetMapping(value = "/findFolder")
    public ResponseResult findFolder(ParamBean paramBean,Integer state){
        try {
            List<Folder> folders=fileService.findFolder(paramBean,state);
            return new ResponseResult(CommonCode.SUCCESS,folders);
        }catch (Exception e){
            log.error("查询文件夹异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new ResponseResult(CommonCode.SYSTEMBUSY);
        }
    }

    @ApiOperation(value = "查询文件",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "state",value = "1查询文件  2查询图片  3查询视频  当查询二级文件夹下的文件时 这个参数随便传"),
            @ApiImplicitParam(name = "folderId",value = "二级文件夹id  查一级时就不传")
    })
    @GetMapping(value = "/findFile")
    public ResponseResult findFile(ParamBean paramBean,Integer state,String folderId){
        try {
            PageInfo<File> pageInfo=fileService.findFile(paramBean,state,folderId);
            return new ResponseResult(CommonCode.SUCCESS,pageInfo);
        }catch (Exception e){
            log.error("查询文件异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new ResponseResult(CommonCode.SYSTEMBUSY);
        }
    }


    @ApiOperation(value = "用户下载时记录日志",httpMethod = "GET")
    @ApiImplicitParam(name = "fileId",value = "文件id")
    @GetMapping(value = "/downLoadJournal")
    public ResponseResult downLoadJournal(String fileId,HttpServletRequest request){
        try {
            fileService.downLoadJournal(fileId,request);
            return new ResponseResult(CommonCode.SUCCESS);
        }catch (Exception e){
            log.error("用户下载时记录日志异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new ResponseResult(CommonCode.SYSTEMBUSY);
        }
    }
}
