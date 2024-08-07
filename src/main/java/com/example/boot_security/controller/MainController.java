package com.example.boot_security.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.boot_security.model.CustomUserDetails;
import com.example.boot_security.model.Member;
import com.example.boot_security.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {
	private final MemberService memberService;
	
	@GetMapping
	public String welcome() {
		return "welcome";
	}
	
	@GetMapping("/main")
	public String main(@AuthenticationPrincipal CustomUserDetails user, Model model) {
		model.addAttribute("user", user);
		return "main";
	}
	
	@GetMapping("/join")
	public String joinForm(Model model) {
		model.addAttribute("member", new Member());
		return "join";
	}
	
	@PostMapping("/join")
	public String join(@ModelAttribute Member member) {
		memberService.save(member);
		return "redirect:/";
	}
	
	
	@PostMapping("/change")
	public String change(@AuthenticationPrincipal CustomUserDetails user, @RequestParam String role) {
		memberService.changeRole(user.getUsername(), role);
		return "redirect:/logout";
	}
	
	@GetMapping("/premium/{title}")
	@ResponseBody
	public String premium(@PathVariable String title) {
		return title;
	}
	
	@GetMapping("/user/{title}")
	@ResponseBody
	public String user(@PathVariable String title) {
		return title;
	}
	
	@GetMapping("/free/{title}")
	@ResponseBody
	public String free(@PathVariable String title) {
		return title;
	}
	
	@GetMapping("/p1")
	@ResponseBody
	public String p1() {
		memberService.forPremium1();
		return "for premium1";
	}
	
	@GetMapping("/p2")
	@ResponseBody
	public String p2() {
		memberService.forPremium2();
		return "for premium2";
	}
	
	@GetMapping("/o1")
	@ResponseBody
	public String o1() {
		memberService.forPremiumOrUser1();
		return "for premium or user 1";
	}
	
//	@GetMapping("/o2")
//	@ResponseBody
//	public String o2() {
//		memberService.forPremiumOrUser2();
//		return "for premium or user 2";
//	}
	
	@GetMapping("/login")
	public String loginForm() {
		return "login";
	}
	
	@GetMapping("/logout")
	public String logout(
			HttpServletRequest request,
			HttpServletResponse response
	) {
		new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
		return "redirect:/logout";
	}
	
	
	
}




