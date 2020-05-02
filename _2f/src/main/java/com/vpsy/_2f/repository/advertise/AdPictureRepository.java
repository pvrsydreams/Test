package com.vpsy._2f.repository.advertise;

import com.vpsy._2f.vo.advertise.AdPicture;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author punith
 * @date 25-Apr-2020
 * @description Repository class to handle requests for AdPicture table
 */
@Repository
public interface AdPictureRepository
		extends PagingAndSortingRepository<AdPicture, Integer>, JpaSpecificationExecutor<AdPicture> {
}
