package com.vpsy._2f.repository.location;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.vpsy._2f.vo.location.Taluk;

/**
 * @author punith
 * @date 23-Apr-2020
 * @description Repository class to handle requests for Taluk table
 */
@Repository
public interface TalukRepository extends PagingAndSortingRepository<Taluk, Integer>, JpaSpecificationExecutor<Taluk> {

}
