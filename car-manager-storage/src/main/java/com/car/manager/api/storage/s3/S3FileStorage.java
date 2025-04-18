package com.car.manager.api.storage.s3;

import com.car.manager.core.storage.FileStorage;
import com.car.manager.core.storage.FileStorageException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@Component
public class S3FileStorage implements FileStorage {

    private final S3Client s3Client;

    private final String bucketName;

    public S3FileStorage(
            @Value("${com.car.manager.storage.aws.s3.bucket-name}") String bucketName,
            S3Client s3Client
    ){
        this.s3Client = s3Client;
        this.bucketName = bucketName;
    }

    @Override
    public URL upload(InputStream inputStream, String folder, String identifier) throws FileStorageException {
        String key = folder + "/" + identifier;
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        try{
            PutObjectResponse response = s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(inputStream, inputStream.available()));
        }catch (SdkClientException | IOException ex){
            throw new FileStorageException();
        }
        return s3Client.utilities().getUrl((resource) -> resource.bucket(bucketName).key(key));
    }

    @Override
    public void delete(String folderIdentifier) throws FileStorageException{
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder().bucket(bucketName).key(folderIdentifier).build();

        try{
            s3Client.deleteObject(deleteObjectRequest);
        }catch (SdkClientException ex){
            throw new FileStorageException();
        }
    }
}
