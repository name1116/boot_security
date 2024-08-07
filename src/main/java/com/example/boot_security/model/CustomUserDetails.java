package com.example.boot_security.model;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import lombok.Getter;



@Getter
public class CustomUserDetails implements UserDetails, OAuth2User {
	private String username;
	private String password;
	private String email;
	private String role;
	private Long memberId;
	private Map<String, Object> attributes;
	private String name;
	
	public CustomUserDetails(Member member) {
		this.username = member.getUsername();
		this.email = member.getEmail();
		this.password = member.getPassword();
		this.role = member.getRole();
		this.memberId = member.getId();
	}
	
	public CustomUserDetails(OAuth2User user) {
		attributes = user.getAttributes();
		username = (String) attributes.get("login");
		email = (String) attributes.get("email");
		name = (String) attributes.get("login");
		// password가 없음 (OAuth2)
		role = "GITHUB";
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority("ROLE_" + role));
	}
}
