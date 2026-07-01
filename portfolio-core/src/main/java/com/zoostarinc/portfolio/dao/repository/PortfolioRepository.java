package com.zoostarinc.portfolio.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zoostarinc.portfolio.dao.entity.PortfolioEntity;

public interface PortfolioRepository extends JpaRepository<PortfolioEntity, String> {

}
