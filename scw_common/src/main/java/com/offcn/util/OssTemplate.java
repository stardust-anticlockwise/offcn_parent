package com.offcn.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import lombok.Data;
import lombok.ToString;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@ToString
@Data
public class OssTemplate {

    private String endpoint;
    private String bucketDomain;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;

    public String upload (InputStream inputStream,String fileName){
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
        String foldname =sdf.format(new Date());
        fileName = UUID.randomUUID().toString().replace("-","")+"_"+fileName;
        OSS ossClient =new OSSClientBuilder().build(endpoint,accessKeyId,accessKeySecret);
        ossClient.putObject(bucketName,"pic/"+foldname+"/"+fileName,inputStream);
        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ossClient.shutdown();
        String url ="https://"+bucketDomain+"/pic/"+foldname+"/"+fileName;
        System.out.println("上传文件访问路径:"+url);
        return url;
    }
}
