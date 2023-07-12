package org.edupoll.repository;

import org.edupoll.model.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Integer>{
	boolean existsByTargetIdAndOwnerId(String targetId, String ownerId);
	void deleteByOwnerIdAndTargetId(String ownerId, String targetId);
	void deleteByOwnerIdOrTargetId(String ownerId, String targetId);
	boolean existsByOwnerIdOrTargetId(String ownerId, String targetId);
}
