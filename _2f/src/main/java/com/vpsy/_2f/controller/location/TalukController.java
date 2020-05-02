package com.vpsy._2f.controller.location;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.vpsy._2f.repository.location.TalukRepository;
import com.vpsy._2f.repository.utility.SearchSpecificationBuilder;
import com.vpsy._2f.utility.Constants;
import com.vpsy._2f.vo.location.Taluk;

/**
 * @author punith
 * @date 22-Apr-2020
 * @description Controller class to handle requests for Taluk table
 */

@RestController
@RequestMapping(value = "/taluk")
public class TalukController {

	private TalukRepository talukRepository;
	
	@Autowired
	public TalukController(TalukRepository talukRepository) {
		this.talukRepository = talukRepository;
	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Taluk> create(@RequestBody Taluk taluk) {
		System.out.println("Taluk Create: " + taluk.toString());
		try {
			Taluk savedTaluk = talukRepository.save(taluk);
			return new ResponseEntity<>(savedTaluk, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
		}
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<HttpStatus> delete(@PathVariable("id") Integer id) {
		System.out.println("Taluk Delete: " + id);
		try {
			talukRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<Taluk>> getAll() {
		System.out.println("Taluk Find All: ");
		try {
			List<Taluk> taluks = new ArrayList<Taluk>();
			
			talukRepository.findAll(Sort.by("name")).forEach(taluk -> {
				taluks.add(new Taluk(taluk));
			});

			if (taluks.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(taluks, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Taluk>> search(@RequestParam(value = "value") String search) {
		System.out.println("Taluk Search: " + search);
		try {
			SearchSpecificationBuilder<Taluk> builder = new SearchSpecificationBuilder<Taluk>();
	        Pattern pattern = Pattern.compile(Constants.SEARCH_PATTERN);
	        Matcher matcher = pattern.matcher(search + ",");
	        while (matcher.find()) {
	            builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
	        }
	         
	        Specification<Taluk> spec = builder.build();
	        
			List<Taluk> taluks = new ArrayList<Taluk>();
			
			talukRepository.findAll(spec).forEach(taluk -> {
				taluks.add(new Taluk(taluk));
			});

			if (taluks.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(taluks, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Taluk> getById(@PathVariable("id") Integer id) {
		System.out.println("Taluk Get By ID: " + id);
		Optional<Taluk> talukInRepo = talukRepository.findById(id);
		if (talukInRepo.isPresent()) {
			Taluk requiredTaluk = new Taluk(talukInRepo.get());
			return new ResponseEntity<>(requiredTaluk, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<Taluk> update(@PathVariable("id") Integer id, @RequestBody Taluk taluk) {
		System.out.println("Taluk Update: " + taluk);
		Optional<Taluk> talukInRepo = talukRepository.findById(id);
		
		if (talukInRepo.isPresent() && (id == taluk.getId())) {
//			taluk.setPlaces(talukInRepo.get().getPlaces());
			
			Taluk updatedTaluk = talukRepository.save(taluk);
			
			return new ResponseEntity<>(new Taluk(updatedTaluk), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
