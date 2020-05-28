package com.minsait.onesait.platform.spring.boot.demo.repository.impl;

//imports as static
import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.sort;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.minsait.onesait.platform.spring.boot.demo.dto.StatusAggregationResults;
import com.minsait.onesait.platform.spring.boot.demo.dto.TypeAggregationResults;
import com.minsait.onesait.platform.spring.boot.demo.model.Message;
import com.minsait.onesait.platform.spring.boot.demo.repository.MessageRepositoryCustom;
import com.mongodb.client.result.UpdateResult;

public class MessageRepositoryImpl implements MessageRepositoryCustom {

	private static final String PERCENTAGE = "percentage";
	private static final String COUNT = "count";
	private static final String ERROR_ON_SENT = "errorOnSent";
	private static final String SENT_DATE = "sentDate";
	private static final String STATUS_MESSAGE = "statusMessage";
	@Autowired
	MongoTemplate mongoTemplate;

	@Override
	public int updateMessageSent(String idMessage, Date sentDate) {
		final Query query = new Query(Criteria.where("idMessage").is(idMessage));
		final Update update = new Update();
		update.set(SENT_DATE, sentDate);
		update.set(STATUS_MESSAGE, Message.MessageStatus.SENT);

		final UpdateResult result = mongoTemplate.updateFirst(query, update, Message.class);

		return (int) result.getModifiedCount();

	}

	@Override
	public int updateMessageWithError(String idMessage, Date sentDate, String errorOnSent) {
		final Query query = new Query(Criteria.where("idMessage").is(idMessage));
		final Update update = new Update();
		update.set(SENT_DATE, sentDate);
		update.set(STATUS_MESSAGE, Message.MessageStatus.ERROR);
		update.set(ERROR_ON_SENT, errorOnSent);

		final UpdateResult result = mongoTemplate.updateFirst(query, update, Message.class);

		return (int) result.getModifiedCount();
	}

	@Override
	public List<StatusAggregationResults> countByStatus() {

		final Aggregation agg = newAggregation(group(STATUS_MESSAGE).count().as(COUNT),
				project(COUNT).and(STATUS_MESSAGE).previousOperation(), sort(Sort.Direction.DESC, COUNT)

		);
		final AggregationResults<StatusAggregationResults> results = mongoTemplate.aggregate(agg, Message.class,
				StatusAggregationResults.class);

		return results.getMappedResults();

	}

	@Override
	public List<TypeAggregationResults> countByType() {

		final Aggregation agg = newAggregation(group("typeMessage").count().as(COUNT),
				project(COUNT).and("typeMessage").previousOperation(), sort(Sort.Direction.DESC, COUNT)

		);
		final AggregationResults<TypeAggregationResults> results = mongoTemplate.aggregate(agg, Message.class,
				TypeAggregationResults.class);

		return results.getMappedResults();

	}

	@Override
	public List<StatusAggregationResults> countByStatusPercentage() {
		final long total = mongoTemplate.count(new Query(), Message.class);

		final Aggregation agg = newAggregation(group(STATUS_MESSAGE).count().as(COUNT),
				project(COUNT).and(STATUS_MESSAGE).previousOperation().and(COUNT).multiply(100 / total).as(PERCENTAGE));
		final AggregationResults<StatusAggregationResults> results = mongoTemplate.aggregate(agg, Message.class,
				StatusAggregationResults.class);

		return results.getMappedResults();
	}

	@Override
	public List<Message> findAllWithLimitOffset(int limit, int offset) {
		final Query query = new Query();
		query.limit(limit);
		query.skip(offset);
		return mongoTemplate.find(query, Message.class);
	}
}
