package cdu.gu.onlinechat.utils;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component
public class TXCloudUtils {

    @Value("${txyun.obs.secretId}")
    private String secretId;
    //API密钥secretKey
    @Value("${txyun.obs.secretKey}")
    private String secretKey;
    //存储桶所属地域
    @Value("${txyun.obs.region}")
    private String region;
    //存储桶空间名称
    @Value("${txyun.obs.bucketName}")
    private String bucketName;
    //存储桶访问域名
    @Value("${txyun.obs.url}")
    private String url;
    @Value("${txyun.obs.prefix}")
    //上传文件前缀路径(eg:/images/)
    private String prefix;
    public  String upload(File file) {
        //生成唯一文件名
        String newFileName = generateUniqueName(file.getName());
        //文件在存储桶中的key
        String key = prefix+newFileName;
        //声明客户端
        COSClient cosClient=null;
        try {
            //初始化用户身份信息(secretId,secretKey)
            COSCredentials cosCredentials = new BasicCOSCredentials(secretId, secretKey);
            //设置bucket的区域
            ClientConfig clientConfig = new ClientConfig(new Region(region));
            //生成cos客户端
            cosClient = new COSClient(cosCredentials, clientConfig);
            //创建存储对象的请求
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, file);
            //执行上传并返回结果信息
            PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
            return url+key;
        } catch (CosClientException e) {
            e.printStackTrace();
        } finally {
            cosClient.shutdown();
        }
        return null;
    }

    /**
     * upload()重载方法
     * @param multipartFile
     * @return 上传文件在存储桶的链接
     */
    public String upload(MultipartFile multipartFile) {
        System.out.println(multipartFile);
        //生成唯一文件名
        String newFileName = generateUniqueName(multipartFile.getOriginalFilename());
        //文件在存储桶中的key
        String key = prefix+newFileName;
        //声明客户端
        COSClient cosClient = null;
        //准备将MultipartFile类型转为File类型
        File file = null;
        try {
            //生成临时文件
            file = File.createTempFile("temp",null);
            //将MultipartFile类型转为File类型
            multipartFile.transferTo(file);
            //初始化用户身份信息(secretId,secretKey)
            COSCredentials cosCredentials = new BasicCOSCredentials(secretId, secretKey);
            //设置bucket的区域
            ClientConfig clientConfig = new ClientConfig(new Region(region));
            //生成cos客户端
            cosClient = new COSClient(cosCredentials, clientConfig);
            //创建存储对象的请求
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, file);
            //执行上传并返回结果信息
            PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
            return url+key;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            cosClient.shutdown();
        }
        return null;
    }

    /**
     * 根据UUID生成唯一文件名
     * @param originalName
     * @return
     */
    public String generateUniqueName(String originalName) {
        return UUID.randomUUID() + originalName.substring(originalName.lastIndexOf("."));
    }
}


