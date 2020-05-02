package com.vpsy._2f.repository.utility;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.vpsy._2f.vo.location.State;

/**
 * @author 			punith
 * @date				23-Apr-2020
 * @description		
 */
public class SearchSpecification<T> implements Specification<T> {

	private static final long serialVersionUID = 1L;
	
	private SearchCriteria criteria;
	
	public SearchSpecification() {
		super();
	}
	
	public SearchSpecification(SearchCriteria criteria) {
		super();
		this.criteria = criteria;
	}

	@Override
	public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
		
		Predicate predicate = null;
		
		String operation = criteria.getOperation().toLowerCase();
		switch (operation) {
		case ">":
			predicate = builder.greaterThan(root.<String> get(criteria.getKey()), criteria.getValue().toString());
			break;
			
		case ">=":
			predicate = builder.greaterThanOrEqualTo(root.<String> get(criteria.getKey()), criteria.getValue().toString());
			break;

		case "<":
			predicate = builder.lessThan(root.<String> get(criteria.getKey()), criteria.getValue().toString());
			break;
			
		case "<=":
			predicate = builder.lessThanOrEqualTo(root.<String> get(criteria.getKey()), criteria.getValue().toString());
			break;
			
		case ":":
			predicate = builder.like(root.<String>get(criteria.getKey()), "%" + criteria.getValue() + "%");
			break;
			
		}
		
		return predicate;
	}

	

}
