package com.zoostarinc.portfolio;

import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.zoostarinc.portfolio.dao.repository.PositionRepository;

public abstract class AbstractMockRepository extends AbstractCommonTest {

	@MockitoBean
	protected PositionRepository positionRepository;

}
