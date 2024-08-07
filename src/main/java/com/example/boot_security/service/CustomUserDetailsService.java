package com.example.boot_security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.boot_security.model.CustomUserDetails;
import com.example.boot_security.model.Member;
import com.example.boot_security.repository.MemberRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService{

	
	private final MemberRepository memberRepository;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Member member = memberRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("유저를 찾을 수 없습니다."));
//		return new User(
//					member.getUsername(),
//					member.getPassword(),
//					// List 같이 되어 있는
//					//GrantedAuthority로 넣어줘야함
//					List.of(new SimpleGrantedAuthority(member.getRole()))
//					//List<GrantedAuthority>
//				);
		return new CustomUserDetails(member);
		
	}
	
}
