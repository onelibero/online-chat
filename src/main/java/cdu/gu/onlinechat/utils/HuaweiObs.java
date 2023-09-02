package cdu.gu.onlinechat.utils;

import com.obs.services.ObsClient;
import com.obs.services.exception.ObsException;
import com.obs.services.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: HweiOBSConfig
 * @Description: 华为云OBS配置类
 * @Author: wuhuiju
 * @Date: 2021-12-21 15:56
 * @Version: 1.0
 */

@Slf4j
@Component
public class HuaweiObs {
    /**
     * 访问密钥AK
     */
    @Value("${hwyun.obs.accessKey}")
    private String accessKey;

    /**
     * 访问密钥SK
     */
    @Value("${hwyun.obs.securityKey}")
    private String securityKey;

    /**
     * 终端节点
     */
    @Value("${hwyun.obs.endPoint}")
    private String endPoint;

    /**
     * 桶
     */
    @Value("${hwyun.obs.bucketName}")
    private String bucketName;

    /**
     * @Description 获取OBS客户端实例
     * @author wuhuiju
     * @date 2021/12/21 15:57
     * @return
     * @return: com.obs.services.ObsClient
     */
    public ObsClient getInstance() {
        return new ObsClient(accessKey, securityKey, endPoint);
    }


    /**
     * @Description 销毁OBS客户端实例
     * @author wuhuiju
     * @date 2021/12/21 17:32
     * @param: obsClient
     * @return
     */
    public void destroy(ObsClient obsClient){
        try {
            obsClient.close();
        } catch (ObsException e) {
            log.error("obs执行失败", e);
        } catch (Exception e) {
            log.error("执行失败", e);
        }
    }

    /**
     * @Description 微服务文件存放路径
     * @author wuhuiju
     * @date 2021/12/21 15:57
     * @return
     * @return: java.lang.String
     */
    public static String getObjectKey() {
        // 项目或者服务名称 + 日期存储方式
        return "Hwei" + "/" + new SimpleDateFormat("yyyy-MM-dd").format(new Date() )+ "/";
    }

    /**
     * @Description 删除文件
     * @author wuhuiju
     * @date 2021/12/21 17:02
     * @param: objectKey 文件名
     * @return: boolean 执行结果
     */
    public boolean delete(String objectKey) {
        ObsClient obsClient = null;
        try {
            // 创建ObsClient实例
            obsClient = new ObsClient(accessKey, securityKey, endPoint);
            // obs删除
            obsClient.deleteObject(bucketName,objectKey);
        } catch (ObsException e) {
            log.error("obs删除保存失败", e);
        } finally {
            destroy(obsClient);
        }
        return true;
    }


    /**
     * @Description 批量删除文件
     * @author wuhuiju
     * @date 2021/12/21 17:02
     * @param: objectKeys 文件名集合
     * @return: boolean 执行结果
     */

    public boolean delete(List<String> objectKeys) {
        ObsClient obsClient = null;
        try {
            obsClient =  new ObsClient(accessKey, securityKey, endPoint);
            DeleteObjectsRequest deleteObjectsRequest = new DeleteObjectsRequest(bucketName);
            objectKeys.forEach(x -> deleteObjectsRequest.addKeyAndVersion(x));
            // 批量删除请求
            obsClient.deleteObjects(deleteObjectsRequest);
            return true;
        } catch (ObsException e) {
            log.error("obs删除保存失败", e);
        } finally {
           destroy(obsClient);
        }
        return false;
    }


    /**
     * @Description 上传文件
     * @author wuhuiju
     * @date 2021/12/21 17:03
     * @param: uploadFile 上传文件
     * @param: objectKey 文件名称
     * @return: java.lang.String url访问路径
     */
    public String fileUpload(MultipartFile uploadFile, String objectKey) {
        ObsClient obsClient = null;
        try {

            obsClient = new ObsClient(accessKey, securityKey, endPoint);
            // 判断桶是否存在
            boolean exists = obsClient.headBucket(bucketName);
            if(!exists){
                // 若不存在，则创建桶
                HeaderResponse response = obsClient.createBucket(bucketName);
                log.info("创建桶成功" + response.getRequestId());
            }
            InputStream inputStream = uploadFile.getInputStream();
            long available = inputStream.available();
            PutObjectRequest request = new PutObjectRequest(bucketName,objectKey,inputStream);

            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(available);
            request.setMetadata(objectMetadata);
            // 设置对象访问权限为公共读
            request.setAcl(AccessControlList.REST_CANNED_PUBLIC_READ);
            PutObjectResult result = obsClient.putObject(request);

            // 读取该已上传对象的URL
            log.info("已上传对象的URL" + result.getObjectUrl());
            return result.getObjectUrl();
        } catch (ObsException e) {
            log.error("obs上传失败", e);
        } catch (IOException e) {
            log.error("上传失败", e);
        } finally {
            destroy(obsClient);
        }
        return null;
    }

    /**
     * @Description 文件下载
     * @author wuhuiju
     * @date 2021/12/21 17:04
     * @param: objectKey
     * @return: java.io.InputStream
     */
    public InputStream fileDownload(String objectKey) {
        ObsClient obsClient = null;
        try {

            obsClient = new ObsClient(accessKey, securityKey, endPoint);
            ObsObject obsObject = obsClient.getObject(bucketName, objectKey);
            return obsObject.getObjectContent();
        } catch (ObsException e) {
            log.error("obs文件下载失败", e);
        } finally {
           destroy(obsClient);
        }
        return null;
    }
}