package com.vpsy._2f.controller.advertise;

import com.vpsy._2f.repository.advertise.AdPictureRepository;
import com.vpsy._2f.repository.utility.SearchSpecificationBuilder;
import com.vpsy._2f.utility.Constants;
import com.vpsy._2f.vo.advertise.AdPicture;
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
 * @description Controller class to handle requests for AdPicture table
 */

@RestController
@RequestMapping(value = "/adPicture")
public class AdPictureController{

    private AdPictureRepository adPictureRepository;

    @Autowired
    public AdPictureController(AdPictureRepository adPictureRepository) {
        this.adPictureRepository = adPictureRepository;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<AdPicture> create(@RequestBody AdPicture adPicture) {
        System.out.println("AdPicture Create: " + adPicture.toString());
        try {
            AdPicture savedAdPicture = adPictureRepository.save(adPicture);
            return new ResponseEntity<>(savedAdPicture, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Integer id) {
        System.out.println("AdPicture Delete: " + id);
        try {
            adPictureRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<AdPicture>> getAll() {
        System.out.println("State Find All: ");
        try {
            List<AdPicture> adPictures = new ArrayList<AdPicture>();

            adPictureRepository.findAll(Sort.by("name")).forEach(adPicture -> {
                adPictures.add(new AdPicture(adPicture));
            });

            if (adPictures.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(adPictures, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<AdPicture>> search(@RequestParam(value = "value") String search) {
        System.out.println("AdPicture Search: " + search);
        try {
            SearchSpecificationBuilder<AdPicture> builder = new SearchSpecificationBuilder<AdPicture>();
            Pattern pattern = Pattern.compile(Constants.SEARCH_PATTERN);
            Matcher matcher = pattern.matcher(search + ",");
            while (matcher.find()) {
                builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
            }

            Specification<AdPicture> spec = builder.build();

            List<AdPicture> adPictures = new ArrayList<AdPicture>();

            adPictureRepository.findAll(spec).forEach(adPicture -> {
                adPictures.add(new AdPicture(adPicture));
            });

            if (adPictures.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(adPictures, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<AdPicture> getById(@PathVariable("id") Integer id) {
        System.out.println("AdPicture Get By ID: " + id);
        Optional<AdPicture> adPictureInRepo = adPictureRepository.findById(id);
        if (adPictureInRepo.isPresent()) {
            AdPicture requiredAdPicture = new AdPicture(adPictureInRepo.get());
            return new ResponseEntity<>(requiredAdPicture, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<AdPicture> update(@PathVariable("id") Integer id, @RequestBody AdPicture adPicture) {
        System.out.println("State Update: " + adPicture);
        Optional<AdPicture> adPictureInRepo = adPictureRepository.findById(id);

        if (adPictureInRepo.isPresent() && (id == adPicture.getId())) {
            AdPicture updatedAdPicture = adPictureRepository.save(adPicture);

            return new ResponseEntity<>(new AdPicture(updatedAdPicture), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
