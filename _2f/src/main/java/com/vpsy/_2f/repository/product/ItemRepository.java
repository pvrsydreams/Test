package com.vpsy._2f.repository.product;

import com.vpsy._2f.vo.product.Item;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author punith
 * @date 25-Apr-2020
 * @description Repository class to handle requests for Item table
 */
@Repository
public interface ItemRepository
		extends PagingAndSortingRepository<Item, Integer>, JpaSpecificationExecutor<Item> {
}
