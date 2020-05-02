package com.vpsy._2f.controller.account;

import com.vpsy._2f.dto.account.AccountRequest;
import com.vpsy._2f.dto.account.AccountResponse;
import com.vpsy._2f.dto.account.AdminAccountRequest;
import com.vpsy._2f.repository.account.AdminAccountRepository;
import com.vpsy._2f.utility.Authorization;
import com.vpsy._2f.vo.account.Account;
import com.vpsy._2f.vo.account.AccountStatus;
import com.vpsy._2f.vo.account.AdminAccount;
import com.vpsy._2f.vo.account.OneTimePassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author punith
 * @date 2020-04-26
 * @description Controller class to handle admin table request
 * Todo: The class should be deleted. It is there only to add admin account.
 */
@RestController
@RequestMapping(value = "/admin")
public class AdminController {

    private AdminAccountRepository adminAccountRepository;

    @Autowired
    public AdminController(AdminAccountRepository adminAccountRepository) {
        this.adminAccountRepository = adminAccountRepository;
    }

    /**
     * The method creates an admin account for given mobile_number and password combination.
     * @param adminAccountRequest-must have mobile_number and password.
     * @return AdminAccount-Admin account information
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<AdminAccount> createAccount(@RequestBody(required = true) AdminAccountRequest adminAccountRequest) {
        System.out.println("Create Admin Account Request: " + adminAccountRequest);

        try {
            /* Notify if the account already exists with the status code 208*/
            if (this.adminAccountRepository.findByMobile(adminAccountRequest.getMobile()) != null) {
                System.out.println("Account already exists");
                /** Http Status 208 is used if the account is already present */
                return new ResponseEntity<>(null, HttpStatus.ALREADY_REPORTED);
            }

            /* Generate random salt */
            String salt = Authorization.randomSalt();

            /* Hash password and otp using salt */
            String passwordKey = Authorization.hashWithSalt(adminAccountRequest.getPassword(), salt);

            AdminAccount _adminAccount  = new AdminAccount()
                    .setName(adminAccountRequest.getName())
                    .setMobile(adminAccountRequest.getMobile())
                    .setMailId(adminAccountRequest.getMailId())
                    .setPasswordKey(passwordKey)
                    .setSalt(salt)
                    .setAccountStatus(AccountStatus.VERIFIED);

            _adminAccount = this.adminAccountRepository.save(_adminAccount);

            return new ResponseEntity<>(_adminAccount, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error in creating admin account");
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
