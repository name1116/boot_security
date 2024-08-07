package com.example.boot_security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.boot_security.service.CustomOAuth2UserService;

@Configuration
@EnableWebSecurity // Spring security 관련된 설정을 활성화
@EnableMethodSecurity // 메서드 보안 처리
public class SecurityConfig {

	private final CustomOAuth2UserService customOAuth2UserService;

	public SecurityConfig(CustomOAuth2UserService customOAuth2UserService) {
		this.customOAuth2UserService = customOAuth2UserService;
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http
				// 요청들(브라우저 등등)에 대해서 어떤 권한을 요구할 것인가를 결정
				.authorizeHttpRequests((requests) -> requests
						// 특정 패턴("/")에 있는 여러가지 데이터를 받아옴 , 모든 사용자에 대해 허용함
						.requestMatchers("/", "/join").permitAll().requestMatchers("/premium/**").hasRole("premium")
						.requestMatchers("/user/**").hasAnyRole("premium", "user").anyRequest().authenticated() // 하지만
																												// 나머지에
																												// 대해서는
																												// 인증이
																												// 필요하게
																												// 만듬
				).formLogin((form) -> form.defaultSuccessUrl("/main").loginPage("/login").permitAll() // 별도로 지정하지 않으면 /login이라는 디폴트를 가짐
				)
				.oauth2Login(oauth2login -> oauth2login
						.loginPage("/login")
						.userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint.userService(customOAuth2UserService))
						.defaultSuccessUrl("/main").permitAll())
				.logout((logout) -> logout
						.logoutUrl("/logout")
						.logoutSuccessUrl("/login")
						.invalidateHttpSession(true)
						.deleteCookies("JSESSIONID")
						.permitAll());
		// 모든 접근 요청에 대해서 로그인 필요 여부를 결정지음
		return http.build(); // 위 필터에 대한 적용
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
