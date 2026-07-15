package com.zoostarinc.portfolio.dao.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.zoostarinc.portfolio.dao.entity.PositionEntity;

public interface PositionRepository extends MongoRepository<PositionEntity, String> {

	List<PositionEntity> findByOauthUserIdOrderByTickerAscQuantityDesc(String oauthUserId);

	List<PositionEntity> findByOauthUserIdAndTickerOrderByTickerAscQuantityDesc(String oauthUserId, String ticker);

	void deleteByOauthUserIdAndId(String oauthUserId, String value);

}
