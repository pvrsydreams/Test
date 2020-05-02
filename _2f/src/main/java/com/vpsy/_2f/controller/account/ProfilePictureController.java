package com.vpsy._2f.controller.account;

import com.vpsy._2f.repository.account.AccountRepository;
import com.vpsy._2f.repository.account.AdminAccountRepository;
import com.vpsy._2f.repository.account.ProfilePictureRepository;
import com.vpsy._2f.repository.utility.SearchSpecificationBuilder;
import com.vpsy._2f.utility.Constants;
import com.vpsy._2f.vo.account.Account;
import com.vpsy._2f.vo.account.AccountStatus;
import com.vpsy._2f.vo.account.AdminAccount;
import com.vpsy._2f.vo.account.ProfilePicture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author punith
 * @date 23-Apr-2020
 * @description Controller class to handle requests for ProfilePicture table
 */
@RestController
@RequestMapping(value = "/profilepicture")
public class ProfilePictureController {

	private AccountRepository accountRepository;
	private AdminAccountRepository adminAccountRepository;
	private ProfilePictureRepository profilePictureRepository;

	@Autowired
	public ProfilePictureController(AccountRepository accountRepository,
			AdminAccountRepository adminAccountRepository,
			ProfilePictureRepository profilePictureRepository) {
		this.accountRepository = accountRepository;
		this.adminAccountRepository = adminAccountRepository;
		this.profilePictureRepository = profilePictureRepository;
	}

	/**
	 * The method creates profile_photo for the given user account.
	 * @param mobile-mobile_number of the account.
	 * @param passwordKey-password_key of the account.
	 * @param photo-profile_photo of the account.
	 * @return The profile picture information.
	 */
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ProfilePicture> create(
			@RequestHeader(value = "mobile", required = true) long mobile,
			@RequestHeader(value = "passwordkey", required = true) String passwordKey,
			@RequestPart(value = "photo", required = true) MultipartFile photo) {

		System.out.println("ProfilePicture Create Request: ");

		try {
			/* Check if the account exists. If no return 401 */
			Account _account = this.accountRepository.findByMobileAndPasswordKey(mobile, passwordKey);
			if (_account == null) {
				System.out.println("Account does not exist. Invalid credentials.");
				return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
			}

			/* Check if the account is verified. If no return 401 */
			if(_account.getAccountStatus() != AccountStatus.VERIFIED) {
				System.out.println("Account is not verified.");
				return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
			}

			/* Create profile_photo object */
			ProfilePicture profilePicture = new ProfilePicture()
					.setAccount(_account)
					.setProfilePhoto(photo.getBytes());

			/* Save the profile_photo object */
			ProfilePicture _profilePicture = this.profilePictureRepository.save(profilePicture);

			return new ResponseEntity<>(new ProfilePicture(_profilePicture), HttpStatus.OK);
		} catch (Exception e) {
			System.err.println("Error while saving profile picture.");
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * The method deletes profile_photo for the given user account.
	 * @param mobile-mobile_number of the account.
	 * @param passwordKey-password_key of the account.
	 * @param id-id of the profile_photo to be deleted.
	 * @return http status 200 if the profile_picture is deleted.
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<HttpStatus> delete(
			@RequestHeader(value = "mobile", required = true) long mobile,
			@RequestHeader(value = "passwordkey", required = true) String passwordKey,
			@PathVariable(value = "id", required = true) Integer id) {
		System.out.println("ProfilePicture id to be deleted = " + id);

		try {
			/* Check if the account exists. If no return 401 */
			Account _account = this.accountRepository.findByMobileAndPasswordKey(mobile, passwordKey);
			if (_account == null) {
				System.out.println("Account does not exist. Invalid credentials.");
				return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
			}

			/* Check if the account is verified. If no return 401 */
			if(_account.getAccountStatus() != AccountStatus.VERIFIED) {
				System.out.println("Account is not verified.");
				return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
			}

			/* Delete the profile_photo and return 200 */
			profilePictureRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			System.err.println("Error while deleting profile picture.");
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}

	/**
	 * The method updates profile_photo information for given user account.
	 * @param mobile-mobile_number of the account.
	 * @param passwordKey-password_key of the account.
	 * @param photo-profile_photo of the account.
	 * @return The profile picture information.
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ProfilePicture> update(
			@RequestHeader(value = "mobile", required = true) long mobile,
			@RequestHeader(value = "passwordkey", required = true) String passwordKey,
			@RequestPart(value = "photo", required = true) MultipartFile photo) {
		System.out.println("ProfilePicture id to be updated");

		try {
			/* Check if the account exists. If no return 401 */
			Account _account = this.accountRepository.findByMobileAndPasswordKey(mobile, passwordKey);
			if (_account == null) {
				System.out.println("Account does not exist. Invalid credentials.");
				return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
			}

			/* Check if the account is verified. If no return 401 */
			if(_account.getAccountStatus() != AccountStatus.VERIFIED) {
				System.out.println("Account is not verified.");
				return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
			}

			ProfilePicture _profilePicture = _account.getProfilePicture();
			if(_profilePicture != null) {
				_profilePicture.setProfilePhoto(photo.getBytes());
				_profilePicture = this.profilePictureRepository.save(_profilePicture);
				return new ResponseEntity<>(new ProfilePicture(_profilePicture), HttpStatus.OK);
			}

			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

		} catch (Exception e) {
			System.err.println("Error while updating profile picture.");
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * The method gets all profile_photos. Only admin users can access this.
	 * @param mobile-mobile_number of an admin account.
	 * @param passwordKey-password_key of an admin account.
	 * @return List of profile_photos.
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<ProfilePicture>> getAll(
			@RequestHeader(value = "mobile", required = true) long mobile,
			@RequestHeader(value = "passwordkey", required = true) String passwordKey) {
		System.out.println("Profile picture find All: ");

		try {
			/* Check if the admin account exists. If no return 401 */
			AdminAccount _adminAccount = this.adminAccountRepository.findByMobileAndPasswordKey(mobile, passwordKey);
			if (_adminAccount == null) {
				System.out.println("Account does not exist. Invalid credentials.");
				return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
			}

			List<ProfilePicture> profilePictures = new ArrayList<ProfilePicture>();

			profilePictureRepository.findAll(Sort.by("name")).forEach(profilePicture -> {
				profilePictures.add(new ProfilePicture(profilePicture));
			});

			if (profilePictures.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(profilePictures, HttpStatus.OK);
		} catch (Exception e) {
			System.err.println("Error in fetching all profile pictures.");
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * The method finds profile_photo for given profile_photo id.
	 * @param id-profile_photo id.
	 * @return ProfilePicture-profile_photo information.
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<ProfilePicture> getById(
			@RequestHeader(value = "mobile", required = true) long mobile,
			@RequestHeader(value = "passwordkey", required = true) String passwordKey,
			@PathVariable(value = "id", required = true) Integer id) {
		System.out.println("ProfilePicture Get By ID: " + id);

		try {
			/* Check if the account exists. If no return 401 */
			Account _account = this.accountRepository.findByMobileAndPasswordKey(mobile, passwordKey);
			if (_account == null) {
				System.out.println("Account does not exist. Invalid credentials.");
				return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
			}

			/* Check if the account is verified. If no return 401 */
			if(_account.getAccountStatus() != AccountStatus.VERIFIED) {
				System.out.println("Account is not verified.");
				return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
			}

			/* Get the profile_photo using id */
			Optional<ProfilePicture> optionalProfilePicture = this.profilePictureRepository.findById(id);
			if (optionalProfilePicture.isPresent()) {
				ProfilePicture _profilePicture = new ProfilePicture(optionalProfilePicture.get());
				return new ResponseEntity<>(new ProfilePicture(_profilePicture), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			System.err.println("Error while getting profile picture.");
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}


	}

	/**
	 * Todo: Think if the method is required
	 * @param search
	 * @return
	 */
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<ProfilePicture>> search(@RequestParam(value = "value") String search) {
		System.out.println("ProfilePicture Search: " + search);
		try {
			SearchSpecificationBuilder<ProfilePicture> builder = new SearchSpecificationBuilder<ProfilePicture>();
			Pattern pattern = Pattern.compile(Constants.SEARCH_PATTERN);
			Matcher matcher = pattern.matcher(search + ",");
			while (matcher.find()) {
				builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
			}

			Specification<ProfilePicture> spec = builder.build();

			List<ProfilePicture> profilePictures = new ArrayList<ProfilePicture>();

			profilePictureRepository.findAll(spec).forEach(profilePicture -> {
				profilePictures.add(new ProfilePicture(profilePicture));
			});

			if (profilePictures.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(profilePictures, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
