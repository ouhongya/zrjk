package com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SFile.Service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qcloud.cos.model.COSObject;
import com.zrjk.MonsterZrjk.Common.Beans.ParamBean;
import com.zrjk.MonsterZrjk.Common.Utils.FileUpLoad;
import com.zrjk.MonsterZrjk.Common.Utils.TokenUtil;
import com.zrjk.MonsterZrjk.Common.Utils.UUIDGenerator;
import com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SAccount.Beans.Account;
import com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SAccount.Mapper.AccountMapper;
import com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SFile.Beans.*;
import com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SFile.Mapper.FileMapper;
import com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SFile.Service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Transactional
@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileMapper fileMapper;

    @Autowired
    private FileUpLoad fileUpLoad;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private TokenUtil tokenUtil;

    @Value("${fileId}")
    private String fileId;

    @Value("${videoId}")
    private String videoId;

    @Value("${pictureId}")
    private String pictureId;

    //存储桶名称
    @Value("${bucketName}")
    private String bucketName;

    /**
     * 创建文件夹
     * @param name
     * @return
     */
    @Override
    public Integer addFolder(String name, Integer state, HttpServletRequest request) throws Exception {
        String fileParentId="";
        String newName="";
        if(state==1){
            fileParentId=fileId;
            newName="file/"+name;
        }
        if(state==2){
            fileParentId=pictureId;
            newName="picture/"+name;
        }
        if(state==3){
            fileParentId=videoId;
            newName="video/"+name;
        }
        //查询是否能创建文件夹
        COSObject ob = fileUpLoad.findFileByName(bucketName, newName+"/");
        if(ob==null){
                //在数据库中创建一条数据
                Folder folder = new Folder();
                folder.setId(UUIDGenerator.uuid());
                folder.setFolderName(name);
                folder.setParentId(fileParentId);
                folder.setCreatedTime(new Date());
                fileMapper.addFolder(folder);
                 //在腾讯云上创建文件夹
                Integer status = fileUpLoad.addFolder(name,state);
                //将本次操作记录日志
            Journal journal = new Journal();
            journal.setId(UUIDGenerator.uuid());
            journal.setFid(folder.getId());
            journal.setTypes(3);
            //获取当前登录的userid

            journal.setUserId(getUid(request));

            journal.setCreatedTime(new Date());

            fileMapper.addJournal(journal);

            return status;
        }
        return 2;
    }

    /**
     * 文件上传
     * @param multipartFile
     * @param folderId
     * @return
     */
    @Override
    public String fileUpload(MultipartFile multipartFile, String folderId,HttpServletRequest request) throws Exception {
        String downLoad="attachment;filename=";
        String look="inline;filename=";
        //通过folderId查询文件夹目录
        if("1".equals(folderId)){
            folderId=fileId;
        }
        if("2".equals(folderId)){
            folderId=pictureId;
        }
        if("3".equals(folderId)){
            folderId=videoId;
        }

        Folder folder=fileMapper.findFolderById(folderId);

        String folderName=folder.getFolderName();
        if(folder.getParentName()!=null){
            folderName=folder.getParentName()+folderName+"/";
        }

        String statusStr = fileUpLoad.upload(multipartFile.getInputStream(), multipartFile.getOriginalFilename(), folderName,downLoad,1);
        if(!"滚".equals(statusStr)){
            String lookFileName=null;
            String lookFileUrl=null;
            //如果是上传的图片  需要再上传一次 用于页面显示
            if(folder.getId().equals(pictureId)||(folder.getParentId()!=null&&folder.getParentId().equals(pictureId))){
                lookFileName=UUIDGenerator.uuid()+multipartFile.getOriginalFilename();
                String lookUrl = fileUpLoad.upload(multipartFile.getInputStream(), lookFileName, folderName, look,1);
                lookFileUrl=lookUrl;
            }
                  //在数据库中创建一条数据
            File file = new File();
            file.setId(UUIDGenerator.uuid());
            file.setFileName(multipartFile.getOriginalFilename());
            file.setCreatedTime(new Date());
            file.setDownUrl(statusStr);
            file.setFolderId(folderId);
            file.setLookFileName(lookFileName);
            file.setLookUrl(lookFileUrl);
            fileMapper.addFile(file);
            //记录日志
            Journal journal = new Journal();
            journal.setId(UUIDGenerator.uuid());
            journal.setCreatedTime(new Date());
            journal.setUserId(getUid(request));
            journal.setFid(file.getId());
            journal.setTypes(1);
            fileMapper.addJournal(journal);
            statusStr=lookFileUrl;
        }
        return statusStr;
    }


    private String getUid(HttpServletRequest request) throws Exception{
        //获取当前登录的userid
        String token=null;
        for (Cookie cookie : request.getCookies()) {
            if("token".equals(cookie.getName())){
                token=cookie.getValue();
            }
        }
        if(token==null){
            throw new RuntimeException();
        }
       return tokenUtil.getUidFromToken(token);
    }
    /**
     * 文件或文件夹删除
     */
    @Override
    public void deleteFile(ListBean listBean,HttpServletRequest request) throws Exception {
        Integer state = 0;
        if(listBean.getState()!=null){
            state=listBean.getState();
        }
        Journal journal = new Journal();
        //通过id查询文件或文件夹
        if(state==1){
            for (String id : listBean.getIds()) {
                //删除文件夹  查询文件夹id
                Folder folder = fileMapper.findFolderById(id);
                fileUpLoad.deleteFile(folder.getParentName()+folder.getFolderName()+"/");
                //删除数据库上的数据
                fileMapper.updateFolderStatus(id,-1);
                //记录日志

                journal.setId(UUIDGenerator.uuid());
                journal.setTypes(5);
                journal.setUserId(getUid(request));
                journal.setCreatedTime(new Date());
                journal.setFid(id);
                fileMapper.addJournal(journal);
            }
        }
        if(state==2){
            for (String id : listBean.getIds()) {
                //删除文件
                File file=fileMapper.findFileById(id);
                //查询文件所属文件夹
                Folder folder = fileMapper.findFolderById(file.getFolderId());

                if(folder.getParentName()!=null){
                    fileUpLoad.deleteFile(folder.getParentName()+folder.getFolderName()+"/"+file.getFileName());
                    if(file.getLookFileName()!=null){
                        fileUpLoad.deleteFile(folder.getParentName()+folder.getFolderName()+"/"+file.getLookFileName());
                    }
                }
                if(folder.getParentName()==null){
                    fileUpLoad.deleteFile(folder.getFolderName()+file.getFileName());
                    if(file.getLookFileName()!=null){
                        fileUpLoad.deleteFile(folder.getParentName()+folder.getFolderName()+"/"+file.getLookFileName());
                    }
                }
                //删除数据库上的记录
                fileMapper.updateFileStatus(id,-1);
                //记录日志
                journal.setId(UUIDGenerator.uuid());
                journal.setTypes(4);
                journal.setUserId(getUid(request));
                journal.setCreatedTime(new Date());
                journal.setFid(id);
                fileMapper.addJournal(journal);
            }
        }
    }

//    /**
//     * 查询文件和文件夹
//     * @param paramBean
//     * @return
//     */
//    @Override
//    public SuperFile findFile(ParamBean paramBean,Integer state) {
//        String folderParentId="";
//        if(state==1){
//            folderParentId=fileId;
//        }
//        if(state==2){
//            folderParentId=pictureId;
//        }
//        if(state==3){
//            folderParentId=videoId;
//        }
//        //查询文件 文件夹列表
//        PageHelper.startPage(paramBean.getPage(),paramBean.getSize(),paramBean.getSortField()+" "+paramBean.getSortWay());
//        List<Folder> folders=fileMapper.findFolderByParentId(folderParentId,paramBean.getSearch());
//        //查询文件夹是否可以删除
//        for (Folder folder : folders) {
//            List<File> files = fileMapper.findFileByFolderId(folder.getId(),null);
//            if(files==null||files.size()<=0){
//                folder.setIsDelete(true);
//            }
//        }
//
//        PageHelper.startPage(paramBean.getPage(),paramBean.getSize(),paramBean.getSortField()+" "+paramBean.getSortWay());
//        List<File> files=fileMapper.findFileByFolderId(folderParentId,paramBean.getSearch());
//
//        SuperFile superFile = new SuperFile();
//        superFile.setFiles(files);
//        superFile.setFolders(folders);
//        return superFile;
//    }

    /**
     * 日志查询
     * @param paramBean
     * @param state
     * @return
     */
    @Override
    public PageInfo<Journal> findJournal(ParamBean paramBean, Integer state) {
        PageHelper.startPage(paramBean.getPage(),paramBean.getSize());
        //查询所有日志
        List<Journal> journals=fileMapper.findJournal();
        //查询日志关联的各种属性
        for (Journal journal : journals) {
            //查询日志操作人属性
            Account account = accountMapper.findAccountById(journal.getUserId());
            journal.setUsername(account.getUsername());
            journal.setTrueName(account.getTrueName());
            //查询日志操作的文件或文件夹属性
            File file = fileMapper.findFileByIdToJournal(journal.getFid());
            if(file==null){
                //查询文件夹
                Folder folder = fileMapper.findFolderByIdToJournal(journal.getFid());
                if(folder.getParentId()==null){
                    journal.setFName(folder.getFolderName());
                }else {
                    journal.setFName(folder.getParentName()+folder.getFolderName());
                }
            }else {
                //通过文件查询文件所属文件夹目录
                Folder folder = fileMapper.findFolderByIdToJournal(file.getFolderId());
                if(folder.getParentId()==null){
                    journal.setFName(folder.getFolderName()+file.getFileName());
                }else {
                    journal.setFName(folder.getParentName()+folder.getFolderName()+"/"+file.getFileName());
                }
            }
        }
        //日志通过时间倒序排序
        Collections.sort(journals, new Comparator<Journal>() {
            @Override
            public int compare(Journal o1, Journal o2) {
                return o2.getCreatedTime().compareTo(o1.getCreatedTime());
            }
        });
        PageInfo<Journal> pageInfo = new PageInfo<>(journals);
        return pageInfo;
    }


    /**
     * 查询文件夹
     * @param paramBean
     * @param state
     * @return
     */
    @Override
    public  List<Folder> findFolder(ParamBean paramBean, Integer state) throws Exception {
        String parentId=stateChecke(state);
        List<Folder> folders=fileMapper.findFolderByParentId(parentId,paramBean.getSearch());
        return folders;
    }


    /**
     * 查询文件
     * @param paramBean
     * @param state
     * @return
     */
    @Override
    public PageInfo<File> findFile(ParamBean paramBean, Integer state,String folderId) throws Exception {

        String parentId="";
        if(folderId==null){
           parentId= stateChecke(state);
        }else {
            parentId=folderId;
        }

        PageHelper.startPage(paramBean.getPage(),paramBean.getSize(),paramBean.getSortField()+" "+paramBean.getSortWay());

        List<File> files = fileMapper.findFileByFolderId(parentId, paramBean.getSearch());

        PageInfo<File> pageInfo = new PageInfo<>(files);

        return pageInfo;
    }

    private String stateChecke(Integer state){
        if(state==1){
            return fileId;
        }
        if(state==2){
            return pictureId;
        }
        if(state==3){
            return videoId;
        }
        return null;
    }


    /**
     * 下载时记录日志
     * @param fileId
     */
    @Override
    public void downLoadJournal(String fileId,HttpServletRequest request) throws Exception {
        Journal journal = new Journal();
        //记录日志
        journal.setId(UUIDGenerator.uuid());
        journal.setTypes(2);
        journal.setUserId(getUid(request));
        journal.setCreatedTime(new Date());
        journal.setFid(fileId);
        fileMapper.addJournal(journal);
    }
}
