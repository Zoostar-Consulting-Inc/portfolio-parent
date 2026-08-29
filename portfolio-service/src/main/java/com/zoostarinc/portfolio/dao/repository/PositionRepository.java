package com.zoostarinc.portfolio.dao.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.zoostarinc.portfolio.dao.entity.PositionEntity;

public interface PositionRepository extends MongoRepository<PositionEntity, String> {

	List<PositionEntity> findByUserIdOrderByTickerAscQuantityDesc(String userId);

	List<PositionEntity> findByUserIdAndTickerOrderByTickerAscQuantityDesc(String userId, String ticker);

	void deleteByUserIdAndId(String userId, String value);

}
