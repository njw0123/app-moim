package org.edupoll.repository;

import org.edupoll.model.entity.Avatar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvatarRepository extends JpaRepository<Avatar, String>{
	
}
