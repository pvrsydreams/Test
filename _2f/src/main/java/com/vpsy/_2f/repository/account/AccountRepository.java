package com.vpsy._2f.repository.account;

import com.vpsy._2f.vo.account.Account;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author punith
 * @date 25-Apr-2020
 * @description Repository class to handle requests for AppUser table
 */
@Repository
public interface AccountRepository
		extends PagingAndSortingRepository<Account, Integer>, JpaSpecificationExecutor<Account> {

	/** Fetch record by mobile number */
	Account findByMobile(Long mobile);

	/** Fetch record by mobile number and password key */
	Account findByMobileAndPasswordKey(Long mobile, String passwordKey);
}
