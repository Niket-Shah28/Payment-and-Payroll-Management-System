package com.aurionpro.payrollsystem.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {
	
	@Value("${app.jwt-secret}")
	private String jwtSecret;
	
	@Value("${app-jwt-expiration-milliseconds}")
	private Long jwtExpirationTime;
	
	public String generateToken(Authentication authentication) {
		String username = authentication.getName();
		Date currentDate = new Date();
		
		Date expirationTime = new Date(currentDate.getTime() + jwtExpirationTime);
		
		String token = Jwts.builder().claims()
					   .subject(username).issuedAt(new Date(System.currentTimeMillis()))
					   .expiration(expirationTime).and()
					   .claim("role", authentication.getAuthorities()).signWith(key()).compact();
		
		return token;
	}
	
	private SecretKey key() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
	}
	
	public String getUsername(String token) {
		Claims claims = Jwts.parser().verifyWith(key()).build().parseSignedClaims(token).getPayload();
		
		String username = claims.getSubject();
		
		return username;
	}
	
	public boolean validateToken(String token){
        try{
        	Jwts.parser().verifyWith(key()).build().parse(token);
            return true;
        } catch (MalformedJwtException ex) {
            throw new JwtException("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            throw new JwtException("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            throw new JwtException("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            throw new JwtException("JWT claims string is empty.");
        }
        catch(Exception e)
        {
        	throw new BadCredentialsException("Invalid Credentials");
        }
    }
}
