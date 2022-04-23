package com.genesyslab.user.api.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Jwts;

public class AuthValidationFilter extends BasicAuthenticationFilter{

	public AuthValidationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		String authorization = request.getHeader("Authorization");
		
		if(authorization == null || !authorization.startsWith("Bearer"))
		{
			chain.doFilter(request, response);
			return;
		}
		
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = getAuthentication(request);
		
		SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
		chain.doFilter(request, response);
	}
	
	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request)
	{
		String authorizationHeader = request.getHeader("Authorization");
		
		if(authorizationHeader == null)
		{
			return null;
		}
		
		String token = authorizationHeader.replace("Bearer", "");
		
		 String userId = Jwts.parser()
				 .setSigningKey("jkmlijorknlmmpkuhjnklmyg")
                 .parseClaimsJws(token)
                 .getBody()
                 .getSubject();

         if (userId == null) {
             return null;
         }
         
         return new UsernamePasswordAuthenticationToken(userId, null, new ArrayList<>());
	}
	
}
