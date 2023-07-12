package org.edupoll.controller;

import java.util.List;

import org.edupoll.model.dto.request.MoimResponseData;
import org.edupoll.model.entity.Attendance;
import org.edupoll.model.entity.Moim;
import org.edupoll.model.entity.Reply;
import org.edupoll.model.entity.User;
import org.edupoll.repository.AttendanceRepository;
import org.edupoll.securty.support.Account;
import org.edupoll.service.AttendanceService;
import org.edupoll.service.MoimService;
import org.edupoll.service.ReplyService;
import org.edupoll.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/moim")
public class MoimController {
	MoimService moimService;
	ReplyService replyService;
	AttendanceService attendanceService;
	UserService userService;

	public MoimController(MoimService moimService, ReplyService replyService, AttendanceService attendanceService,
			UserService userService) {
		super();
		this.moimService = moimService;
		this.replyService = replyService;
		this.attendanceService = attendanceService;
		this.userService = userService;
	}

	@GetMapping
	public String showMoimList(@RequestParam(defaultValue = "1")int page, Model model) {
		List<MoimResponseData> all = moimService.findByAll(page);
		model.addAttribute("all", all);
		model.addAttribute("pagination", moimService.createPagination(page));
		return "moim/list";
	}
	
	@GetMapping("/create")
	public String showMoimForm(Model model, String error) {
		List<String> li = List.of("취미","학습","봉사","건강","비지니스","문화","스포츠");
		model.addAttribute("cate", li);
		if (error != null) model.addAttribute("error", error);
		return "moim/create";
	}
	
	@PostMapping("/create")
	public String moimCreateHandle(@AuthenticationPrincipal Account account, @Valid Moim moim, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			redirectAttributes.addAttribute("error", "1");
			return "redirect:/moim/create";
		}
		moimService.create(userService.findById(account.getUsername()), moim);
		return "redirect:/moim";
	}
	
	@GetMapping("/detail")
	public String showMoimDetail(@AuthenticationPrincipal Account account, String id, Model model, @RequestParam(defaultValue = "1") int page) {
		Moim moim = moimService.findById(id);
		model.addAttribute("moim", moim);
		model.addAttribute("replys", replyService.getReplys(id, page));
		
		List<String> pages = replyService.getPages(id, page);
		model.addAttribute("pages", pages);
		if (page > 5) model.addAttribute("Previous", Integer.parseInt(pages.get(0))-1);
		if (replyService.getNext(id, page)) model.addAttribute("Next", Integer.parseInt(pages.get(4))+1);
		if (account != null) {
			model.addAttribute("isJoined", attendanceService.isJoinAttendance(account.getUsername(), id));
			model.addAttribute("isLogin", true);
		}
		
		return "moim/detail";
	}
	
	@PostMapping("/reply")
	public String replyCreateHandle(Reply reply) {
		replyService.create(reply);
		return "redirect:/moim/detail?id="+reply.getMoim().getId();
	}
	
}
