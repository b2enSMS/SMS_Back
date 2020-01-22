package com.b2en.sms.security;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.b2en.sms.entity.login.Login;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenProvider {

	private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

	public String generateToken(Login login) {

		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + 604800000);

		return Jwts.builder().setSubject(login.getUsername()).setIssuedAt(new Date())
				.setExpiration(expiryDate).signWith(SignatureAlgorithm.HS512, "JWTSuperSecretKey").compact();
	}

	public Long getUserIdFromJWT(String token) {
		Claims claims = Jwts.parser().setSigningKey("JWTSuperSecretKey").parseClaimsJws(token).getBody();

		return Long.parseLong(claims.getSubject());
	}

	public boolean validateToken(String authToken) {
		try {
			Jwts.parser().setSigningKey("JWTSuperSecretKey").parseClaimsJws(authToken);
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return false;
	}
}