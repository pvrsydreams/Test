package com.vpsy._2f.repository.message;

import com.vpsy._2f.vo.message.Message;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author punith
 * @date 25-Apr-2020
 * @description Repository class to handle requests for Message table
 */
@Repository
public interface MessageRepository
		extends PagingAndSortingRepository<Message, Integer>, JpaSpecificationExecutor<Message> {
}
