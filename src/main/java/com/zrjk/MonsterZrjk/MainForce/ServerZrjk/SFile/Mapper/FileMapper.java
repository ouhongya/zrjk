package com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SFile.Mapper;

import com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SFile.Beans.File;
import com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SFile.Beans.Folder;
import com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SFile.Beans.Journal;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FileMapper {

    void addFolder(Folder folder);

    Folder findFolderById(String folderId);

    Folder findFolderByIdToJournal(String folderId);

    void addFile(File file);

    File findFileById(String id);

    File findFileByIdToJournal(String id);

    void updateFolderStatus(@Param("id") String id,@Param("status") int i);

    void updateFileStatus(@Param("id") String id,@Param("status") int i);

    List<Folder> findFolderByParentId(@Param("folderParentId") String folderParentId,@Param("search") String search);

    List<File> findFileByFolderId(@Param("folderId") String folderId,@Param("search") String search);

    void addJournal(Journal journal);

    List<Journal> findJournal();

}
