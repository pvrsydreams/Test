package com.vpsy._2f.repository.utility;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.jpa.domain.Specification;

public class SearchSpecificationBuilder<T> {
	private final List<SearchCriteria> params;

	public SearchSpecificationBuilder() {
		params = new ArrayList<SearchCriteria>();
	}

	public SearchSpecificationBuilder<T> with(String key, String operation, Object value) {
		params.add(new SearchCriteria(key, operation, value));
		return this;
	}

	public Specification<T> build() {
        if (params.size() == 0) {
            return null;
        }
 
        List<Specification<T>> specs = params.stream()
          .map(SearchSpecification<T>::new)
          .collect(Collectors.toList());
         
        Specification<T> result = specs.get(0);
 
        for (int i = 1; i < params.size(); i++) {
        	System.out.println("Setting param: ");
			result = Specification.where(result).or(specs.get(i));
		}
        
//        for (int i = 1; i < params.size(); i++) {
//            result = params.get(i).isOrPredicate()
//                ? Specification.where(result)
//                  .or(specs.get(i))
//                : Specification.where(result)
//                  .and(specs.get(i));
//        }       
        return result;
    }
}
