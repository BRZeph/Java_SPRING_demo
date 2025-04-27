package brzeph.backend.spring.java_spring_demo.resources;

import brzeph.backend.spring.java_spring_demo.entities.Category;
import brzeph.backend.spring.java_spring_demo.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/categories")
public class CategoryResource {

    @Autowired
    private CategoryService service;

    @GetMapping
    public ResponseEntity<List<Category>> findAll(){
        List<Category> obj = service.findAll();
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Category> findById(@PathVariable Long id){
        Category obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }
}
