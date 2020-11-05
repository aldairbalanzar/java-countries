package com.aldair.countries.repositories;

import com.aldair.countries.models.Country;
import org.springframework.data.repository.CrudRepository;

public interface CountryRepository extends CrudRepository<Country, Long> {

}
