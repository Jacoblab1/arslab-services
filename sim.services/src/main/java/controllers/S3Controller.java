package controllers;

import java.io.File;
import java.net.URL;

import com.amazonaws.HttpMethod;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;

public class S3Controller {
	
	private AWSCredentials credentials;
	private AmazonS3 s3client;
	private String bucketName;
	
	public S3Controller() {
		// These credentials should normally be stored as environment variables for increased security 
		credentials = new BasicAWSCredentials(
				  "AKIAR4NSFYC2XMDYZCZS", 
				  "AXGOsQbzXeBi2UUz+D47w1aAake9UCnRx/uomOxr"
				);
		
		// Create the S3 Client object
		s3client = AmazonS3ClientBuilder
				  .standard()
				  .withCredentials(new AWSStaticCredentialsProvider(credentials))
				  .withRegion(Regions.US_EAST_2)
				  .build();
		
		// This is the name of the bucket files are stored in
		bucketName = "ars-lab";
	}
	
	/** Upload a file to the S3 Bucket
	 * 
	 * @param key Key to access the file
	 * @param file The actual file to be uploaded
	 */
	public void uploadObject(String key, File file) {
		s3client.putObject(
				  bucketName, 
				  key, 
				  file
				);
	}
	
	public String getObjectUrl(String key) {
		// Set the presigned URL to expire after one hour.
        java.util.Date expiration = new java.util.Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 1000 * 60 * 60;
        expiration.setTime(expTimeMillis);

        // Generate the presigned URL.
        System.out.println("Generating pre-signed URL.");
        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucketName, key)
                        .withMethod(HttpMethod.GET)
                        .withExpiration(expiration);
        URL url = s3client.generatePresignedUrl(generatePresignedUrlRequest);

        System.out.println("Pre-Signed URL: " + url.toString());
        
        return url.toString();
	}
}
