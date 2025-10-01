package com.aurionpro.payrollsystem.service.authentication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.aurionpro.payrollsystem.dto.authentication.LoginRequestDto;
import com.aurionpro.payrollsystem.dto.authentication.LoginResponseDto;
import com.aurionpro.payrollsystem.security.JwtTokenProvider;

@Service
public class AuthServiceImpl implements AuthService {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtTokenProvider tokenProvider;
	
	private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

	@Override
	public LoginResponseDto login(LoginRequestDto dto) {
		try {
	        Authentication authentication = authenticationManager.authenticate(
	            new UsernamePasswordAuthenticationToken(dto.getReferenceId(), dto.getPassword()));
	        SecurityContextHolder.getContext().setAuthentication(authentication);
	        String token = tokenProvider.generateToken(authentication);
	        
	        LoginResponseDto response = new LoginResponseDto();
	        response.setAccessToken(token);
	        logger.info("ID: "+dto.getReferenceId()+ " has logged In");
	        return response;
	    } 
	    catch (BadCredentialsException e) {
	        throw new BadCredentialsException("Username or Password is incorrect");
	    }
	}

}
