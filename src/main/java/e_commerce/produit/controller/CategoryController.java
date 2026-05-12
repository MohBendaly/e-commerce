package e_commerce.produit.controller;

import org.springframework.web.bind.annotation.*;
import e_commerce.produit.entity.Category;
import e_commerce.produit.service.CategoryService;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    List<Category> getAllCategories() {
         return categoryService.getAllCategories();
     }

    @GetMapping("/{id}")
    Category getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }

    @PostMapping("/create")
    Category saveCategory(@RequestBody Category Category) {
        return categoryService.saveCategory( Category);
    }

    @PutMapping("/update/{id}")
    Category updateCategory(@PathVariable Long id, @RequestBody Category Category) {
        return categoryService.updateCategory(id, Category);
    }

    @DeleteMapping("/delete/{id}")
    void deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
    }
}