package org.edupoll.controller;

import java.text.SimpleDateFormat;
import java.util.List;

import org.edupoll.model.entity.Avatar;
import org.edupoll.model.entity.User;
import org.edupoll.model.entity.UserDetail;
import org.edupoll.securty.support.Account;
import org.edupoll.service.AttendanceService;
import org.edupoll.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
public class PrivateController {
	UserService userService;
	AttendanceService attendanceService;
	
	public PrivateController(UserService userService, AttendanceService attendanceService) {
		super();
		this.userService = userService;
		this.attendanceService = attendanceService;
	}

	@GetMapping("/private")
	public String showPrivateInfoView(@AuthenticationPrincipal Account account, Model model) {
		model.addAttribute("user", userService.findById(account.getUsername()));
		model.addAttribute("joinMoim", attendanceService.findByUserId(account.getUsername()));
		return "private/default";
	}
	
	@GetMapping("/private/modify")
	public String showPrivateForm (@AuthenticationPrincipal Account account, Model model) {
		if (userService.findById(account.getUsername()).getUserDetail() != null) {
			UserDetail userDetail = userService.findSpecificSaveDetail(userService.findById(account.getUsername()));
			model.addAttribute("user", userDetail);
			if (userDetail.getBirthday() != null) model.addAttribute("fomatDate", new SimpleDateFormat("yyyy-MM-dd").format(userDetail.getBirthday()));
		}
		List<Avatar> li = userService.avatarLoad();
		model.addAttribute("avatar", li);
		return "private/modify";
	}
	
	@PostMapping("/private/modify")
	public String privateModifyHandle(@AuthenticationPrincipal Account account, UserDetail userDetail, Model model) {
		boolean rst = userService.modifySpecificUserDetail(userDetail, account.getUsername());
		return "redirect:/private/modify";
	}
	
}
