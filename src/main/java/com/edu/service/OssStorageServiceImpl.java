package com.edu.service;

import cn.hutool.core.date.DateTime;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.edu.config.OssProperties;
import com.edu.error.StorageException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.UUID;

@Service
public class OssStorageServiceImpl implements StorageService {

    @Resource
    private OssProperties ossProperties;

    public String store(MultipartFile file) {
        if (file.isEmpty()) {
            throw new StorageException("Failed to store empty file.");
        }
        OSS ossClient = null;
        try {
            // 创建OSS实例。
            ossClient = new OSSClientBuilder().build(ossProperties.getEndpoint(), ossProperties.getAccessKeyId(), ossProperties.getAccessKeySecret());
            String fileName = file.getOriginalFilename();
            //1 在文件名称里面添加随机唯一的值
            String uuid = UUID.randomUUID().toString().replaceAll("-","");
            // yuy76t5rew01.jpg
            fileName = uuid+ fileName;
            String datePath = new DateTime().toString("yyyy/MM/dd");
            fileName = datePath+"/"+fileName;
            ossClient.putObject(ossProperties.getBucketName(), fileName , file.getInputStream());
            return getOssFileUrl(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            ossClient.shutdown();
        }
        return null;
    }

    private String getOssFileUrl(String fileName){
        String url = "https://"+ ossProperties.getBucketName()+"."+ ossProperties.getEndpoint()+"/"+fileName;
        return url;
    }

}