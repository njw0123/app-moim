package org.edupoll.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.edupoll.model.dto.response.AttendanceJoinResponseData;
import org.edupoll.model.entity.Attendance;
import org.edupoll.model.entity.Moim;
import org.edupoll.model.entity.User;
import org.edupoll.repository.AttendanceRepository;
import org.edupoll.repository.MoimRepository;
import org.edupoll.repository.UserRepository;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class AttendanceService {
	AttendanceRepository attendanceRepository;
	UserRepository userRepository;
	MoimRepository moimRepository;

	public AttendanceService(AttendanceRepository attendanceRepository, UserRepository userRepository,
			MoimRepository moimRepository) {
		super();
		this.attendanceRepository = attendanceRepository;
		this.userRepository = userRepository;
		this.moimRepository = moimRepository;
	}

	@Transactional
	public AttendanceJoinResponseData addNewAttendance(User user, String MoimId) {
		AttendanceJoinResponseData ajrd = new AttendanceJoinResponseData();
		if (user == null || MoimId == null) {
			ajrd.setResult(false);
			ajrd.setErrorMessage("유효하지 않은 정보가 전송 되었습니다.");
			return ajrd;
		}
		if (attendanceRepository.existsByUserIdAndMoimId(user.getId(), MoimId)) {
			ajrd.setResult(false);
			ajrd.setErrorMessage("이미 참가중인 모임 입니다.");
			return ajrd;
		}
		Optional<Moim> moim = moimRepository.findById(MoimId);
		if (moim.get().getMaxPerson() == moim.get().getCurrentPerson()) {
			ajrd.setResult(false);
			ajrd.setErrorMessage("최대 허용 인원을 초과 하였습니다.");
			return ajrd;
		}
		Attendance attendance = new Attendance();
		attendance.setUser(user);
		attendance.setMoim(moim.get());
		attendanceRepository.save(attendance);
		
		moim.get().setCurrentPerson(moim.get().getCurrentPerson()+1);
		moimRepository.save(moim.get());
		
		ajrd.setResult(true);
		ajrd.setCurrentPerson(moim.get().getCurrentPerson());
		List<String> list = attendanceRepository.findByMoimId(MoimId).stream().map(t -> t.getUser().getId()).toList();
		ajrd.setAttendUserIds(list);
		return ajrd;
	}
	
	@Transactional
	public AttendanceJoinResponseData cancelAttendance(String loginId, String moimId) {
		AttendanceJoinResponseData ajrd = new AttendanceJoinResponseData();
		if (loginId == null || moimId == null) {
			ajrd.setResult(false);
			ajrd.setErrorMessage("유효하지 않은 정보가 전송 되었습니다.");
			return ajrd;
		}
		if (!attendanceRepository.existsByUserIdAndMoimId(loginId, moimId)) {
			ajrd.setResult(false);
			ajrd.setErrorMessage("이미 참가중인 모임이 아닙니다.");
			return ajrd;
		}
		attendanceRepository.deleteByUserIdAndMoimId(loginId, moimId);
		Optional<Moim> moim = moimRepository.findById(moimId);
		moim.get().setCurrentPerson(moim.get().getCurrentPerson()-1);
		moimRepository.save(moim.get());
		
		ajrd.setResult(true);
		ajrd.setCurrentPerson(moim.get().getCurrentPerson());
		List<String> list = attendanceRepository.findByMoimId(moimId).stream().map(t -> t.getUser().getId()).toList();
		ajrd.setAttendUserIds(list);
		
		return ajrd;
	}
	
	public boolean isJoinAttendance(String userId, String moimId) {
		return attendanceRepository.existsByUserIdAndMoimId(userId, moimId);
	}
	
	public List<Attendance> findByUserId(String userId) {
		return attendanceRepository.findByUserId(userId);
	}
	
}
