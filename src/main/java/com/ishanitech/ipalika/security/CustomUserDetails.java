package com.ishanitech.ipalika.security;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ishanitech.ipalika.dto.UserDTO;

import lombok.Data;
import lombok.ToString;

/**
 * {@code CustomUserDetails} gives the information about the 
 * currently loggedin user or authenticated entity.
 * This class also holds the authorities of the currently authenticated user or entity.
 * @author Umesh Bhujel <yoomesbhujel@gmail.com>
 * @since 1.0
 */
@Data
@ToString
public class CustomUserDetails implements UserDetails{
	private static final long serialVersionUID = 6534708822085674206L;
	private UserDTO user;
	private final List<GrantedAuthority> authorities;
	
	public CustomUserDetails(UserDTO user) {
		super();
		this.user = user;
		this.authorities = user.getRoles().stream()
				.map(authority -> {
					return new SimpleGrantedAuthority(String.format("ROLE_%s", authority));
				}).collect(Collectors.toList());
	}

	@JsonIgnore
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@JsonIgnore
	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public String getUsername() {
		return this.user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return this.user.isExpired();
	}

	@Override
	public boolean isAccountNonLocked() {
		return this.user.isLocked();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return false;
	}

	@Override
	public boolean isEnabled() {
		return this.user.isEnabled();
	}

	public UserDTO getUser() {
		return user;
	}

}
