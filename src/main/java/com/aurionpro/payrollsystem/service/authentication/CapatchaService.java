package com.aurionpro.payrollsystem.service.authentication;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

public class CapatchaService {
	
	@Value("${CAPATCHA_SECRET_KEY}")
	private String capatchaSecretKey;
	
	private String capatchaVerificationUrl = "https://www.google.com/recaptcha/api/siteverify";
	
	@Autowired
	private WebClient webClient;
	
	public boolean verifyCaptcha(String captchaResponse) {
		MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
		formData.add("secret", capatchaSecretKey);
		formData.add("response", captchaResponse);

        @SuppressWarnings("unchecked")
        Map<String, Object> response = webClient.post()
							        .uri(capatchaVerificationUrl)
							        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
							        .bodyValue(formData)
							        .retrieve()
							        .bodyToMono(Map.class)
							        .onErrorResume(e -> Mono.just(Map.of("success", false)))
							        .block();
        return response != null && Boolean.TRUE.equals(response.get("success"));
    }

}
