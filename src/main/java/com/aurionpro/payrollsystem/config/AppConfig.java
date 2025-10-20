package com.aurionpro.payrollsystem.config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.aurionpro.payrollsystem.service.authentication.CapatchaService;
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
	
	@Bean
	CapatchaService capatchaService() {
		return new CapatchaService();
	}
	
	@Bean
	WebClient webClient() {
		return WebClient.builder().build();
	}
	
//	@Configuration
//	public class CorsConfig implements WebMvcConfigurer {
//	    @Override
//	    public void addCorsMappings(CorsRegistry registry) {
//	        registry.addMapping("/**")
//	                .allowedOrigins("http://localhost:4200")
//	                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
//	                .allowedHeaders("*")
//	                .allowCredentials(true);
//	    }
//	}

}
