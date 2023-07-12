package org.edupoll.controller;

import org.edupoll.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpSession;

@Controller
public class IndexController {
	UserService userService;
	
	public IndexController(UserService userService) {
		super();
		this.userService = userService;
	}

	@GetMapping("/")
	public String indexHandle() {
		return "index";
	}
	
	@GetMapping("/status")
	@ResponseBody
	public Object statusHandle(HttpSession session) {
		// System.out.println();
		// return "index";
		return session.getAttribute("SPRING_SECURITY_CONTEXT");
	}
}
