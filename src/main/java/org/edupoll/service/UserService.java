package org.edupoll.service;

import java.util.List;
import java.util.Optional;

import org.edupoll.model.dto.request.LoginRequestData;
import org.edupoll.model.entity.Avatar;
import org.edupoll.model.entity.Moim;
import org.edupoll.model.entity.User;
import org.edupoll.model.entity.UserDetail;
import org.edupoll.repository.AttendanceRepository;
import org.edupoll.repository.AvatarRepository;
import org.edupoll.repository.FollowRepository;
import org.edupoll.repository.MoimRepository;
import org.edupoll.repository.ReplyRepository;
import org.edupoll.repository.UserDetailRepository;
import org.edupoll.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class UserService {
	UserRepository userRepository;
	UserDetailRepository userDetailRepository;
	AvatarRepository avatarRepository;
	MoimRepository moimRepository;
	ReplyRepository replyRepository;
	AttendanceRepository attendanceRepository;
	FollowRepository followRepository;

	public UserService(UserRepository userRepository, UserDetailRepository userDetailRepository,
			AvatarRepository avatarRepository, MoimRepository moimRepository, ReplyRepository replyRepository,
			AttendanceRepository attendanceRepository, FollowRepository followRepository) {
		super();
		this.userRepository = userRepository;
		this.userDetailRepository = userDetailRepository;
		this.avatarRepository = avatarRepository;
		this.moimRepository = moimRepository;
		this.replyRepository = replyRepository;
		this.attendanceRepository = attendanceRepository;
		this.followRepository = followRepository;
	}

	// 회원가입을 처리할 서비스 메서드
	public boolean createNewUser(User user) {
		Optional<User> found = userRepository.findById(user.getId());
		if (found.isEmpty()) {
			user.setPass("{bcrypt}" + new BCryptPasswordEncoder().encode(user.getPass()));
			user.setAuthority("ROLE_NORMAL");
			userRepository.save(user);
			return true;
		}
		return false;
	}

	// 로그인 처리를 하기 위해 사용할 서비스 메서드
	public boolean isValidUser(LoginRequestData data) {
		Optional<User> optional = userRepository.findById(data.getId());
		if (optional.isEmpty())
			return false;
		if (!optional.get().getPass().equals(data.getPass()))
			return false;

		return true;
	}

	public User findById(String id) {
		return userRepository.findById(id).orElse(null);
	}

	// 회원 상세정보를 수정/변경 처리할 서비스 메서드
	public boolean modifySpecificUserDetail(UserDetail userDetail, String userId) {
		if (userRepository.findById(userId).isEmpty())
			return false;

		User foundUser = userRepository.findById(userId).get();
		if (foundUser.getUserDetail() != null) {
			userDetail.setIdx(foundUser.getUserDetail().getIdx());
		}
		UserDetail saved = userDetailRepository.save(userDetail);
		foundUser.setUserDetail(saved);
		userRepository.save(foundUser);
		return true;
	}

	public UserDetail findSpecificSaveDetail(User user) {
		return userRepository.findById(user.getId()).get().getUserDetail();
	}

	public List<Avatar> avatarLoad() {
		return avatarRepository.findAll();
	}

	@Transactional
	public void deleteUser(User user) {
		if (followRepository.existsByOwnerIdOrTargetId(user.getId(), user.getId())) {
			followRepository.deleteByOwnerIdOrTargetId(user.getId(), user.getId());
		}
		
		if (!moimRepository.findByUser(user).isEmpty()) {
			moimRepository.findByUser(user).stream().forEach(t -> {
				replyRepository.deleteAll(t.getReplies());
				attendanceRepository.deleteAll(t.getAttendances());
			});
		}
		
		if (!attendanceRepository.findByUserId(user.getId()).isEmpty()) {
			attendanceRepository.findByUserId(user.getId()).stream().forEach(t -> {
				Moim moim = t.getMoim();
				moim.setCurrentPerson(moim.getCurrentPerson() - 1);
				moimRepository.save(moim);
				attendanceRepository.delete(t);
			});
		}
		
		if (!moimRepository.findByUser(user).isEmpty())
			moimRepository.deleteAll(moimRepository.findByUser(user));
		
		userRepository.delete(user);
		
		if (user.getUserDetail() != null)
			userDetailRepository.delete(user.getUserDetail());
	}
}
