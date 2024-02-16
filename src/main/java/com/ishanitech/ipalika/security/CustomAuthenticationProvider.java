package com.ishanitech.ipalika.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.ishanitech.ipalika.converter.impl.UserConverter;
import com.ishanitech.ipalika.model.AuthException;
import com.ishanitech.ipalika.model.User;
import com.ishanitech.ipalika.service.UserService;


/**
 * {@code CustomAuthenticationProvicer} is a customized authentication provider class
 * which does the authentication based on {@code String username} and {@code String password}
 * @author Umesh Bhujel
 * @since 1.0
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
	private PasswordEncoder passwordEncoder;
	private UserService userService;
	
	public CustomAuthenticationProvider(PasswordEncoder passwordEncoder, UserService userService) {
		this.passwordEncoder = passwordEncoder;
		this.userService = userService;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
		User user;
		try {
			user = userService.getUserByUsername(token.getName());
			if(passwordEncoder.matches(token.getCredentials().toString(), user.getPassword())) {
				UserConverter userConverter = new UserConverter();
				CustomUserDetails loggedInUser = new CustomUserDetails(userConverter.fromEntity(user));
				authentication = new UsernamePasswordAuthenticationToken(loggedInUser, null, loggedInUser.getAuthorities());
				return authentication;
			} else {
				throw new BadCredentialsException("Incorrect Credentials!");
			}
		} catch(UsernameNotFoundException ex) {
			throw new AuthException(ex.getMessage());
		}
		
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.equals(authentication);
	}

}
