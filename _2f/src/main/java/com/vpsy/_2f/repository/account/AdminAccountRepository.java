package com.vpsy._2f.repository.account;

import com.vpsy._2f.vo.account.AdminAccount;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author punith
 * @date 2020-04-26
 * @description
 */
public interface AdminAccountRepository extends PagingAndSortingRepository<AdminAccount, Long>, JpaSpecificationExecutor<AdminAccount> {

    /** Fetch record by mobile number */
    AdminAccount findByMobile(Long mobile);

    /** Fetch record by mobile number and password key */
    AdminAccount findByMobileAndPasswordKey(Long mobile, String passwordKey);
}
