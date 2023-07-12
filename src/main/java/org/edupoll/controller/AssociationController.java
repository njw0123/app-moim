package org.edupoll.controller;

import java.util.Iterator;
import java.util.List;

import org.edupoll.model.entity.Avatar;
import org.edupoll.model.entity.User;
import org.edupoll.model.entity.UserDetail;
import org.edupoll.repository.AvatarRepository;
import org.edupoll.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AssociationController {
	UserRepository userRepository;
	AvatarRepository avatarRepository;

	public AssociationController(UserRepository userRepository, AvatarRepository avatarRepository) {
		super();
		this.userRepository = userRepository;
		this.avatarRepository = avatarRepository;
	}

	@GetMapping("/assoc/02")
	public String assoc02Handle(String avatarId) {
		if (avatarRepository.findById(avatarId).isPresent()) {
			Avatar avatar = avatarRepository.findById(avatarId).get();
			List<UserDetail> details = avatar.getUserDetails();
			for (UserDetail i : details) {
				System.out.println("→ " + i.getUser().getId());
			}
		}else {
			System.out.println("not found");
		}
		return "index";
	}
	
	@GetMapping("/assoc/01")
	public String assoc01Handle(String userId) {
		User found = userRepository.findById(userId).orElse(null);
		System.out.println(found);
//		UserDetail detail = found.getUserDetail();
//		System.out.println(detail);
		return "index";
	}	
}
