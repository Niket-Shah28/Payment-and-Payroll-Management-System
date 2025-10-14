package com.aurionpro.payrollsystem.config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.aurionpro.payrollsystem.service.payment.PayslipService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Configuration
public class AppConfig {
	
	@Value("${cloud_name}")
	private String cloudName;

	@Value("${cloudinary_api_key}")
	private String apiKey;

	@Value("${cloudinary_api_secret}")
	private String apiSecret;
	
	@Bean
	ModelMapper mapper() {
		return new ModelMapper();
	}
	
	@Bean
    Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
            "cloud_name", cloudName,
            "api_key", apiKey,
            "api_secret", apiSecret
        ));
    }
	
	@Bean
	PayslipService payslipService() {
		return new PayslipService();
	}
}
