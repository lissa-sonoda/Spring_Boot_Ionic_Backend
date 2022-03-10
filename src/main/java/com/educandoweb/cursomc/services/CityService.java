package com.educandoweb.cursomc.services;

import com.educandoweb.cursomc.domain.City;
import com.educandoweb.cursomc.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {

    @Autowired
    private CityRepository repo;

    public List<City> findByState(Integer stateId){
        return repo.findCities(stateId);
    }
}
