package com.vpsy._2f.controller.advertise;

import com.vpsy._2f.repository.advertise.AdRepository;
import com.vpsy._2f.repository.utility.SearchSpecificationBuilder;
import com.vpsy._2f.utility.Constants;
import com.vpsy._2f.vo.advertise.Ad;
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
 * @description Controller class to handle requests for Ad table
 */

@RestController
@RequestMapping(value = "/ad")
public class AdController {

    private AdRepository adRepository;

    @Autowired
    public AdController(AdRepository adRepository) {
        this.adRepository = adRepository;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Ad> create(@RequestBody Ad ad) {
        System.out.println("Ad Create: " + ad.toString());
        try {
            Ad savedAd = adRepository.save(ad);
            return new ResponseEntity<>(savedAd, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Integer id) {
        System.out.println("Ad Delete: " + id);
        try {
            adRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Ad>> getAll() {
        System.out.println("State Find All: ");
        try {
            List<Ad> ads = new ArrayList<Ad>();

            adRepository.findAll(Sort.by("name")).forEach(ad -> {
                ads.add(new Ad(ad));
            });

            if (ads.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(ads, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Ad>> search(@RequestParam(value = "value") String search) {
        System.out.println("Ad Search: " + search);
        try {
            SearchSpecificationBuilder<Ad> builder = new SearchSpecificationBuilder<Ad>();
            Pattern pattern = Pattern.compile(Constants.SEARCH_PATTERN);
            Matcher matcher = pattern.matcher(search + ",");
            while (matcher.find()) {
                builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
            }

            Specification<Ad> spec = builder.build();

            List<Ad> ads = new ArrayList<Ad>();

            adRepository.findAll(spec).forEach(ad -> {
                ads.add(new Ad(ad));
            });

            if (ads.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(ads, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Ad> getById(@PathVariable("id") Integer id) {
        System.out.println("Ad Get By ID: " + id);
        Optional<Ad> adInRepo = adRepository.findById(id);
        if (adInRepo.isPresent()) {
            Ad requiredAd = new Ad(adInRepo.get());
            return new ResponseEntity<>(requiredAd, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<Ad> update(@PathVariable("id") Integer id, @RequestBody Ad ad) {
        System.out.println("State Update: " + ad);
        Optional<Ad> adInRepo = adRepository.findById(id);

        if (adInRepo.isPresent() && (id == ad.getId())) {
            ad.setDeals(adInRepo.get().getDeals());
            ad.setMessages(adInRepo.get().getMessages());
            ad.setAdPictures(adInRepo.get().getAdPictures());

            Ad updatedAd = adRepository.save(ad);

            return new ResponseEntity<>(new Ad(updatedAd), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}