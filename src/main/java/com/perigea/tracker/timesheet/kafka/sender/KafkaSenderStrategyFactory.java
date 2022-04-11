package com.perigea.tracker.timesheet.kafka.sender;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class KafkaSenderStrategyFactory<T> {

	private final Map<Class<T>, KafkaSenderStrategy<T>> strategies = new HashMap<>();
	
	@Autowired
	public KafkaSenderStrategyFactory(List<KafkaSenderStrategy<T>> streamStrategies) {
		streamStrategies.forEach(strategy -> strategies.put(strategy.entityClass(), strategy));
	}

	public KafkaSenderStrategy<T> load(Class<T> entityClass) {
		KafkaSenderStrategy<T> strategy = strategies.get(entityClass);
		if(strategy == null) {
			// TODO add custom exception
			throw new RuntimeException(String.format("KafkaSenderStrategyFactory - Unrecognized entityClass Type : %s", entityClass));
		} 
		return strategy;
	}

	
}
