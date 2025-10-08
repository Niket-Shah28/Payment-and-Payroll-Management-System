package com.aurionpro.payrollsystem.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.aurionpro.payrollsystem.service.payment.PayslipService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Configuration
public class AppConfig {
	@Bean
	ModelMapper mapper() {
		return new ModelMapper();
	}
	
	@Bean
    Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "${cloud-name}",
            "api_key", "${api-key}",
            "api_secret", "${api-secret}"
        ));
    }
	
	@Bean
	PayslipService payslipService() {
		return new PayslipService();
	}
}
