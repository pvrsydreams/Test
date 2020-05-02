package com.vpsy._2f.repository.account;

import com.vpsy._2f.vo.account.Account;
import com.vpsy._2f.vo.account.OneTimePassword;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author punith
 * @date 25-Apr-2020
 * @description Repository class to handle requests for AppUser table
 */
@Repository
public interface OneTimePasswordRepository
		extends PagingAndSortingRepository<OneTimePassword, Long>, JpaSpecificationExecutor<OneTimePassword> {

	/** Fetch record by mobile number and password key */
	OneTimePassword findByMobileAndOtpKey(Long mobile, String otpKey);
}
