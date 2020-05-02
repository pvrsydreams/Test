package com.vpsy._2f.repository.account;

import com.vpsy._2f.vo.account.ProfilePicture;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author punith
 * @date 25-Apr-2020
 * @description Repository class to handle requests for ProfilePicture table
 */
@Repository
public interface ProfilePictureRepository
		extends PagingAndSortingRepository<ProfilePicture, Integer>, JpaSpecificationExecutor<ProfilePicture> {
}
