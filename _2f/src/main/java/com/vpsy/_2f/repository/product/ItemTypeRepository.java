package com.vpsy._2f.repository.product;

import com.vpsy._2f.vo.product.ItemType;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author punith
 * @date 25-Apr-2020
 * @description Repository class to handle requests for ItemType table
 */
@Repository
public interface ItemTypeRepository
		extends PagingAndSortingRepository<ItemType, Integer>, JpaSpecificationExecutor<ItemType> {
}
