package com.vpsy._2f.controller.advertise;

import com.vpsy._2f.repository.advertise.DealRepository;
import com.vpsy._2f.repository.utility.SearchSpecificationBuilder;
import com.vpsy._2f.utility.Constants;
import com.vpsy._2f.vo.advertise.Deal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author punith
 * @date 23-Apr-2020
 * @description Controller class to handle requests for Deal table
 */

@RestController
@RequestMapping(value = "/deal")
public class DealController {

	private DealRepository dealRepository;

	@Autowired
	public DealController(DealRepository dealRepository) {
		this.dealRepository = dealRepository;
	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Deal> create(@RequestBody Deal deal) {
		System.out.println("Deal Create: " + deal.toString());
		try {
			Deal savedDeal = dealRepository.save(deal);
			return new ResponseEntity<>(savedDeal, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
		}
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<HttpStatus> delete(@PathVariable("id") Integer id) {
		System.out.println("Deal Delete: " + id);
		try {
			dealRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<Deal>> getAll() {
		System.out.println("State Find All: ");
		try {
			List<Deal> deals = new ArrayList<Deal>();
			
			dealRepository.findAll(Sort.by("name")).forEach(deal -> {
				deals.add(new Deal(deal));
			});

			if (deals.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(deals, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/search", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Deal>> search(@RequestParam(value = "value") String search) {
		System.out.println("Deal Search: " + search);
		try {
			SearchSpecificationBuilder<Deal> builder = new SearchSpecificationBuilder<Deal>();
	        Pattern pattern = Pattern.compile(Constants.SEARCH_PATTERN);
	        Matcher matcher = pattern.matcher(search + ",");
	        while (matcher.find()) {
	            builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
	        }
	         
	        Specification<Deal> spec = builder.build();
	        
			List<Deal> deals = new ArrayList<Deal>();
			
			dealRepository.findAll(spec).forEach(deal -> {
				deals.add(new Deal(deal));
			});

			if (deals.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(deals, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Deal> getById(@PathVariable("id") Integer id) {
		System.out.println("Deal Get By ID: " + id);
		Optional<Deal> dealInRepo = dealRepository.findById(id);
		if (dealInRepo.isPresent()) {
			Deal requiredDeal = new Deal(dealInRepo.get());
			return new ResponseEntity<>(requiredDeal, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<Deal> update(@PathVariable("id") Integer id, @RequestBody Deal deal) {
		System.out.println("State Update: " + deal);
		Optional<Deal> dealInRepo = dealRepository.findById(id);
		
		if (dealInRepo.isPresent() && (id == deal.getId())) {
			Deal updatedDeal = dealRepository.save(deal);
			
			return new ResponseEntity<>(new Deal(updatedDeal), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
