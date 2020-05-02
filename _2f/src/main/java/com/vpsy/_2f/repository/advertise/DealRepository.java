package com.vpsy._2f.repository.advertise;

import com.vpsy._2f.vo.advertise.Deal;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author punith
 * @date 25-Apr-2020
 * @description Repository class to handle requests for Deal table
 */
@Repository
public interface DealRepository
		extends PagingAndSortingRepository<Deal, Integer>, JpaSpecificationExecutor<Deal> {
}
