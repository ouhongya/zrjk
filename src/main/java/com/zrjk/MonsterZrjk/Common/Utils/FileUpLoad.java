package com.zrjk.MonsterZrjk.Common.Utils;
//                            _ooOoo_
//                           o8888888o
//                           88" . "88
//                           (| -_- |)
//                            O\ = /O
//                        ____/`---'\____
//                      .   ' \\| |// `.
//                       / \\||| : |||// \
//                     / _||||| -:- |||||- \
//                       | | \\\ - /// | |
//                     | \_| ''\---/'' | |
//                      \ .-\__ `-` ___/-. /
//                   ___`. .' /--.--\ `. . __
//                ."" '< `.___\_<|>_/___.' >'"".
//               | | : `- \`.;`\ _ /`;.`/ - ` : | |
//                 \ \ `-. \_ __\ /__ _/ .-` / /
//         ======`-.____`-.___\_____/___.-`____.-'======
//                            `=---='
//
//         .............................................
//                  佛祖保佑             永无BUG
//          佛曰:
//                  写字楼里写字间，写字间里程序员；
//                  程序人员写程序，又拿程序换酒钱。
//                  酒醒只在网上坐，酒醉还来网下眠；
//                  酒醉酒醒日复日，网上网下年复年。
//                  但愿老死电脑间，不愿鞠躬老板前；
//                  奔驰宝马贵者趣，公交自行程序员。
//                  别人笑我忒疯癫，我笑自己命太贱；
//                  不见满街漂亮妹，哪个归得程序员？

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.*;
import com.qcloud.cos.region.Region;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 文件上传-腾讯云
 */
@Component
public class FileUpLoad {

    //腾讯云密钥对（1/2）（腾讯云官网个人信息可查看）
   @Value("${secretId}")
   private String  secretId;

    //腾讯云密钥对（2/2）（腾讯云官网个人信息可查看）
   @Value("${secretKey}")
   private String secretKey;

    //AO使用的存储桶名称
   @Value("${bucketName}")
   private String bucketName;

   @Value("${bucketNameT}")
   private String bucketNameT;

    //腾讯云指定的 地区缩写  （不同地区 缩写不同）
   @Value("${area}")
   private String area;
    /**
     * 文件上传
     * @param inputStream
     * @return  图片url
     */
    public String upload(InputStream inputStream, String fileName,String fileFolder,String disposition,Integer a) throws IOException {
        String bName="";
        if(a==1){
            bName=bucketName;
        }else {
            bName=bucketNameT;
        }
        //用户身份初始化
        COSCredentials cosCredentials = new BasicCOSCredentials(secretId, secretKey);
        //创建地区对象
        Region region = new Region(area);
        //创建ClientConfig 并指定地区
        ClientConfig clientConfig = new ClientConfig(region);
        //生成cos客户端
        COSClient cosClient = new COSClient(cosCredentials,clientConfig);
        //判断该文件名称是否重复
        COSObject file = null;
        if(fileFolder==null){
            file=findFileByName(bName, fileName);
        }else {
            file=findFileByName(bName,fileFolder+fileName);
        }
        if(file!=null){
            return "滚";
        }
        //获取文件大小
        int available = inputStream.available();
        //创建objectMetadata对象  用户设置文件的访问信息
        ObjectMetadata objectMetadata = new ObjectMetadata();
        //设置objectMetadata属性
        //文件缓存长度  从流中获取
        objectMetadata.setContentLength(available);
        //缓存控制  no-cache 代表每次缓存需要服务器验证   no-store  代表不缓存
        objectMetadata.setCacheControl("no-cache");
        //设置请求头
        objectMetadata.setHeader("Pragma", "no-cache");
        //设置文件类型
       // objectMetadata.setContentType(getContentType(fileName.substring(fileName.lastIndexOf("."))));
        //设置外网访问文件的方式    固定写法  inline为直接将文件显示到页面上（一般用于显示图片）
        // attachment弹出下载框 让用户下载  需要指定fileName  例：objectMetadata.setContentDisposition("attachment;filename=哈哈哈");
        objectMetadata.setContentDisposition(disposition+fileName);
        //上传图片
        if(fileFolder==null){
            cosClient.putObject(bName, fileName, inputStream, objectMetadata);
        }else {
            cosClient.putObject(bName, fileFolder+"/"+fileName, inputStream, objectMetadata);
        }
        //拼接腾讯云存储的外网访问地址
        String url="";
        if(fileFolder==null){
            url="https://"+bName+".cos."+area+".myqcloud.com/"+fileName;
        }else {
            url="https://"+bName+".cos."+area+".myqcloud.com/"+fileFolder+fileName;
        }
        //关闭腾讯云连接
        cosClient.shutdown();

        return url;
    }
//    public  List<COSObjectSummary>  findFile(String name,String search,int size){
//        //用户身份初始化
//        COSCredentials cosCredentials = new BasicCOSCredentials(secretId, secretKey);
//        //创建地区对象
//        Region region = new Region(area);
//        //创建ClientConfig 并指定地区
//        ClientConfig clientConfig = new ClientConfig(region);
//        //生成cos客户端
//        COSClient cosClient = new COSClient(cosCredentials,clientConfig);
//        ListObjectsRequest listObjectsRequest = new ListObjectsRequest();
//        // 设置存储桶名称
//        listObjectsRequest.setBucketName(bucketName);
//        //setPrefix是搜索条件
//       listObjectsRequest.setPrefix(search);
//        // deliter表示分隔符, 设置为/表示列出当前目录下的object, 设置为空表示列出所有的object
//       //listObjectsRequest.setDelimiter("/");
//        // 设置最大遍历出多少个对象, 一次listobject最大支持1000
//        listObjectsRequest.setMaxKeys(size);
//        listObjectsRequest.setMarker(name);
//        ObjectListing objectListing = null;
//            try {
//                objectListing = cosClient.listObjects(listObjectsRequest);
//            } catch (CosServiceException e) {
//                e.printStackTrace();
//            } catch (CosClientException e) {
//                e.printStackTrace();
//            }
//            // object summary表示所有列出的object列表
//            List<COSObjectSummary> cosObjectSummaries = objectListing.getObjectSummaries();
//
//
////            String nextMarker = objectListing.getNextMarker();
////            listObjectsRequest.setMarker(nextMarker);
//
//        //关闭云连接
//        cosClient.shutdown();
//          return cosObjectSummaries;
//    }


    /**
     *
     * @param name
     * @return status  1=创建成功   2=文件夹名称已存在   3文件夹名称不能含有/
     */
    public Integer addFolder(String name,Integer state) throws IOException {
        //文件夹名称是否包含/
        if(name.contains("/")){
            return 3;
        }

        if(state==1){
            name="file/"+name;
        }
        if(state==2){
            name="picture/"+name;
        }
        if(state==3){
            name="video/"+name;
        }

        String s = upload(new ByteArrayInputStream(new byte[0]),name + "/",null,"attachment;filename=",1);
        if("滚".equals(s)){
            return 2;
        }else {
            return 1;
        }
    }

    /**
     * 通过文件名称查询单个文件属性
     * @param bucketName
     * @param name
     * @return
     */
    public COSObject findFileByName(String bucketName,String name){
        //用户身份初始化
        COSCredentials cosCredentials = new BasicCOSCredentials(secretId, secretKey);
        //创建地区对象
        Region region = new Region(area);
        //创建ClientConfig 并指定地区
        ClientConfig clientConfig = new ClientConfig(region);
        //生成cos客户端
        COSClient cosClient = new COSClient(cosCredentials,clientConfig);
        try {
            COSObject object = cosClient.getObject(bucketName, name);
            return object;
        }catch (Exception e){
            return null;
        }finally {
            cosClient.shutdown();
        }
    }


    /**
     * 文件 文件夹删除
     * @param name
     * @return
     */
    public void deleteFile(String name) {
        //用户身份初始化
        COSCredentials cosCredentials = new BasicCOSCredentials(secretId, secretKey);
        //创建地区对象
        Region region = new Region(area);
        //创建ClientConfig 并指定地区
        ClientConfig clientConfig = new ClientConfig(region);
        //生成cos客户端
        COSClient cosClient = new COSClient(cosCredentials,clientConfig);

            cosClient.deleteObject(bucketName,name);

        cosClient.shutdown();

    }
}

