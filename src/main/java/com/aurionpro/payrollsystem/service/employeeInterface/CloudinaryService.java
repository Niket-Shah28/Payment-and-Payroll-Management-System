package com.aurionpro.payrollsystem.service.employeeInterface;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Service
public class CloudinaryService {

	@Autowired
	private  Cloudinary cloudinary;
	
	public CloudinaryService(
	        @Value("${cloud_name}") String cloudName,
	        @Value("${cloudinary_api_key}") String apiKey,
	        @Value("${cloudinary_api_secret}") String apiSecret) {

	        this.cloudinary = new Cloudinary(Map.of(
	            "cloud_name", cloudName,
	            "cloudinary_api_key", apiKey,
	            "cloudinary_api_secret", apiSecret
	        ));
	    }
	
	public String uploadFile(MultipartFile file) {
        try {
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            return (String) uploadResult.get("secure_url");
        } catch (IOException e) {
            throw new RuntimeException("Image upload failed", e);
        }
    }
}
