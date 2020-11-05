package com.aldair.countries.controllers;

import com.aldair.countries.CheckCountry;
import com.aldair.countries.models.Country;
import com.aldair.countries.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Collections;

@RestController
public class CountryController {

    //filter function
    private static List<Country> filterCountry(List<Country> list, CheckCountry func) {
        List<Country> tempList = new ArrayList<>();
        for(Country c:list) {
            if(func.check(c)) {
                tempList.add(c);
            }
        }
        return tempList;
    }

    @Autowired
    private CountryRepository countryrepo;

    @GetMapping(value = "/countries/all", produces = {"application/json"})
    public ResponseEntity<?> getAllCountries() {

        List<Country> countryList = new ArrayList<>();
        countryrepo.findAll().iterator().forEachRemaining(countryList::add);
        countryList.sort((c1, c2) -> c1.getName().compareToIgnoreCase(c2.getName()));

        for(Country c:countryList) {
            System.out.println(c);
        }

        return new ResponseEntity<>(countryList, HttpStatus.OK);
    }

    @GetMapping(value = "/names/start/{letter}", produces = {"application/json"})
    public ResponseEntity<?> getAllByName(@PathVariable char letter) {
        List<Country> countryList = new ArrayList<>();
        countryrepo.findAll().iterator().forEachRemaining(countryList::add);

        List<Country> filteredList = filterCountry(countryList, c -> c.getName().charAt(0) == Character.toUpperCase(letter));
        return new ResponseEntity<>(filteredList, HttpStatus.OK);


    }

    @GetMapping(value = "/names/population/total", produces = {"application/json"})
    public ResponseEntity<?> getAllByFirstLetterName() {
        List<Country> countryList = new ArrayList<>();
        countryrepo.findAll().iterator().forEachRemaining(countryList::add);

        long totalPopulation = 0;
        for(Country c:countryList){
            totalPopulation += c.getPopulation();
        }
        return new ResponseEntity<>(totalPopulation, HttpStatus.OK);
    }

    @GetMapping(value = "/names/population/min", produces = {"application/json"})
    public ResponseEntity<?> getCountryMinPopulation() {
        List<Country> countryList = new ArrayList<>();
        countryrepo.findAll().iterator().forEachRemaining(countryList::add);

        Country smallestPopulation = Collections.min(countryList, Comparator.comparing(c -> c.getPopulation()));
        return new ResponseEntity<>(smallestPopulation, HttpStatus.OK);
    }

    @GetMapping(value = "/names/population/max", produces = {"application/json"})
    public ResponseEntity<?> getCountryMaxPopulation() {
        List<Country> countryList = new ArrayList<>();
        countryrepo.findAll().iterator().forEachRemaining(countryList::add);

        Country smallestPopulation = Collections.max(countryList, Comparator.comparing(c -> c.getPopulation()));
        return new ResponseEntity<>(smallestPopulation, HttpStatus.OK);
    }
}
