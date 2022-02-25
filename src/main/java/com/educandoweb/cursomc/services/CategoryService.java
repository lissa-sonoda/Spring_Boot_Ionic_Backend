package com.educandoweb.cursomc.services;

import com.educandoweb.cursomc.domain.Category;
import com.educandoweb.cursomc.repositories.CategoryRepository;
import com.educandoweb.cursomc.services.exceptions.DataIntegrityException;
import com.educandoweb.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repo;

    public Category search(Integer id) {
        Optional<Category> obj = repo.findById(id);
        return    obj.orElseThrow(() -> new ObjectNotFoundException(
                "Object not found! Id: " + id + ", Type: " + Category.class.getName()));
    }

    public Category insert(Category obj){
        obj.setId(null);
        return repo.save(obj);
    }

    public Category update(Category obj){
        search(obj.getId());
        return repo.save(obj);
    }

    public void delete(Integer id){
        search(id);
        try{
            repo.deleteById(id);
        }
        catch (DataIntegrityViolationException e){
            throw new DataIntegrityException("It's not possible to delete a category that has products!");
        }
    }

    public List<Category> findAll(){
        return repo.findAll();
    }
}
