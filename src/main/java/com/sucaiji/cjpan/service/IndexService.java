package com.sucaiji.cjpan.service;

import com.sucaiji.cjpan.entity.Index;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;


public interface IndexService {
    /**
     * 创建文件夹，如果parentUuid为空，则在根目录创建文件夹
     * @param name
     * @param parentUuid
     */
    void createDir(String name,String parentUuid);
    //void createDir(String name);
    List<Index> visitDir(String parentUuid);
    //List<Index> visitDir();
    //void uploadFile(MultipartFile multipartFile);

    /**
     *
     * @return
     */
    Index getIndexByUuid(String uuid);

    /**
     * 根据传入的uuid获取到文件，并写入到传入的outputstream里面
     * @param uuid
     * @param os
     */
    void writeInOutputStream(String uuid, OutputStream os) throws IOException;

    /**
     * 根据传入的uuid获取到文件，并写入到传入的outputstream里面
     * @param index
     * @param os
     */
    void writeInOutputStream(Index index, OutputStream os ) throws IOException;

    /**
     * 文件合并和保存到数据库
     * @param parentUuid
     * @param fileMd5
     * @param name
     * @param total
     * @return
     */
    boolean saveFile(String parentUuid, String fileMd5,String name,int total);

    /**
     * 将接收到的分片包保存在临时文件夹
     * @param multipartFile
     * @param fileMd5
     * @param md5
     * @param index
     */
    void saveTemp(MultipartFile multipartFile,String fileMd5,String md5,Integer index);

    /**
     * 当服务器中有该文件时，通过md5值秒存（在数据库index表中添加一条新纪录）
     * @param md5
     * @param parentUuid
     * @param name
     */
    void saveByMd5(String md5,String parentUuid,String name);

    /**
     * 通过uuid获取文件实际所在位置，如果文件不存在则返回null
     * @param uuid
     * @return
     */
    File getFileByUuid(String uuid);

    /**
     * 判断该md5对应的文件是否存在
     * @param md5
     * @return
     */
    boolean md5Exist(String md5);

    /**
     * 删除某个文件，如果该uuid指向一个文件夹的话，则删除该文件夹下所有文件
     * @param uuid
     */
    void deleteByUuid(String uuid);

}
