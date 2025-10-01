package com.aurionpro.payrollsystem.security;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.aurionpro.payrollsystem.entity.authentication.Role;
import com.aurionpro.payrollsystem.entity.authentication.User;
import com.aurionpro.payrollsystem.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String referenceId) throws UsernameNotFoundException {
		User user = userRepository.findById(referenceId)
				                  .orElseThrow(()-> new BadCredentialsException("Username Not found"));
		
		Role role = user.getRole();
		Set<GrantedAuthority> authorities = new HashSet<>();
		SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getRoleName());
		
		authorities.add(grantedAuthority);
		
		Long userId;

		if (user.getEmployee() != null) {
		    userId = user.getEmployee().getEmployeeId();
		} else if (user.getOrganization() != null) {
		    userId = user.getOrganization().getOrganizationId();
		} else {
		    userId = user.getAdmin().getAdminId();
		}
		
		return new org.springframework.security.core.userdetails.User(userId.toString(), 
				user.getPassword(), authorities);
	}
	
}
