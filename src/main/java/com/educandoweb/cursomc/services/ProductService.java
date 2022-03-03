package com.educandoweb.cursomc.services;

import com.educandoweb.cursomc.domain.Category;
import com.educandoweb.cursomc.domain.Product;
import com.educandoweb.cursomc.repositories.CategoryRepository;
import com.educandoweb.cursomc.repositories.ProductRepository;
import com.educandoweb.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repo;

    @Autowired
    private CategoryRepository categoryRepository;

    public Product search(Integer id){
        Optional<Product> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Object not found! Id: " + id + ", Type: " + Product.class.getName()
        ));
    }

    public Page<Product> find(String name, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        List<Category> categories = categoryRepository.findAllById(ids);
        //return repo.search(name, categories, pageRequest);
        return repo.findDistinctByNameContainingAndCategoriesIn(name, categories, pageRequest);
    }
}
