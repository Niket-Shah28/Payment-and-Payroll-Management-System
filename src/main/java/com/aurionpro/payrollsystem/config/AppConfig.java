package com.aurionpro.payrollsystem.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
	 @Bean
	    public ModelMapper modelMapper() {
	        ModelMapper mapper = new ModelMapper();

	        mapper.getConfiguration()
	              .setSkipNullEnabled(true)                // Ignores nulls during mapping
	              .setMatchingStrategy(MatchingStrategies.STRICT); // Only map exact field names

	        return mapper;
	    }
}
