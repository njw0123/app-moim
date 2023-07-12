package org.edupoll.repository;

import java.util.Date;
import java.util.List;

import org.edupoll.model.entity.Moim;
import org.edupoll.model.entity.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoimRepository extends JpaRepository<Moim, String>{
	List<Moim> findByTargetDateAfter(Date date, PageRequest pageRequest);
	long countByTargetDateAfter(Date date, PageRequest pageRequest);
	List<Moim> findByUser(User user);
}
