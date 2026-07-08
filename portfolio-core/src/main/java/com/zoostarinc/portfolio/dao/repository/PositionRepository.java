package com.zoostarinc.portfolio.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zoostarinc.portfolio.dao.entity.PositionEntity;

public interface PositionRepository extends JpaRepository<PositionEntity, String> {

	List<PositionEntity> findByOauthUserIdOrderByTickerAscQuantityDesc(String oauthUserId);

}
