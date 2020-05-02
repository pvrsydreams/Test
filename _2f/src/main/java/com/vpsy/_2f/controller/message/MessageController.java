package com.vpsy._2f.controller.message;

import com.vpsy._2f.repository.message.MessageRepository;
import com.vpsy._2f.repository.utility.SearchSpecificationBuilder;
import com.vpsy._2f.utility.Constants;
import com.vpsy._2f.vo.message.Message;
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
 * @description Controller class to handle requests for Message table
 */

@RestController
@RequestMapping(value = "/message")
public class MessageController {
	
	private MessageRepository messageRepository;
	
	@Autowired
	public MessageController(MessageRepository messageRepository) {
		this.messageRepository = messageRepository;
	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Message> create(@RequestBody Message message) {
		System.out.println("Message Create: " + message.toString());
		try {
			Message savedMessage = messageRepository.save(message);
			return new ResponseEntity<>(savedMessage, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
		}
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<HttpStatus> delete(@PathVariable("id") Integer id) {
		System.out.println("Message Delete: " + id);
		try {
			messageRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<Message>> getAll() {
		System.out.println("State Find All: ");
		try {
			List<Message> messages = new ArrayList<Message>();
			
			messageRepository.findAll(Sort.by("name")).forEach(message -> {
				messages.add(new Message(message));
			});

			if (messages.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(messages, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/search", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Message>> search(@RequestParam(value = "value") String search) {
		System.out.println("Message Search: " + search);
		try {
			SearchSpecificationBuilder<Message> builder = new SearchSpecificationBuilder<Message>();
	        Pattern pattern = Pattern.compile(Constants.SEARCH_PATTERN);
	        Matcher matcher = pattern.matcher(search + ",");
	        while (matcher.find()) {
	            builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
	        }
	         
	        Specification<Message> spec = builder.build();
	        
			List<Message> messages = new ArrayList<Message>();
			
			messageRepository.findAll(spec).forEach(message -> {
				messages.add(new Message(message));
			});

			if (messages.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(messages, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Message> getById(@PathVariable("id") Integer id) {
		System.out.println("Message Get By ID: " + id);
		Optional<Message> messageInRepo = messageRepository.findById(id);
		if (messageInRepo.isPresent()) {
			Message requiredMessage = new Message(messageInRepo.get());
			return new ResponseEntity<>(requiredMessage, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<Message> update(@PathVariable("id") Integer id, @RequestBody Message message) {
		System.out.println("State Update: " + message);
		Optional<Message> messageInRepo = messageRepository.findById(id);
		
		if (messageInRepo.isPresent() && (id == message.getId())) {
			Message updatedMessage = messageRepository.save(message);
			
			return new ResponseEntity<>(new Message(updatedMessage), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
