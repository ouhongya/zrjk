package com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SFile.Service;

import com.github.pagehelper.PageInfo;
import com.zrjk.MonsterZrjk.Common.Beans.ParamBean;
import com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SFile.Beans.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

public interface FileService {
    Integer addFolder(String name, Integer state, HttpServletRequest request) throws Exception;

    String fileUpload(MultipartFile multipartFile, String folderId,HttpServletRequest request) throws IOException, Exception;

    void deleteFile(ListBean listBean,HttpServletRequest request) throws Exception;

//    SuperFile findFile(ParamBean paramBean,Integer state);

    PageInfo<Journal> findJournal(ParamBean paramBean, Integer state);

    List<Folder> findFolder(ParamBean paramBean, Integer state) throws Exception;

    PageInfo<File> findFile(ParamBean paramBean, Integer state,String folderId) throws Exception;

    void downLoadJournal(String fileId,HttpServletRequest request) throws Exception;
}
