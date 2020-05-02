package com.vpsy._2f.controller.location;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.vpsy._2f.repository.account.AccountRepository;
import com.vpsy._2f.repository.account.AdminAccountRepository;
import com.vpsy._2f.vo.account.Account;
import com.vpsy._2f.vo.account.AdminAccount;
import com.vpsy._2f.vo.location.State;
import com.vpsy._2f.vo.location.Taluk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.vpsy._2f.repository.location.DistrictRepository;
import com.vpsy._2f.repository.utility.SearchSpecificationBuilder;
import com.vpsy._2f.utility.Constants;
import com.vpsy._2f.vo.location.District;

/**
 * @author punith
 * @date 23-Apr-2020
 * @description Controller class to handle requests for District table
 */
@RestController
@RequestMapping(value = "/district")
public class DistrictController {

	private AccountRepository accountRepository;
	private DistrictRepository districtRepository;
	private AdminAccountRepository adminAccountRepository;


	@Autowired
	public DistrictController(AccountRepository accountRepository, DistrictRepository districtRepository, AdminAccountRepository adminAccountRepository) {
		this.accountRepository = accountRepository;
		this.districtRepository = districtRepository;
		this.adminAccountRepository = adminAccountRepository;
	}

	/**
	 * The method creates a District record. Only admin users can create District records.
	 * @param mobile-mobile_number of an admin account.
	 * @param passwordKey-password_key of an admin account.
	 * @param district-district object to be created.
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<District> create(
			@RequestHeader(value = "mobile", required = true) long mobile,
			@RequestHeader(value = "passwordkey", required = true) String passwordKey,
			@RequestBody(required = true) District district) {
		System.out.println("District Create Request: " + district.toString());

		try {
			/* Check if the admin account exists. If no return 401 */
			AdminAccount _adminAccount = this.adminAccountRepository.findByMobileAndPasswordKey(mobile, passwordKey);
			if (_adminAccount == null) {
				System.out.println("Unauthorized user.");
				return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
			}

			District _district = districtRepository.save(district);
			return new ResponseEntity<>(_district, HttpStatus.OK);
		} catch (Exception e) {
			System.err.println("Error in creating district.");
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
		}
	}

	/**
	 * The method deletes the District record with given Id. Only admin users can delete District records.
	 * @param mobile-mobile_number of an admin account.
	 * @param passwordKey-password_key of an admin account.
	 * @param id-id of the district to be deleted.
	 * @return http status 200 is returned if the record is deleted.
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<HttpStatus> delete(
			@RequestHeader(value = "mobile", required = true) long mobile,
			@RequestHeader(value = "passwordkey", required = true) String passwordKey,
			@PathVariable(value = "id", required = true) Integer id) {
		System.out.println("District Delete: " + id);
		try {
			/* Check if the admin account exists. If no return 401 */
			AdminAccount _adminAccount = this.adminAccountRepository.findByMobileAndPasswordKey(mobile, passwordKey);
			if (_adminAccount == null) {
				System.out.println("Unauthorized user.");
				return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
			}

			districtRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			System.err.println("Error in deleting district.");
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}

	/**
	 * The method fetches all districts. Any valid user can access all district records.
	 * @param mobile-mobile_number of an account.
	 * @param passwordKey-password_key of an account.
	 * @return List of Districts.
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<District>> getAll(
			@RequestHeader(value = "mobile", required = true) long mobile,
			@RequestHeader(value = "passwordkey", required = true) String passwordKey) {
		System.out.println("District Find All: ");

		try {
			/* Check if the account exists. If no return 401 */
			Account _account = this.accountRepository.findByMobileAndPasswordKey(mobile, passwordKey);
			if (_account == null) {
				System.out.println("Unauthorized user.");
				return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
			}

			List<District> districts = new ArrayList<District>();
			
			districtRepository.findAll(Sort.by("name")).forEach(district -> {
				districts.add(new District(district));
			});

			if (districts.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(districts, HttpStatus.OK);
		} catch (Exception e) {
			System.err.println("Error in fetching all districts.");
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * The method searches for District records.
	 * @param mobile-mobile_number of an account.
	 * @param passwordKey-password_key of an account.
	 * @param search-search query with the example pattern column_name:value1,column_name>value2
	 * @return Districts satisfying given search criteria.
	 */
	@RequestMapping(value = "/search", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<District>> search(
			@RequestHeader(value = "mobile", required = true) long mobile,
			@RequestHeader(value = "passwordkey", required = true) String passwordKey,
    		@RequestParam(value = "value", required = true) String search) {
		System.out.println("District Search: " + search);

		try {
			/* Check if the account exists. If no return 401 */
			Account _account = this.accountRepository.findByMobileAndPasswordKey(mobile, passwordKey);
			if (_account == null) {
				System.out.println("Unauthorized user.");
				return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
			}

			SearchSpecificationBuilder<District> builder = new SearchSpecificationBuilder<District>();
	        Pattern pattern = Pattern.compile(Constants.SEARCH_PATTERN);
	        Matcher matcher = pattern.matcher(search + ",");
	        while (matcher.find()) {
	            builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
	        }
	         
	        Specification<District> spec = builder.build();
	        
			List<District> districts = new ArrayList<District>();
			
			districtRepository.findAll(spec).forEach(district -> {
				districts.add(new District(district));
			});

			if (districts.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(districts, HttpStatus.OK);
		} catch (Exception e) {
			System.err.println("Error in searching for districts.");
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }

	/**
	 * The method finds the District record with given Id. Only admin users can find District records by its id.
	 * @param mobile-mobile_number of an admin account.
	 * @param passwordKey-password_key of an admin account.
	 * @param id-id of the district to be fetched.
	 * @return District record if the record is found.
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<District> getById(
			@RequestHeader(value = "mobile", required = true) long mobile,
			@RequestHeader(value = "passwordkey", required = true) String passwordKey,
			@PathVariable("id") Integer id) {
		System.out.println("District Get By ID: " + id);
		try {
			/* Check if the admin account exists. If no return 401 */
			AdminAccount _adminAccount = this.adminAccountRepository.findByMobileAndPasswordKey(mobile, passwordKey);
			if (_adminAccount == null) {
				System.out.println("Unauthorized user.");
				return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
			}

			Optional<District> optionalDistrict = districtRepository.findById(id);
			if (optionalDistrict.isPresent()) {
				District _district = new District(optionalDistrict.get());
				return new ResponseEntity<>(_district, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			System.err.println("Error in finding district by id.");
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}

	/**
	 * The method updates the District record with given Id. Only admin users can update District records.
	 * @param mobile-mobile_number of an admin account.
	 * @param passwordKey-password_key of an admin account.
	 * @param id-id of the district to be updated.
	 * @return Updated district record.
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<District> update(
			@RequestHeader(value = "mobile", required = true) long mobile,
			@RequestHeader(value = "passwordkey", required = true) String passwordKey,
			@PathVariable(value = "id", required = true) Integer id, @RequestBody District district) {
		System.out.println("State Update: " + district);

		try {
			/* Check if the admin account exists. If no return 401 */
			AdminAccount _adminAccount = this.adminAccountRepository.findByMobileAndPasswordKey(mobile, passwordKey);
			if (_adminAccount == null) {
				System.out.println("Unauthorized user.");
				return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
			}

			Optional<District> optionalDistrict = districtRepository.findById(id);

			if (optionalDistrict.isPresent() && (id == district.getId())) {
				district.setTaluks(optionalDistrict.get().getTaluks());

				District _district = districtRepository.save(district);
				return new ResponseEntity<>(new District(_district), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			System.err.println("Error in updating state.");
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}

	/**
	 * The method fetches all taluks for the given District Id. Any valid user can access all taluks for given District Id.
	 * @param mobile-mobile_number of an account.
	 * @param passwordKey-password_key of an account.
	 * @param id-id of the district for which all taluks to be fetched.
	 * @return List of Districts under the given state id.
	 */
	@RequestMapping(value = "/{id}/taluk", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Set<Taluk>> districtsForGivenStateId(
			@RequestHeader(value = "mobile", required = true) long mobile,
			@RequestHeader(value = "passwordkey", required = true) String passwordKey,
			@PathVariable(value = "id") Integer id) {
		System.out.println("Fetch all taluks for given district: " + id);

		try {
			/* Check if the account exists. If no return 401 */
			Account _account = this.accountRepository.findByMobileAndPasswordKey(mobile, passwordKey);
			if (_account == null) {
				System.out.println("Unauthorized user.");
				return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
			}

			Optional<District> optionalDistrict = districtRepository.findById(id);

			if (optionalDistrict.isPresent()) {
				Set<Taluk> taluks = optionalDistrict.get().getTaluks();
				return new ResponseEntity<>(taluks, HttpStatus.OK);
			}

			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			System.err.println("Error in fetching taluks for given district id.");
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}
}
