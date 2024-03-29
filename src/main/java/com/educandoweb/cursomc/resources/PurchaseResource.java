package com.educandoweb.cursomc.resources;

import com.educandoweb.cursomc.domain.Category;
import com.educandoweb.cursomc.domain.Purchase;
import com.educandoweb.cursomc.dto.CategoryDTO;
import com.educandoweb.cursomc.services.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value = "/purchases")
public class PurchaseResource {

    @Autowired
    private PurchaseService service;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Purchase> find(@PathVariable Integer id){
        Purchase obj = service.search(id);
        return ResponseEntity.ok().body(obj);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> insert(@Valid @RequestBody Purchase obj){
        obj = service.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Page<Purchase>> findPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24")Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "instant")String orderBy,
            @RequestParam(value = "direction", defaultValue = "DESC")String direction){
        Page<Purchase> list = service.findPage(page, linesPerPage, orderBy,direction);
        return ResponseEntity.ok().body(list);
    }
}
