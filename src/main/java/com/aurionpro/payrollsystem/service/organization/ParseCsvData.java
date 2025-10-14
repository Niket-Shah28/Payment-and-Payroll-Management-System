package com.aurionpro.payrollsystem.service.organization;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.aurionpro.payrollsystem.dto.employee.EmployeeUploadDataDto;
import com.opencsv.bean.CsvToBeanBuilder;

public class ParseCsvData {
	

	public static List<EmployeeUploadDataDto> parseCsv(MultipartFile file){
	    try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
	        return new CsvToBeanBuilder<EmployeeUploadDataDto>(reader)
	                .withType(EmployeeUploadDataDto.class)
	                .withIgnoreLeadingWhiteSpace(true)
	                .withSkipLines(0) // don’t skip header line
	                .withSeparator(',')
	                .build()
	                .parse();
	    }catch (IOException e) {
	        throw new RuntimeException("Failed to read CSV", e);
	    }
	}

}
