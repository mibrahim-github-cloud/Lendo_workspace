package com.genesyslab.user.api.security;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.genesyslab.user.api.service.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter{
	
	Logger logger = LogManager.getLogger(WebSecurity.class);
	
	private UserService userService;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	public WebSecurity(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
		super();
		this.userService = userService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		logger.debug("HTTP REQUEST "+http.toString());
		System.out.println("HTTP REQUEST "+http.toString());
		http.headers().frameOptions().disable();
		
		http.authorizeRequests()
		.antMatchers("/h2-console/**").permitAll()
		//.antMatchers("/","/**").permitAll()
		.antMatchers(HttpMethod.POST, "/users/login").permitAll()
		.antMatchers(HttpMethod.POST, "/users").permitAll()
		//.antMatchers(HttpMethod.POST, "/posts").permitAll()
		.anyRequest().authenticated()
		.and()
		.addFilter(new AuthValidationFilter(authenticationManager()))
		.addFilter(getAuthenticationFilter());
		
		
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		
	}
	
	private AuthenticationFilter getAuthenticationFilter() throws Exception {
		
		AuthenticationFilter authenticationFilter = new AuthenticationFilter(userService, authenticationManager());
		//authenticationFilter.setAuthenticationManager(authenticationManager());
		authenticationFilter.setFilterProcessesUrl("/users/login");
		return authenticationFilter;
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
	}

	
}
