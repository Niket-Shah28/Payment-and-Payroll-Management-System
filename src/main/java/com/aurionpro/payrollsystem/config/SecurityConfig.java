package com.aurionpro.payrollsystem.config;

import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.aurionpro.payrollsystem.security.JwtAuthenticationEntryPoint;
import com.aurionpro.payrollsystem.security.JwtAuthenticationFilter;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SecurityConfig {
	@Autowired
	private JwtAuthenticationFilter authenticationFilter;
	@Autowired
	private JwtAuthenticationEntryPoint authenticationEntryPoint;
	
	@Bean
    OpenAPI customizeOpenAPI() {
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components().addSecuritySchemes("bearerAuth",
                        new SecurityScheme()
                                .name("bearerAuth")
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));
    }
 

	@Bean
	static PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
		return configuration.getAuthenticationManager();
	}
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		http.csrf(csrf -> csrf.disable())
	    .cors(withDefaults())
	    .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
	    
	    .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
	    .authorizeHttpRequests(request -> request
	        // GENERAL
	        .requestMatchers("/auth/login", "/swagger-ui/**",
                    "/v3/api-docs/**",
                    "/swagger-resources/**",
                    "/swagger-resources",
                    "/webjars/**").permitAll()
	        
	        .requestMatchers(HttpMethod.POST, "/organization/requests").permitAll()
	        
	        .requestMatchers(HttpMethod.POST, "/organization/requests/documents").permitAll()
	        
	        .requestMatchers(HttpMethod.GET, "/organization/requests/pending").authenticated()
	        
	        .requestMatchers(HttpMethod.GET, "/organization/requests/*/documents").authenticated()
	        
	        .requestMatchers(HttpMethod.PUT, "/organization/requests/*").authenticated()
	        
	        .requestMatchers(HttpMethod.POST, "/organization/departments").authenticated()
	        
	        .requestMatchers(HttpMethod.POST, "/organization/businessUnits").authenticated()
	        
	        .requestMatchers(HttpMethod.POST, "/organization/employee/roles").authenticated()
	        
	        .requestMatchers(HttpMethod.POST, "/organization/employees").authenticated()
	        
	        .requestMatchers(HttpMethod.PATCH, "/organization/departments/*").authenticated()
	        
	        .requestMatchers(HttpMethod.PATCH, "/organization/businessUnits/*").authenticated()
	        
	        .requestMatchers(HttpMethod.PATCH, "/organization/employee/roles/*").authenticated()
	        
	        .requestMatchers(HttpMethod.DELETE, "/organization/departments/*").authenticated()
	        
	        .requestMatchers(HttpMethod.DELETE, "/organization/businessUnits/*").authenticated()
	        
	        .requestMatchers(HttpMethod.DELETE, "/organization/employee/roles/*").authenticated()
	        
	        .requestMatchers(HttpMethod.POST, "/organization/bankAccount").authenticated()
	        
	        .requestMatchers(HttpMethod.PATCH, "/organization/bankAccount/*").authenticated()
	        
	        .requestMatchers(HttpMethod.DELETE, "/organization/bankAccount/*").authenticated()
	        
	        .requestMatchers(HttpMethod.GET, "/organization/bankAccount").authenticated()
	        
	        .requestMatchers(HttpMethod.POST, "/organization/bankAccount/*/deposit").authenticated()
	        
	        .requestMatchers(HttpMethod.POST, "/organization/paymentRequest/*").permitAll()
	        
	        .requestMatchers(HttpMethod.GET, "/banks/transactions").authenticated()
	        
	        .requestMatchers(HttpMethod.GET, "/banks/organizations/info").authenticated()
	        
	        .requestMatchers(HttpMethod.GET, "/banks/transactions").authenticated()
	        
	        .requestMatchers(HttpMethod.GET, "/banks/payments/requests").authenticated()
	        
	        .requestMatchers(HttpMethod.PATCH, "/organization/employees/*/salary").authenticated()
	        
	        .requestMatchers(HttpMethod.PUT, "/organization/employees/*/designation").authenticated()
	        
	        .requestMatchers(HttpMethod.DELETE, "/organization/employees/*").authenticated()
	        
	        //.requestMatchers(HttpMethod.PUT, "/banks/payments/requests").authenticated()
	        
	        
	        //Employee Interface Features
	        
	        //organization side's ticket
	        .requestMatchers(HttpMethod.GET, "/organization/tickets").authenticated()
	        .requestMatchers(HttpMethod.POST, "/organization/ticket/respond").authenticated()
	        .requestMatchers(HttpMethod.PATCH, "/organization/ticket/close").authenticated()
	        
	        
	        .requestMatchers(HttpMethod.GET, "/employee/address").permitAll()
	        .requestMatchers(HttpMethod.PATCH, "/employee/address").permitAll()
	        .requestMatchers(HttpMethod.GET, "/employee/contactdetails").permitAll()
	        .requestMatchers(HttpMethod.PATCH, "/employee/contactdetails").permitAll()
	        .requestMatchers(HttpMethod.GET, "/employee/profiledetails").permitAll()
	        .requestMatchers(HttpMethod.PATCH, "/employee/profiletdetails").permitAll()
	        
	        .requestMatchers(HttpMethod.GET, "/employee/bank-account").authenticated()
	        .requestMatchers(HttpMethod.PATCH, "/employee/bank-account/{accountId}").authenticated()
	        .requestMatchers(HttpMethod.GET, "/employee/bank-account/{accountId}").authenticated()
	        .requestMatchers(HttpMethod.GET, "/employee/bank-account/active").authenticated()
	        .requestMatchers(HttpMethod.GET,"/employee/bank-info").authenticated()
	        .requestMatchers(HttpMethod.GET, "/employee/bank-info/{bankInfoId}").authenticated()
	        .requestMatchers(HttpMethod.GET, "/employee/bank-info/{employeeId}").authenticated()
	        .requestMatchers(HttpMethod.PATCH, "/employee/bank-info/{bankInfoId}").authenticated()
	        .requestMatchers(HttpMethod.GET, "/employee/bank-info/active").authenticated()
	        
	        
	        
	        
	     .requestMatchers(HttpMethod.POST, "/employee/ticket").authenticated()
	     .requestMatchers(HttpMethod.GET, "/employee/ticket").authenticated()
	     .requestMatchers(HttpMethod.GET, "/employee/ticket/{ticketId}").authenticated()
	     .requestMatchers(HttpMethod.GET, "/employee/ticket/open").authenticated()
	     .requestMatchers(HttpMethod.GET, "/employee/ticket/close").authenticated()
	     .requestMatchers(HttpMethod.POST, "/employee/ticket/response").authenticated()
	     .requestMatchers(HttpMethod.PATCH, "/employee/ticket/{ticketId}").authenticated()
	        
	        
	        .requestMatchers(HttpMethod.GET, "/employee/payslips").authenticated()
	        .requestMatchers(HttpMethod.GET, "/employee/payslips/summaries").authenticated()
	        .requestMatchers(HttpMethod.GET, "/employee/payslips/{payslipId}").authenticated()
	        .requestMatchers(HttpMethod.GET, "/employee/payslips/timeperiod/month{month}/year/{year}").authenticated()
	        .requestMatchers(HttpMethod.GET, "/employee/payslips/year/{year}").authenticated()
	        .requestMatchers(HttpMethod.GET, "/employee/payslips/latest").authenticated()
	       // .requestMatchers(HttpMethod.GET, "/employee/payslips/{payslipId}/download").authenticated()
	        
	        .requestMatchers(HttpMethod.POST, "/employee/documents/upload").authenticated()
	        .requestMatchers(HttpMethod.GET, "/employee/documents").authenticated()
	        .requestMatchers(HttpMethod.GET, "/employee/documents/{documentId}").authenticated()
	        .requestMatchers(HttpMethod.GET, "/employee/documents/type/{documentTypeId}").authenticated()
	        .requestMatchers(HttpMethod.GET, "/employee/documents/types").authenticated()
	        
	        .requestMatchers(HttpMethod.GET, "/employee/attendance").authenticated()
	        .requestMatchers(HttpMethod.POST, "/employee/attendance/mark").authenticated()
	        .requestMatchers(HttpMethod.POST, "/employee/leave/apply").authenticated()
	        .requestMatchers(HttpMethod.GET, "/employee/leave/view").authenticated()
	        .requestMatchers(HttpMethod.GET, "/employee/leave/view/manager/{managerId}").authenticated()
	        .requestMatchers(HttpMethod.POST, "/employee/leave/manager/decision").authenticated()
	        
	        
	        .requestMatchers(HttpMethod.GET, "/organization/requests/*/documents/*/view").hasAuthority("ROLE_ADMIN")
	        .requestMatchers(HttpMethod.GET, "/organization/requests/*/documents/*/download").hasAuthority("ROLE_ADMIN")
	        .requestMatchers(HttpMethod.GET, "/banks/organizations/*/documents/*/view").hasAuthority("ROLE_ADMIN")
	        .requestMatchers(HttpMethod.GET, "/banks/organizations/*/documents/*/download").hasAuthority("ROLE_ADMIN")
	        

	        // LOGOUT
	        .requestMatchers("/logout").authenticated()

	        .anyRequest().authenticated()
	    )
	    .exceptionHandling(exception -> exception.authenticationEntryPoint(authenticationEntryPoint));

		return http.build();
	}
}
