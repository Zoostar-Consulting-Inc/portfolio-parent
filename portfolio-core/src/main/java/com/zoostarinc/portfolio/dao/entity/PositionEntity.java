package com.zoostarinc.portfolio.dao.entity;

import java.util.Date;

import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.domain.Persistable;
import org.springframework.util.StringUtils;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@ToString
public class PositionEntity implements Persistable<String> {

	@Id
//	@GeneratedValue(generator = "uuid", strategy = GenerationType.UUID)
	@UuidGenerator
	private String id;
	
	private String oauthUserId;
	
	private Date txDate;
	
	private String ticker;
	
	private Integer quantity;
	
	private Float amount;

	@Override
	public boolean isNew() {
		return !StringUtils.hasText(id);
	}

}
