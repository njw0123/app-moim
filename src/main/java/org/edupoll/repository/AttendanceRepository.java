package org.edupoll.repository;

import java.util.List;

import org.edupoll.model.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {
	boolean existsByUserIdAndMoimId(String userId, String moimId);
	void deleteByUserIdAndMoimId(String userId, String moimId);
	List<Attendance> findByMoimId(String moimId);
	List<Attendance> findByUserId(String userId);
}
