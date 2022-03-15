package com.perigea.tracker.timesheet.search;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.perigea.tracker.commons.utils.Utils;

/**
 * Da utilizzare per filtri semplici e non innestati. Tutti i predicati vengono
 * messi sullo stesso livello in AND oppure in OR. Non sono gestiti predicati
 * innestati o precedenze
 * 
 * Caso non gestito (X AND Y) OR Z
 *
 * @param <T>
 */
@Component
public class FilterFactory<T> {

	@Autowired
	private Logger logger;

	public Specification<T> buildSpecification(final List<Condition> conditions, boolean isOrPredicates) {
		Specification<T> specification = new Specification<T>() {
			private static final long serialVersionUID = -7325784609028963910L;

			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> criteriaQuery,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				conditions.forEach(
						condition -> predicates.add(buildPredicate(condition, root, criteriaQuery, criteriaBuilder)));
				return predicates.size() > 1
						? ((isOrPredicates) ? (criteriaBuilder.or(predicates.toArray(new Predicate[predicates.size()])))
								: (criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]))))
						: predicates.get(0);
			}
		};
		return specification;
	}

	public Specification<T> getLikeSpecificationWithJoin(String key, String joinEntity, String value) {
		return new Specification<T>() {
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				return builder.like(root.join(joinEntity).as(String.class), value);
			}
		};
	}

	public Specification<T> buildSpecification(final List<Condition> conditions) {
		return buildSpecification(conditions, false);
	}

	private Predicate buildPredicate(Condition condition, Root<T> root, CriteriaQuery<?> criteriaQuery,
			CriteriaBuilder criteriaBuilder) {
		switch (condition.operator) {
		case eq:
			return buildEqualsPredicateToCriteria(condition, root, criteriaQuery, criteriaBuilder);
		case between:
			return buildBetweenPredicateToCriteria(condition, root, criteriaQuery, criteriaBuilder);
		case gt:
			return buildGreaterThanPredicateToCriteria(condition, root, criteriaQuery, criteriaBuilder);
		case gte:
			return buildGreaterThanOrEqualPredicateToCriteria(condition, root, criteriaQuery, criteriaBuilder);
		case in:
			return buildInPredicateToCriteria(condition, root, criteriaQuery, criteriaBuilder);
		case notIn:
			return buildNotInPredicateToCriteria(condition, root, criteriaQuery, criteriaBuilder);
		case isNull:
			return buildIsNullPredicateToCriteria(condition, root, criteriaQuery, criteriaBuilder);
		case lt:
			return buildLessThanPredicateToCriteria(condition, root, criteriaQuery, criteriaBuilder);
		case lte:
			return buildLessThanOrEqualPredicateToCriteria(condition, root, criteriaQuery, criteriaBuilder);
		case ne:
			return buildNotEqualsPredicateToCriteria(condition, root, criteriaQuery, criteriaBuilder);
		case like:
		case contains:
			return buildLikePredicateToCriteria(condition, root, criteriaQuery, criteriaBuilder);
		case startsWith:
			return buildStartsPredicateToCriteria(condition, root, criteriaQuery, criteriaBuilder);
		case endsWith:
			return buildEndsWithPredicateToCriteria(condition, root, criteriaQuery, criteriaBuilder);
		default:
			return buildEqualsPredicateToCriteria(condition, root, criteriaQuery, criteriaBuilder);
		}
	}

	private Predicate buildBetweenPredicateToCriteria(Condition condition, Root<T> root, CriteriaQuery<?> criteriaQuery,
			CriteriaBuilder builder) {
		return builder.between(getFieldCondition(condition, root),
				builder.literal(parseJavaType(condition.value, condition.valueType)),
				builder.literal(parseJavaType(condition.valueTo, condition.valueType)));
	}

	private Predicate buildGreaterThanPredicateToCriteria(Condition condition, Root<T> root,
			CriteriaQuery<?> criteriaQuery, CriteriaBuilder builder) {
		return builder.greaterThan(getFieldCondition(condition, root),
				builder.literal(parseJavaType(condition.value, condition.valueType)));
	}

	private Predicate buildGreaterThanOrEqualPredicateToCriteria(Condition condition, Root<T> root,
			CriteriaQuery<?> criteriaQuery, CriteriaBuilder builder) {
		return builder.greaterThanOrEqualTo(getFieldCondition(condition, root),
				builder.literal(parseJavaType(condition.value, condition.valueType)));
	}

	private Predicate buildLessThanPredicateToCriteria(Condition condition, Root<T> root,
			CriteriaQuery<?> criteriaQuery, CriteriaBuilder builder) {
		return builder.lessThan(getFieldCondition(condition, root),
				builder.literal(parseJavaType(condition.value, condition.valueType)));
	}

	private Predicate buildLessThanOrEqualPredicateToCriteria(Condition condition, Root<T> root,
			CriteriaQuery<?> criteriaQuery, CriteriaBuilder builder) {
		return builder.lessThanOrEqualTo(getFieldCondition(condition, root),
				builder.literal(parseJavaType(condition.value, condition.valueType)));
	}

	private Predicate buildInPredicateToCriteria(Condition condition, Root<T> root, CriteriaQuery<?> criteriaQuery,
			CriteriaBuilder builder) {
		return getFieldCondition(condition, root).in((List<?>) condition.value);
	}

	private Predicate buildNotInPredicateToCriteria(Condition condition, Root<T> root, CriteriaQuery<?> criteriaQuery,
			CriteriaBuilder builder) {
		return builder.not(buildInPredicateToCriteria(condition, root, criteriaQuery, builder));
	}

	private Predicate buildNotEqualsPredicateToCriteria(Condition condition, Root<T> root,
			CriteriaQuery<?> criteriaQuery, CriteriaBuilder builder) {
		return builder.not(builder.equal(getFieldCondition(condition, root), condition.value));
	}

	private Predicate buildEqualsPredicateToCriteria(Condition condition, Root<T> root, CriteriaQuery<?> criteriaQuery,
			CriteriaBuilder builder) {
		return builder.equal(getFieldCondition(condition, root), condition.value);
	}

	private Predicate buildIsNullPredicateToCriteria(Condition condition, Root<T> root, CriteriaQuery<?> query,
			CriteriaBuilder builder) {
		return builder.isNull(getFieldCondition(condition, root));
	}

	private Predicate buildLikePredicateToCriteria(Condition condition, Root<T> root, CriteriaQuery<?> query,
			CriteriaBuilder builder) {
		return builder.like(builder.upper(getFieldConditionForUpper(condition, root)),
				Utils.QUERY_LIKE_BOUNDARY + (condition.value).toString().toUpperCase() + Utils.QUERY_LIKE_BOUNDARY);
	}

	private Predicate buildEndsWithPredicateToCriteria(Condition condition, Root<T> root, CriteriaQuery<?> query,
			CriteriaBuilder builder) {
		return builder.like(builder.upper(getFieldConditionForUpper(condition, root)),
				Utils.join(Utils.QUERY_LIKE_BOUNDARY, condition.value.toString().toUpperCase()));
	}

	private Predicate buildStartsPredicateToCriteria(Condition condition, Root<T> root, CriteriaQuery<?> query,
			CriteriaBuilder builder) {
		return builder.like(builder.upper(getFieldConditionForUpper(condition, root)),
				Utils.join(condition.value.toString().toUpperCase(), Utils.QUERY_LIKE_BOUNDARY));
	}

	@SuppressWarnings("unchecked")
	private Comparable<? super Object> parseJavaType(Object value, Class<?> clazz) {
		Comparable<? super Object> parsedValue = null;
		try {
			parsedValue = (Comparable<? super Object>) Utils.getMapper()
					.readValue(Utils.getMapper().writeValueAsBytes(value), clazz);
		} catch (Exception ex) {
			logger.debug(ex.getMessage());
		}
		return parsedValue;
	}

	private Path<Comparable<? super Object>> getFieldCondition(Condition condition, Root<T> root) {

		if (condition.field.contains(".")) {
			String[] arr = condition.field.split("\\.");
			String joinName = arr[0];
			if (arr.length <= 2) {
				String fieldName = arr[1];
				return root.join(joinName).get(fieldName);
			} else {
				String joinSecondName = arr[1];
				String fieldName = arr[2];
				return root.join(joinName).join(joinSecondName).get(fieldName);
			}
		} else {
			return root.get(condition.field);
		}
	}

	private Expression<String> getFieldConditionForUpper(Condition condition, Root<T> root) {

		if (condition.field.contains(".")) {
			String[] arr = condition.field.split("\\.");
			String joinName = arr[0];
			if (arr.length <= 2) {
				String fieldName = arr[1];
				return root.join(joinName).get(fieldName);
			} else {
				String joinSecondName = arr[1];
				String fieldName = arr[2];
				return root.join(joinName).join(joinSecondName).get(fieldName);
			}
		} else {
			return root.get(condition.field);
		}
	}

}