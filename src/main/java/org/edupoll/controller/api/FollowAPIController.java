package org.edupoll.controller.api;

import org.edupoll.model.dto.response.FollowResponseData;
import org.edupoll.model.entity.User;
import org.edupoll.securty.support.Account;
import org.edupoll.service.FollowService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

@RestController
@RequestMapping("/api/follow")
public class FollowAPIController {
	FollowService followService;

	public FollowAPIController(FollowService followService) {
		super();
		this.followService = followService;
	}
	
	@PostMapping
	public FollowResponseData followHandle(@AuthenticationPrincipal Account account, String targetId) {
		return followService.followJoin(account.getUsername(), targetId);
	}
	
	@DeleteMapping
	public FollowResponseData followCancelHandle(@AuthenticationPrincipal Account account, String targetId) {
		return followService.followCancel(account.getUsername(), targetId);
	}
}
