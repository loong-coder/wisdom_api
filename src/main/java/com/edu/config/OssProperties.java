package com.edu.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "aliyun.oss")
@Data
@NoArgsConstructor
public class OssProperties {

    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;



//    public static void main(String[] args) {
//        OSS ossClient = new OSSClientBuilder().build("oss-cn-beijing.aliyuncs.com", "LTAI5tAkAxjCtPa34wGtC78p", "00wDBobGttNVWKvW20CV0KNshAC1vd");
//        // 填写Bucket名称，例如examplebucket。
//        String bucketName = "wisdom-oss";
//        // 填写Object完整路径，例如exampledir/exampleobject.txt。Object完整路径中不能包含Bucket名称。
//        String objectName = "exampledir/exampleobject.txt";
//        try {
//
//            // 填写字符串。
//            String content = "Hello OSS";
//
//            // 创建PutObjectRequest对象。
//            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, new ByteArrayInputStream(content.getBytes()));
//
//            // 如果需要上传时设置存储类型和访问权限，请参考以下示例代码。
//            // ObjectMetadata metadata = new ObjectMetadata();
//            // metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
//            // metadata.setObjectAcl(CannedAccessControlList.Private);
//            // putObjectRequest.setMetadata(metadata);
//
//            // 上传字符串。
//            ossClient.putObject(putObjectRequest);
//        } catch (OSSException oe) {
//            System.out.println("Caught an OSSException, which means your request made it to OSS, "
//                    + "but was rejected with an error response for some reason.");
//            System.out.println("Error Message:" + oe.getErrorMessage());
//            System.out.println("Error Code:" + oe.getErrorCode());
//            System.out.println("Request ID:" + oe.getRequestId());
//            System.out.println("Host ID:" + oe.getHostId());
//        } catch (ClientException ce) {
//            System.out.println("Caught an ClientException, which means the client encountered "
//                    + "a serious internal problem while trying to communicate with OSS, "
//                    + "such as not being able to access the network.");
//            System.out.println("Error Message:" + ce.getMessage());
//        } finally {
//            if (ossClient != null) {
//                ossClient.shutdown();
//            }
//        }
//    }
}
