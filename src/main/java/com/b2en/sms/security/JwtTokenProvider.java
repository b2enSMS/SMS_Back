package com.b2en.sms.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    private String secretKey = "b2ensms!";

    private long tokenValidMilisecond = 1000L * 60 * 60 * 8;
    private final String TOKEN_HEADER = "Authorization";

    private final UserDetailsService userDetailsService;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    // Jwt 토큰 생성
    public String createToken(String email, List<GrantedAuthority> roles) {
        log.debug("createToken:email:{}", email);
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("roles", roles);
        Date now = new Date();
        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidMilisecond))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
        log.debug("createToken:token:{}", token);
        return token;
    }

    // Jwt 토큰으로 인증 정보를 조회
    public Authentication getAuthentication(String token) {
        log.debug("getAuthentication:token:{}", token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getEmail(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private String getEmail(String token) {
        log.debug("getEmail:token:{}", token);
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest req) {
        return req.getHeader(TOKEN_HEADER);
    }

    public boolean validateToken(String jwtToken) {
        log.debug("validateToken:jwtToken:{}", jwtToken);
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}