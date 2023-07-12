package org.edupoll.service;

import java.util.List;
import java.util.stream.Collectors;

import org.edupoll.model.dto.response.UserResponseData;
import org.edupoll.model.entity.User;
import org.edupoll.repository.FollowRepository;
import org.edupoll.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class SearchService {
	UserRepository userRepository;
	FollowRepository followRepository;
	
	public SearchService(UserRepository userRepository, FollowRepository followRepository) {
		super();
		this.userRepository = userRepository;
		this.followRepository = followRepository;
	}

	public List<UserResponseData> getUsersMatchedQuery(String query) {
		List<User> user = userRepository.findByIdContainingOrNickContainingAllIgnoreCase(query, query);
		return user.stream()
				.map(t -> new UserResponseData(t))
				.toList();
	}
	
	public List<UserResponseData> isFollow(String owner, List<UserResponseData> li) {
		return li.stream()
	            .map(t -> {
	                if (followRepository.existsByTargetIdAndOwnerId(t.getId(), owner)) {
	                    t.setFollow(true);
	                }
	                return t;
	            })
	            .collect(Collectors.toList());
	}
}
