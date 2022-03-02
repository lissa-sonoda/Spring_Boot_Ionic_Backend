package com.educandoweb.cursomc.services;

import com.educandoweb.cursomc.domain.Purchase;
import com.educandoweb.cursomc.repositories.PurchaseRepository;
import com.educandoweb.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PurchaseService {

    @Autowired
    private PurchaseRepository repo;

    public Purchase search(Integer id){
        Optional<Purchase> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + id + ", Tipo: " + Purchase.class.getName()
        ));
    }
}
