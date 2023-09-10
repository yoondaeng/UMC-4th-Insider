package com.example.umc_insider.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import com.example.umc_insider.domain.Exchanges;
import com.example.umc_insider.domain.Goods;
import com.example.umc_insider.domain.Users;
import com.example.umc_insider.repository.GoodsRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;



@Service
public class S3Service {
    @Bean
    public AmazonS3 initializeAmazonS3() {
        return null;
    }
    private final String bucketName = "umcinsider";
    private final AmazonS3 s3Client;
    private String url;
    private final GoodsRepository goodsRepository;
    public S3Service(AmazonS3 s3Client, GoodsRepository goodsRepository) {
        this.s3Client = s3Client;
        this.goodsRepository = goodsRepository;
    }

    public String uploadFileToS3(MultipartFile file, Goods goods) {
        String keyName = "goods/" + goods.getId() + "_" + file.getOriginalFilename();

        try {
            byte[] bytes = file.getBytes();

            // 메타데이터 객체를 생성하고 콘텐츠 타입을 설정
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(bytes.length);
            metadata.setContentType(file.getContentType());

            s3Client.putObject(new PutObjectRequest(bucketName, keyName, new ByteArrayInputStream(bytes), new ObjectMetadata()));
            url = s3Client.getUrl(bucketName, keyName).toString();
            return url;
        } catch (IOException e) {
            throw new RuntimeException("File loading failed.", e);
        }
    }

    public String uploadProfileS3(MultipartFile file, Users users) {
        String keyName = "users/" + users.getId() + "_" + file.getOriginalFilename();

        try {
            byte[] bytes = file.getBytes();

            // 메타데이터 객체를 생성하고 콘텐츠 타입을 설정
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(bytes.length);
            metadata.setContentType(file.getContentType());

            s3Client.putObject(new PutObjectRequest(bucketName, keyName, new ByteArrayInputStream(bytes), new ObjectMetadata()));
            url = s3Client.getUrl(bucketName, keyName).toString();
            return url;
        } catch (IOException e) {
            throw new RuntimeException("File loading failed.", e);
        }
    }

    public String uploadExchangesS3(MultipartFile file, Exchanges exchanges) {
        String keyName = "exchanges/" + exchanges.getId() + "_" + file.getOriginalFilename();

        try {
            byte[] bytes = file.getBytes();

            // 메타데이터 객체를 생성하고 콘텐츠 타입을 설정
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(bytes.length);
            metadata.setContentType(file.getContentType());

            s3Client.putObject(new PutObjectRequest(bucketName, keyName, new ByteArrayInputStream(bytes), new ObjectMetadata()));
            url = s3Client.getUrl(bucketName, keyName).toString();
            return url;
        } catch (IOException e) {
            throw new RuntimeException("File loading failed.", e);
        }
    }

}