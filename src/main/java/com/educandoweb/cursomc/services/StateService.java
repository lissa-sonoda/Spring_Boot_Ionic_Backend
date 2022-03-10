package com.educandoweb.cursomc.services;

import com.educandoweb.cursomc.domain.State;
import com.educandoweb.cursomc.repositories.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StateService {

    @Autowired
    private StateRepository repo;

    public List<State> findAll(){
        return repo.findAllByOrderByName();
    }
}
