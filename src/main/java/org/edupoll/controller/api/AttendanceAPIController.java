package org.edupoll.controller.api;

import org.edupoll.model.dto.response.AttendanceJoinResponseData;
import org.edupoll.model.entity.User;
import org.edupoll.securty.support.Account;
import org.edupoll.service.AttendanceService;
import org.edupoll.service.MoimService;
import org.edupoll.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

@RestController
@RequestMapping("/api")
public class AttendanceAPIController {
	AttendanceService attendanceService;
	MoimService moimService;
	UserService userService;

	public AttendanceAPIController(AttendanceService attendanceService, MoimService moimService,
			UserService userService) {
		super();
		this.attendanceService = attendanceService;
		this.moimService = moimService;
		this.userService = userService;
	}

	@PostMapping("/attendance/join")	
	public AttendanceJoinResponseData attendanceJoinHandle(@AuthenticationPrincipal Account account, String moimId) {
		AttendanceJoinResponseData rst = attendanceService.addNewAttendance(userService.findById(account.getUsername()), moimId);
		return rst;
	}
	
	@DeleteMapping("/attendance/cancel")
	public AttendanceJoinResponseData attendanceCancelHandle(@AuthenticationPrincipal Account account, String moimId) {
		return attendanceService.cancelAttendance(account.getUsername(), moimId);
	}
	
}
