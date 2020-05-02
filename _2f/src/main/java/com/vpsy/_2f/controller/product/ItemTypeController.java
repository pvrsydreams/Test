package com.vpsy._2f.controller.product;

import com.vpsy._2f.repository.product.ItemTypeRepository;
import com.vpsy._2f.repository.utility.SearchSpecificationBuilder;
import com.vpsy._2f.utility.Constants;
import com.vpsy._2f.vo.product.ItemType;
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
 * @date 25-Apr-2020
 * @description Controller class to handle requests for ItemType table
 */
@RestController
@RequestMapping(value = "/itemType")
public class ItemTypeController {
    private ItemTypeRepository itemTypeRepository;

    @Autowired
    public ItemTypeController(ItemTypeRepository itemTypeRepository) {
        this.itemTypeRepository = itemTypeRepository;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ItemType> create(@RequestBody ItemType itemType) {
        System.out.println("ItemType Create: " + itemType.toString());
        try {
            ItemType savedItemType = itemTypeRepository.save(itemType);
            return new ResponseEntity<>(savedItemType, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Integer id) {
        System.out.println("ItemType Delete: " + id);
        try {
            itemTypeRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<ItemType>> getAll() {
        System.out.println("State Find All: ");
        try {
            List<ItemType> itemTypes = new ArrayList<ItemType>();

            itemTypeRepository.findAll(Sort.by("name")).forEach(itemType -> {
                itemTypes.add(new ItemType(itemType));
            });

            if (itemTypes.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(itemTypes, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<ItemType>> search(@RequestParam(value = "value") String search) {
        System.out.println("ItemType Search: " + search);
        try {
            SearchSpecificationBuilder<ItemType> builder = new SearchSpecificationBuilder<ItemType>();
            Pattern pattern = Pattern.compile(Constants.SEARCH_PATTERN);
            Matcher matcher = pattern.matcher(search + ",");
            while (matcher.find()) {
                builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
            }

            Specification<ItemType> spec = builder.build();

            List<ItemType> itemTypes = new ArrayList<ItemType>();

            itemTypeRepository.findAll(spec).forEach(itemType -> {
                itemTypes.add(new ItemType(itemType));
            });

            if (itemTypes.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(itemTypes, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ItemType> getById(@PathVariable("id") Integer id) {
        System.out.println("ItemType Get By ID: " + id);
        Optional<ItemType> itemTypeInRepo = itemTypeRepository.findById(id);
        if (itemTypeInRepo.isPresent()) {
            ItemType requiredItemType = new ItemType(itemTypeInRepo.get());
            return new ResponseEntity<>(requiredItemType, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<ItemType> update(@PathVariable("id") Integer id, @RequestBody ItemType itemType) {
        System.out.println("State Update: " + itemType);
        Optional<ItemType> itemTypeInRepo = itemTypeRepository.findById(id);

        if (itemTypeInRepo.isPresent() && (id == itemType.getId())) {
            itemType.setItems(itemTypeInRepo.get().getItems());

            ItemType updatedItemType = itemTypeRepository.save(itemType);

            return new ResponseEntity<>(new ItemType(updatedItemType), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
