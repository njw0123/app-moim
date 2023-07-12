package org.edupoll.service;

import java.util.List;
import java.util.stream.Collectors;

import org.edupoll.model.dto.response.FollowResponseData;
import org.edupoll.model.dto.response.UserResponseData;
import org.edupoll.model.entity.Follow;
import org.edupoll.repository.FollowRepository;
import org.edupoll.repository.UserRepository;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class FollowService {
	FollowRepository followRepository;
	UserRepository userRepository;
	
	public FollowService(FollowRepository followRepository, UserRepository userRepository) {
		super();
		this.followRepository = followRepository;
		this.userRepository = userRepository;
	}
	
	@Transactional
	public FollowResponseData followJoin(String owner, String target) {
		FollowResponseData followResponseData = new FollowResponseData();
		if (!followRepository.existsByTargetIdAndOwnerId(target, owner)) {			
			Follow follow = new Follow();
			follow.setOwner(userRepository.findById(owner).get());
			follow.setTarget(userRepository.findById(target).get());
			followRepository.save(follow);
			followResponseData.setResult(true);
		}else {
			followResponseData.setResult(false);
		}
		return followResponseData;
	}
	
	@Transactional
	public FollowResponseData followCancel(String owner, String target) {
		FollowResponseData followResponseData = new FollowResponseData();
		if (followRepository.existsByTargetIdAndOwnerId(target, owner)) {
			followRepository.deleteByOwnerIdAndTargetId(owner, target);
			followResponseData.setResult(true);
		}else {
			followResponseData.setResult(false);
		}
		return followResponseData;
	}
}
