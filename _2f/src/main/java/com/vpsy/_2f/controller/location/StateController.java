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
import com.vpsy._2f.vo.location.District;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.vpsy._2f.repository.location.StateRepository;
import com.vpsy._2f.repository.utility.SearchSpecificationBuilder;
import com.vpsy._2f.utility.Constants;
import com.vpsy._2f.vo.location.State;

/**
 * @author punith
 * @date 22-Apr-2020
 * @description Controller class to handle requests for State table
 */

@RestController
@RequestMapping(value = "/state")
public class StateController {

	private StateRepository stateRepository;
	private AccountRepository accountRepository;
	private AdminAccountRepository adminAccountRepository;

	@Autowired
	public StateController(StateRepository stateRepository,
						   AccountRepository accountRepository,
						   AdminAccountRepository adminAccountRepository) {
		this.stateRepository = stateRepository;
		this.accountRepository = accountRepository;
		this.adminAccountRepository = adminAccountRepository;
	}

	/**
	 * The method creates a State record. Only admin users can create State records.
	 * @param mobile-mobile_number of an admin account.
	 * @param passwordKey-password_key of an admin account.
	 * @param state-state object to be created.
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<State> create(
			@RequestHeader(value = "mobile", required = true) long mobile,
			@RequestHeader(value = "passwordkey", required = true) String passwordKey,
			@RequestBody(required = true) State state) {
		System.out.println("State Create Request: " + state.toString());

		try {
			/* Check if the admin account exists. If no return 401 */
			AdminAccount _adminAccount = this.adminAccountRepository.findByMobileAndPasswordKey(mobile, passwordKey);
			if (_adminAccount == null) {
				System.out.println("Unauthorized user.");
				return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
			}

			State _state = stateRepository.save(state);
			return new ResponseEntity<>(_state, HttpStatus.OK);
		} catch (Exception e) {
			System.err.println("Error in creating state.");
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
		}
	}

	/**
	 * The method deletes the State record with given Id. Only admin users can delete State records.
	 * @param mobile-mobile_number of an admin account.
	 * @param passwordKey-password_key of an admin account.
	 * @param id-id of the state to be deleted.
	 * @return http status 200 is returned if the record is deleted.
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<HttpStatus> delete(
			@RequestHeader(value = "mobile", required = true) long mobile,
			@RequestHeader(value = "passwordkey", required = true) String passwordKey,
			@PathVariable(value = "id", required = true) Integer id) {
		System.out.println("State Delete Request: " + id);
		try {
			/* Check if the admin account exists. If no return 401 */
			AdminAccount _adminAccount = this.adminAccountRepository.findByMobileAndPasswordKey(mobile, passwordKey);
			if (_adminAccount == null) {
				System.out.println("Unauthorized user.");
				return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
			}

			stateRepository.deleteById(id);
			return new ResponseEntity<>(null, HttpStatus.OK);
		} catch (Exception e) {
			System.err.println("Error in deleting state.");
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}

	/**
	 * The method fetches all states. Any valid user can access all state records.
	 * @param mobile-mobile_number of an account.
	 * @param passwordKey-password_key of an account.
	 * @return List of States.
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<State>> getAll(
			@RequestHeader(value = "mobile", required = true) long mobile,
			@RequestHeader(value = "passwordkey", required = true) String passwordKey) {
		System.out.println("State Find All Request: ");

		try {
			/* Check if the account exists. If no return 401 */
			Account _account = this.accountRepository.findByMobileAndPasswordKey(mobile, passwordKey);
			if (_account == null) {
				System.out.println("Unauthorized user.");
				return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
			}

			List<State> states = new ArrayList<State>();
			
			stateRepository.findAll(Sort.by("name")).forEach(state -> {
				states.add(new State(state));
			});

			if (states.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(states, HttpStatus.OK);
		} catch (Exception e) {
			System.err.println("Error in fetching all states.");
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * The method searches for State records.
	 * @param mobile-mobile_number of an account.
	 * @param passwordKey-password_key of an account.
	 * @param search-search query with the example pattern column_name:value1,column_name>value2
	 * @return States satisfying given search criteria.
	 */
	@RequestMapping(value = "/search", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<State>> search(
			@RequestHeader(value = "mobile", required = true) long mobile,
			@RequestHeader(value = "passwordkey", required = true) String passwordKey,
    		@RequestParam(value = "value", required = true) String search) {
		System.out.println("State Search: " + search);
		try {
			/* Check if the account exists. If no return 401 */
			Account _account = this.accountRepository.findByMobileAndPasswordKey(mobile, passwordKey);
			if (_account == null) {
				System.out.println("Unauthorized user.");
				return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
			}

			SearchSpecificationBuilder<State> builder = new SearchSpecificationBuilder<State>();
	        Pattern pattern = Pattern.compile(Constants.SEARCH_PATTERN);
	        Matcher matcher = pattern.matcher(search + ",");
	        while (matcher.find()) {
	            builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
	        }
	         
	        Specification<State> spec = builder.build();
	        
			List<State> states = new ArrayList<State>();
			
			stateRepository.findAll(spec).forEach(state -> {
				states.add(new State(state));
			});

			if (states.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(states, HttpStatus.OK);
		} catch (Exception e) {
			System.err.println("Error in searching for states.");
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }

	/**
	 * The method finds the State record with given Id. Only admin users can find State records by its id.
	 * @param mobile-mobile_number of an admin account.
	 * @param passwordKey-password_key of an admin account.
	 * @param id-id of the state to be fetched.
	 * @return State record if the record is found.
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<State> getById(
			@RequestHeader(value = "mobile", required = true) long mobile,
			@RequestHeader(value = "passwordkey", required = true) String passwordKey,
			@PathVariable(value = "id", required = true) Integer id) {
		System.out.println("State Get By ID: " + id);

		try {
			/* Check if the admin account exists. If no return 401 */
			AdminAccount _adminAccount = this.adminAccountRepository.findByMobileAndPasswordKey(mobile, passwordKey);
			if (_adminAccount == null) {
				System.out.println("Unauthorized user.");
				return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
			}

			Optional<State> stateInRepo = stateRepository.findById(id);
			if (stateInRepo.isPresent()) {
				State requiredState = new State(stateInRepo.get());
				return new ResponseEntity<>(requiredState, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			System.err.println("Error in finding state by id.");
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}

	/**
	 * The method updates the State record with given Id. Only admin users can update State records.
	 * @param mobile-mobile_number of an admin account.
	 * @param passwordKey-password_key of an admin account.
	 * @param id-id of the state to be updated.
	 * @return Updated state record.
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<State> update(
			@RequestHeader(value = "mobile", required = true) long mobile,
			@RequestHeader(value = "passwordkey", required = true) String passwordKey,
			@PathVariable(value = "id", required = true) Integer id, @RequestBody State state) {
		System.out.println("State Update request: " + state);

		try {
			/* Check if the admin account exists. If no return 401 */
			AdminAccount _adminAccount = this.adminAccountRepository.findByMobileAndPasswordKey(mobile, passwordKey);
			if (_adminAccount == null) {
				System.out.println("Unauthorized user.");
				return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
			}

			Optional<State> optionalState = stateRepository.findById(id);

			if (optionalState.isPresent() && (id == state.getId())) {
				state.setDistricts(optionalState.get().getDistricts());
				State updatedState = stateRepository.save(state);

				return new ResponseEntity<>(new State(updatedState), HttpStatus.OK);
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
	 * The method fetches all districts for the given State Id. Any valid user can access all districts for given State Id.
	 * @param mobile-mobile_number of an account.
	 * @param passwordKey-password_key of an account.
	 * @param id-id of the state for which all districts to be fetched.
	 * @return List of Districts under the given state id.
	 */
	@RequestMapping(value = "/{id}/district", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Set<District>> districtsForGivenStateId(
			@RequestHeader(value = "mobile", required = true) long mobile,
			@RequestHeader(value = "passwordkey", required = true) String passwordKey,
			@PathVariable(value = "id") Integer id) {
		System.out.println("Fetch all districts for given state: " + id);

		try {
			/* Check if the account exists. If no return 401 */
			Account _account = this.accountRepository.findByMobileAndPasswordKey(mobile, passwordKey);
			if (_account == null) {
				System.out.println("Unauthorized user.");
				return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
			}

			Optional<State> optionalState = stateRepository.findById(id);

			if (optionalState.isPresent()) {
				Set<District> districts = optionalState.get().getDistricts();
				return new ResponseEntity<>(districts, HttpStatus.OK);
			}

			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			System.err.println("Error in fetching districts for given state id.");
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}
}
