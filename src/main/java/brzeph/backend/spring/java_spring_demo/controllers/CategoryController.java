package brzeph.backend.spring.java_spring_demo.controllers;

import brzeph.backend.spring.java_spring_demo.entities.products.ProductCategory;
import brzeph.backend.spring.java_spring_demo.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @PreAuthorize("hasAuthority('READ_PRODUCT_CAT')")
    @GetMapping
    public ResponseEntity<List<ProductCategory>> findAll(){
        List<ProductCategory> obj = service.findAll();
        return ResponseEntity.ok().body(obj);
    }

    @PreAuthorize("hasAuthority('READ_PRODUCT_CAT')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductCategory> findById(@PathVariable Long id){
        ProductCategory obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }
}
