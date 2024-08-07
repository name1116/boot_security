package com.example.boot_security.service;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.boot_security.model.Member;
import com.example.boot_security.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;

	public void save(Member member) {
		member.setRole("USER");
		member.setPassword(passwordEncoder.encode(member.getPassword())); // -> bcrypt으로 변환된 비밀번호,
																			// CustomUserDetailsService에서는 등록된
																			// passwordEncoder를 이용하여 해석해 사용함
		memberRepository.save(member);
	}

	public void changeRole(String username, String role) {
		Member member = memberRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("유저 없음"));

		member.setRole(role);
		memberRepository.save(member);
	}
	
	@PreAuthorize("hasRole('premium')") //해당 메서드 호출 전 권한을 검사함
	public void forPremium1() {

	}
	
	@Secured("premium")
	public void forPremium2() {

	}
	
	
	@PreAuthorize("hasAnyRole('premium', 'user')") //해당 메서드 호출 전 권한을 검사함
	public void forPremiumOrUser1() {

	}
	

}






