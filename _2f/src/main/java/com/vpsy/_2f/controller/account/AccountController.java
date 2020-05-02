package com.vpsy._2f.controller.account;

import com.vpsy._2f.dto.account.*;
import com.vpsy._2f.dto.response.SignUpResponse;
import com.vpsy._2f.repository.account.AccountRepository;
import com.vpsy._2f.repository.account.OneTimePasswordRepository;
import com.vpsy._2f.utility.Authorization;
import com.vpsy._2f.utility.Constants;
import com.vpsy._2f.utility.HashResult;
import com.vpsy._2f.vo.account.Account;
import com.vpsy._2f.vo.account.AccountStatus;
import com.vpsy._2f.vo.account.OneTimePassword;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

/**
 * @author punith
 * @date 2020-04-25
 * @description Controller class to handle requests for User accounts
 */
@RestController
@RequestMapping(value = "/account")
public class AccountController {

    private DozerBeanMapper beanMapper;

    private AccountRepository accountRepository;

    private OneTimePasswordRepository oneTimePasswordRepository;


    @Autowired
    public AccountController(DozerBeanMapper beanMapper, AccountRepository accountRepository,
                             OneTimePasswordRepository oneTimePasswordRepository) {
        this.beanMapper = beanMapper;
        this.accountRepository = accountRepository;
        this.oneTimePasswordRepository = oneTimePasswordRepository;
    }

    /**
     * The method creates an account for given mobile_number and password combination.
     * It also initialises account verification process by sending a randomly generated OTP to the received mobile number.
     * The account should be verified with an OTP.
     * @param accountRequest-must have mobile_number and password.
     * @return AccountResponse-Account information.
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<AccountResponse> createAccount(@RequestBody(required = true) AccountRequest accountRequest) {
        System.out.println("Create Account Request: " + accountRequest);

        try {
            /* Notify if the account already exists with the status code 208*/
            if (this.accountRepository.findByMobile(accountRequest.getMobile()) != null) {
                System.out.println("Account already exists");
                /** Http Status 208 is used if the account is already present */
                return new ResponseEntity<>(null, HttpStatus.ALREADY_REPORTED);
            }

            /* Generate Random OTP and Salt */
            String otp = Authorization.randomOTP();
            String salt = Authorization.randomSalt();

            /* Hash password and otp using salt */
            String passwordKey = Authorization.hashWithSalt(accountRequest.getPassword(), salt);
            String otpKey = Authorization.hashWithSalt(otp, salt);

            System.out.println("Generated OTP: " + otp);
            System.out.println("Generated Salt: " + salt);

            /* Save OTP and Account in Database */
            OneTimePassword _oneTimePassword = new OneTimePassword(accountRequest.getMobile(), otpKey);

            Account _account = new Account();
            _account.setMobile(accountRequest.getMobile())
                    .setPasswordKey(passwordKey)
                    .setSalt(salt)
                    .setAccountStatus(AccountStatus.PENDING_VERIFICATION);

            oneTimePasswordRepository.save(_oneTimePassword);
            _account = accountRepository.save(_account);

            // Todo: SMS OTP
            System.out.println(String.format("Send OTP: %s to Mobile: %d!", otp, accountRequest.getMobile()));

            AccountResponse accountResponse = beanMapper.map(_account, AccountResponse.class);
            return new ResponseEntity<>(accountResponse, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error in verifying account");
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * The method updates an account for given mobile_number and password combination.
     * @param account-account object to be updated.
     * @return AccountResponse-Account information.
     */
    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<AccountResponse> updateAccount(@RequestBody(required = true) Account account) {
        System.out.println("Update Account Request: " + account);

        try {
            /* Check if the account exists. If no return 401 */
            Account _account = this.accountRepository.findByMobileAndPasswordKey(account.getMobile(), account.getPasswordKey());
            if (_account == null) {
                System.out.println("Account does not exist.");
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }

            account.setId(_account.getId())
                .setDeals(_account.getDeals())
                .setMessagesSent(_account.getMessagesSent())
                .setMessagesReceived(_account.getMessagesReceived())
                .setAds(_account.getAds());

            _account = this.accountRepository.save(account);
            AccountResponse accountResponse = beanMapper.map(_account, AccountResponse.class);
            return new ResponseEntity<>(accountResponse, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error in verifying account");
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * The method is used to authorize user account. If the user sends invalid password for 3 continuous times,
     * the account should be blocked. // Todo: Add this logic
     * @param loginRequest-must have mobile_number and password.
     * @return AccountResponse-Account information.
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<AccountResponse> login(@RequestBody(required = true) AccountRequest loginRequest) {
        System.out.println("Login Request: " + loginRequest);

        try {
            /* Check if the account exists with the given mobile number */
            Account _account = this.accountRepository.findByMobile(loginRequest.getMobile());
            if (_account == null) {
                System.out.println("Account does not exist.");
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }

            String passwordKey = Authorization.hashWithSalt(loginRequest.getPassword(), _account.getSalt());

            if(passwordKey.equals(_account.getPasswordKey())) {
                _account.setFailedLoginAttempts(0);
                _account = this.accountRepository.save(_account);
                AccountResponse accountResponse = beanMapper.map(_account, AccountResponse.class);
                return new ResponseEntity<>(accountResponse, HttpStatus.OK);
            }

            _account.setFailedLoginAttempts(_account.getFailedLoginAttempts() + 1);
            this.accountRepository.save(_account);
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            System.out.println("Error in authorizing account");
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * The method is used to initialize verification process by sending a randomly generated OTP to the received mobile_number.
     * The verification process is initialized only when the account is in 'PENDING_VERIFICATION' status.
     * If the account is already verified, the user will be notified with http status code 208.
     * @param accountVerificationRequest-must have mobile_number and password_key
     * @return AccountResponse-Account information.
     */
    @RequestMapping(value = "/verification", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<AccountResponse> initializeOtp(@RequestBody(required = true) AccountVerificationRequest accountVerificationRequest) {
        System.out.println("Start verification Request: " + accountVerificationRequest);

        try {
            /* Check if the account exists. If no return 401 */
            Account _account = this.accountRepository.findByMobileAndPasswordKey(accountVerificationRequest.getMobile(), accountVerificationRequest.getPasswordKey());
            if (_account == null) {
                System.out.println("Account does not exist.");
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }

            if (_account.getAccountStatus() != AccountStatus.PENDING_VERIFICATION) {
                System.out.println("The account is already verified.");
                return new ResponseEntity<>(null, HttpStatus.ALREADY_REPORTED);
            }

            /* Generate Random OTP and Hash OTP */
            String otp = Authorization.randomOTP();
            System.out.println("otp = " + otp);
            String otpKey = Authorization.hashWithSalt(otp, _account.getSalt());
            System.out.println("otpKey = " + otpKey);

            OneTimePassword _oneTimePassword = new OneTimePassword(accountVerificationRequest.getMobile(), otpKey);
            oneTimePasswordRepository.save(_oneTimePassword);

            // Todo: SMS OTP

            AccountResponse accountResponse = beanMapper.map(_account, AccountResponse.class);
            return new ResponseEntity<>(accountResponse, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error in initialising account verification process");
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * The method verifies the received account with OTP. OTP lasts for 10 minutes. If the OTP is expired the OTP entry will be
     * removed from database. If the account is successfully verified, its status will get changed to VERIFIED.
     * @param accountVerificationRequest-must have mobile_number, password_key and OTP received.
     * @return AccountResponse-Account information.
     */
    @RequestMapping(value = "/verification", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<AccountResponse> verifyAccount(@RequestBody(required = true) AccountVerificationRequest accountVerificationRequest) {
        System.out.println("Account Verification Request: " + accountVerificationRequest);

        try {
            /* Check if the account exists. If no return 401 */
            Account _account = this.accountRepository.findByMobileAndPasswordKey(accountVerificationRequest.getMobile(),
                    accountVerificationRequest.getPasswordKey());
            if (_account == null) {
                System.out.println("Account does not exist.");
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }

            /* Hash otp and check if it exists and not expired */
            String otpKey = Authorization.hashWithSalt(accountVerificationRequest.getOtp(), _account.getSalt());
            System.out.println("OTP Key: " + otpKey);
            OneTimePassword _oneTimePassword = this.oneTimePasswordRepository.findByMobileAndOtpKey(accountVerificationRequest.getMobile(), otpKey);

            /* If the otp record does not exist return 401 */
            if(_oneTimePassword == null) {
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }

            /* Check if the accountVerificationRequest is expired. If it is expired return 403 */
            if(new Date().getTime() > _oneTimePassword.getCreatedAt().getTime() + Constants.OTP_EXPIRES_IN) {
                System.out.println("Expired");
                this.oneTimePasswordRepository.delete(_oneTimePassword);
                return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
            }

            /* Update account status to verified and delete the otp */
            _account.setAccountStatus(AccountStatus.VERIFIED);
            _account = this.accountRepository.save(_account);

            this.oneTimePasswordRepository.delete(_oneTimePassword);

            AccountResponse accountResponse = beanMapper.map(_account, AccountResponse.class);
            return new ResponseEntity<>(accountResponse, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error in creating account");
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * The method resets the password for given valid account. It returns 401 if the credentials are invalid.
     * @param resetPasswordRequest-must have mobile_number, password_key of existing password and new_password.
     * @return AccountResponse-Account information.
     */
    @RequestMapping(value = "/password", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<AccountResponse> resetPassword(@RequestBody(required = true) ResetPasswordRequest resetPasswordRequest) {
        System.out.println("Reset password request: " + resetPasswordRequest);

        try {
            /* Check if the account exists. If no return 401 */
            Account _account = this.accountRepository.findByMobileAndPasswordKey(resetPasswordRequest.getMobile(), resetPasswordRequest.getPasswordKey());
            if (_account == null) {
                System.out.println("Account does not exist.");
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }

            /* Hash new password and update password in the account */
            String salt = _account.getSalt();
            String newPasswordKey = Authorization.hashWithSalt(resetPasswordRequest.getNewPassword(), salt);
            _account.setPasswordKey(newPasswordKey);
            _account = this.accountRepository.save(_account);

            AccountResponse accountResponse = beanMapper.map(_account, AccountResponse.class);
            return new ResponseEntity<>(accountResponse, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error in resetting the password.");
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * The method sends a randomly generated OTP to the given mobile number if the account exists for it.
     * The validity of the generated OTP is 10 minutes.
     * @param mobile
     * @return http status 200 if the account exists.
     */
    @RequestMapping(value = "/password/forgot", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> initializeForgotPassword(@RequestParam(value = "mobile", required = true) long mobile) {
        System.out.println("Mobile number: " + mobile);

        try {
            /* Check if the account exists with the given mobile number */
            Account _account = this.accountRepository.findByMobile(mobile);
            if (_account == null) {
                System.out.println("Account does not exist.");
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }

            /* Generate Random OTP and Salt */
            String otp = Authorization.randomOTP();
            String salt = _account.getSalt();

            /* Hash the otp using salt */
            String otpKey = Authorization.hashWithSalt(otp, salt);

            System.out.println("Generated OTP: " + otp);


            /* Save OTP in database */
            OneTimePassword _oneTimePassword = new OneTimePassword(mobile, otpKey);
            this.oneTimePasswordRepository.save(_oneTimePassword);

            // Todo: SMS OTP

            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error in initializing forgot password OTP.");
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * The method updates the password of the given account if the received OTP is valid.
     * It also update status of the account to VERIFIED as it is indirectly verified by OTP.
     * @param forgotPasswordRequest-must have mobile_number, otp received and new_password.
     * @return http status 200 if the account exists.
     */
    @RequestMapping(value = "/password/forgot", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> changePassword(@RequestBody(required = true) ForgotPasswordRequest forgotPasswordRequest) {
        System.out.println("Forgot password request: " + forgotPasswordRequest);

        try {
            /* Check if the account exists with the given mobile number */
            Account _account = this.accountRepository.findByMobile(forgotPasswordRequest.getMobile());
            if (_account == null) {
                System.out.println("Account does not exist.");
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }

            /* Hash otp and check if it exists and not expired */
            String otpKey = Authorization.hashWithSalt(forgotPasswordRequest.getOtp(), _account.getSalt());
            System.out.println("OTP Key: " + otpKey);
            OneTimePassword _oneTimePassword = this.oneTimePasswordRepository.findByMobileAndOtpKey(forgotPasswordRequest.getMobile(), otpKey);

            /* If the otp record does not exist return 401 */
            if(_oneTimePassword == null) {
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }

            /* Check if the accountVerificationRequest is expired. If it is expired return 403 */
            if(new Date().getTime() > _oneTimePassword.getCreatedAt().getTime() + Constants.OTP_EXPIRES_IN) {
                System.out.println("Expired");
                this.oneTimePasswordRepository.delete(_oneTimePassword);
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }

            /* Update account status to verified and delete the otp */
            _account.setAccountStatus(AccountStatus.VERIFIED);
            _account = this.accountRepository.save(_account);

            this.oneTimePasswordRepository.delete(_oneTimePassword);

            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error in changing the lost password.");
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
