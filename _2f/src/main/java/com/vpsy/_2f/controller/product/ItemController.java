package com.vpsy._2f.controller.product;

import com.vpsy._2f.repository.product.ItemRepository;
import com.vpsy._2f.repository.utility.SearchSpecificationBuilder;
import com.vpsy._2f.utility.Constants;
import com.vpsy._2f.vo.product.Item;
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

@RestController
@RequestMapping(value = "/item")
public class ItemController {

    private ItemRepository itemRepository;

    @Autowired
    public ItemController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Item> create(@RequestBody Item item) {
        System.out.println("Item Create: " + item.toString());
        try {
            Item savedItem = itemRepository.save(item);
            return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Integer id) {
        System.out.println("Item Delete: " + id);
        try {
            itemRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Item>> getAll() {
        System.out.println("State Find All: ");
        try {
            List<Item> items = new ArrayList<Item>();

            itemRepository.findAll(Sort.by("name")).forEach(item -> {
                items.add(new Item(item));
            });

            if (items.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(items, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Item>> search(@RequestParam(value = "value") String search) {
        System.out.println("Item Search: " + search);
        try {
            SearchSpecificationBuilder<Item> builder = new SearchSpecificationBuilder<Item>();
            Pattern pattern = Pattern.compile(Constants.SEARCH_PATTERN);
            Matcher matcher = pattern.matcher(search + ",");
            while (matcher.find()) {
                builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
            }

            Specification<Item> spec = builder.build();

            List<Item> items = new ArrayList<Item>();

            itemRepository.findAll(spec).forEach(item -> {
                items.add(new Item(item));
            });

            if (items.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(items, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Item> getById(@PathVariable("id") Integer id) {
        System.out.println("Item Get By ID: " + id);
        Optional<Item> itemInRepo = itemRepository.findById(id);
        if (itemInRepo.isPresent()) {
            Item requiredItem = new Item(itemInRepo.get());
            return new ResponseEntity<>(requiredItem, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<Item> update(@PathVariable("id") Integer id, @RequestBody Item item) {
        System.out.println("State Update: " + item);
        Optional<Item> itemInRepo = itemRepository.findById(id);

        if (itemInRepo.isPresent() && (id == item.getId())) {
            item.setAds(itemInRepo.get().getAds());

            Item updatedItem = itemRepository.save(item);

            return new ResponseEntity<>(new Item(updatedItem), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
