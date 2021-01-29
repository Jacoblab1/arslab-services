package controllers;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;

import com.amazonaws.HttpMethod;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSSessionCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.Bucket;

import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;

import services.DatabaseSelectService;



import java.util.List;




public class S3Controller {
	
	private AWSCredentials credentials;
	private AmazonS3 s3client;
	private String bucketName;
	
	public S3Controller() {
		// These credentials should normally be stored as environment variables for increased security 
		credentials = new BasicAWSCredentials(
				 System.getenv("AWS_ACCESS_KEY"), 
				  System.getenv("AWS_SECRET_KEY")
				);
		
		System.out.println(System.getenv("AWS_ACCESS_KEY"));
		System.out.println(System.getenv("AWS_SECRET_KEY"));
		//"AKIAR4NSFYC2XMDYZCZS", 					
		//  "AXGOsQbzXeBi2UUz+D47w1aAake9UCnRx/uomOxr"
		
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
	
	public byte[] test() {
		
		S3Object s3object = s3client.getObject(bucketName, "simulation_results/ABP_output_messages.txt");
		S3ObjectInputStream inputStream = s3object.getObjectContent();
		DatabaseSelectService service = new DatabaseSelectService();
		ArrayList<HashMap<String,String>> files = service.getModelFiles(70);
		
		try {
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		    ZipOutputStream zip = new ZipOutputStream(byteArrayOutputStream);
		    InputStream is;
		    for(int i = 0; i < files.size(); i++) {
				//System.out.println(files.get(i).get("location"));
				String location = getObjectUrl(files.get(i).get("location"));
				URL url = new URL(location);
		        is = url.openStream();
		       // Path pathInZipfile = zipfs.getPath((files.get(i).get("name") + files.get(i).get("type")));
		        zip.putNextEntry(new ZipEntry(files.get(i).get("location")));
		        int length;

		        byte[] b = new byte[2048];

		        while((length = is.read(b)) > 0) {
		            zip.write(b, 0, length);
		        }
		        zip.closeEntry();
		        is.close();
		    }
		    zip.close();
		    return byteArrayOutputStream.toByteArray();
		   
		    
		}catch(Exception e) {
			
		}
		
		return null;
	}

}
