package org.edupoll.controller;

import org.edupoll.model.dto.request.LoginRequestData;
import org.edupoll.model.entity.User;
import org.edupoll.securty.support.Account;
import org.edupoll.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/user")
@SessionAttributes("loginId")
public class UserController {

	private static final Logger log = LoggerFactory.getLogger(UserController.class);

	UserService userService;

	public UserController(UserService userService) {
		super();
		this.userService = userService;
	}

	@GetMapping("/join")
	public String showUserJoinForm() {
		return "user/join";
	}

	@PostMapping("/join")
	public String userJoinHandle(@Valid User user, BindingResult bindingResult, Model model) {
		if (!bindingResult.hasErrors()) {
			boolean result = userService.createNewUser(user);
			if (result) {
				return "redirect:/user/login";
			} else {
				model.addAttribute("error", "이미 존재하는 아이디 입니다.");
				return "user/join";
			}
		} else {
			model.addAttribute("error", "형식이 맞지 않습니다. 다시 입력해주세요.");
			return "user/join";
		}
	}

	@GetMapping("/login")
	public String showUserLoginForm(@RequestParam(required = false) String error, Model model) {
		if (error != null && error.equals(""))
			model.addAttribute("error", "아이디 또는 비밀번호가 틀렸습니다.");
		return "user/login";
	}
	/*
	@PostMapping("/login")
	public String loginHandle(LoginRequestData data, Model model) {
		boolean result = userService.isValidUser(data);
		if (result) {
			User user = userService.findById(data.getId());
			model.addAttribute("loginId", user);
			return "redirect:/";
		} else {
			model.addAttribute("error", "아이디 또는 비밀번호가 틀렸습니다.");
			return "user/login";
		}
	}
 	*/
	/*
	@GetMapping("/logout")
	public String userLogoutHandle(SessionStatus sessionStatus) {
		sessionStatus.setComplete();
		return "redirect:/";
	}
	*/
	
	@GetMapping("/delete")
	public String userDeleteHandle(@AuthenticationPrincipal Account account) {
		userService.deleteUser(userService.findById(account.getUsername()));
		return "redirect:/logout";
	}
	
	@GetMapping("/access/denied")
	public String showAccessDenied(Model model) {
		return "user/access-denied";
	}
}
