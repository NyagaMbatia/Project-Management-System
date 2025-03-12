package com.joe.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.List;

import static com.joe.config.JwtConstant.HEADER;
import static com.joe.config.JwtConstant.SECRET_KEY;

public class JwtTokenValidator extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // retrieve the JWT from the request header
        String jwt = request.getHeader(HEADER);

        if (jwt != null){
            // Removes the "Bearer" prefix from the JWT
            String token = jwt.substring(7);

            try{
                // Generates a secret key from the JwtConstant.SECRET_KEY string
                SecretKey secretKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

                // Parses and validates the JWT using the secret key
                Claims claims = Jwts.parser()
                        .verifyWith(secretKey)
                        .build()
                        .parseSignedClaims(token)
                        .getPayload();

                // Extracts the email from the JWT claims
                String email = String.valueOf(claims.get("email"));

                // Extracts the authorities (roles) from the JWT claims
                String authorities = String.valueOf(claims.get("authorities"));

                List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);

                // Converts an authentication object representing the authenticated user
                Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, auths);

                // sets the authentication to the Spring Security Context
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (Exception ex){
                throw new BadCredentialsException("Invalid Token");
            }

            // Passes the request to the next filter chain
            filterChain.doFilter(request, response);
        }
    }
}
