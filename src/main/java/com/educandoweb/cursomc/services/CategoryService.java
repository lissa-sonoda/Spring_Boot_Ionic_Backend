package com.educandoweb.cursomc.services;

import com.educandoweb.cursomc.domain.Category;
import com.educandoweb.cursomc.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repo;

    public Category search(Integer id) {
        Optional<Category> obj = repo.findById(id);
        return obj.orElse(null);
    }
}
