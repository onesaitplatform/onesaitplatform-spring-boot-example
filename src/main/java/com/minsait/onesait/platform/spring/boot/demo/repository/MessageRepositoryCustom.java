package com.minsait.onesait.platform.spring.boot.demo.repository;

import java.util.Date;
import java.util.List;

import com.minsait.onesait.platform.spring.boot.demo.dto.StatusAggregationResults;
import com.minsait.onesait.platform.spring.boot.demo.dto.TypeAggregationResults;
import com.minsait.onesait.platform.spring.boot.demo.model.Message;

public interface MessageRepositoryCustom {

	int updateMessageSent(String idMessage, Date sentDate);

	int updateMessageWithError(String idMessage, Date sentDate, String errorOnSent);

	List<Message> findAllWithLimitOffset(int limit, int offset);

	List<StatusAggregationResults> countByStatus();

	List<StatusAggregationResults> countByStatusPercentage();

	List<TypeAggregationResults> countByType();

}
