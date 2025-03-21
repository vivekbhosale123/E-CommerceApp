
package com.vivek.Vivek.Ecommerce.project.Services;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import io.jsonwebtoken.io.IOException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AwsS3Service {

	private final String bucketName="vivek-commerce";
	
	@Value("${aws.s3.access}")
	private String awsS3AccessKey;
	
	@Value("${aws.s3.secrete}")
	private String awsS3SecreteKey;
	
	public String saveImageToS3(MultipartFile photo) throws java.io.IOException
	{
		try {
			String s3FileName = photo.getOriginalFilename();
			
			// create aws credentials using the access and secret key
			
			BasicAWSCredentials awsCredentials=new BasicAWSCredentials(awsS3AccessKey, awsS3SecreteKey);
		
			// create an s3 client with config credentials and region
			
			AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
					.withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
					.withRegion(Regions.EU_NORTH_1)
					.build();
			
			// get input stream from photo
			InputStream inputStream= photo.getInputStream();
			
			// set metadata for object
			ObjectMetadata metadata=new ObjectMetadata();
			metadata.setContentType("image/avif");
			
			// create a put request to upload the image to s3
			PutObjectRequest objectRequest=new PutObjectRequest(bucketName, s3FileName, inputStream, metadata);
	        s3Client.putObject(objectRequest);
			
			return "https://"+bucketName+".s3.eu-north-1.amazonaws.com/"+s3FileName;
		}
		catch (IOException e) {
			e.getStackTrace();
			throw new RuntimeException("unable to upload the image to S3 Bucket "+e.getMessage());
			
		}
	}
}
