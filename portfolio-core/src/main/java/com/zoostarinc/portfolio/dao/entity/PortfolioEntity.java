package com.zoostarinc.portfolio.dao.entity;

import org.hibernate.annotations.GeneratorType;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.util.StringUtils;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class PortfolioEntity implements Persistable<String> {

	@Id
	@GeneratedValue(generator = "uuid")
	@GeneratorType(name = "uuid", strategy = "uuid2")
	private String id;

	@Override
	public boolean isNew() {
		return !StringUtils.hasText(id);
	}

}
