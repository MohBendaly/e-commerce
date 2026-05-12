package e_commerce.produit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import e_commerce.produit.entity.Category;
import e_commerce.produit.repository.CategoryRepository;
import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category savecategory(Category category) {
        return categoryRepository.save(category);
    }


    public Category getCategoryById(Long id) { return categoryRepository.findById(id).orElse(null);
    }

    public Category saveCategory(Category category) { return categoryRepository.save(category);
    }

    public Category updateCategory(Long id, Category category) { return categoryRepository.save(category);
    }

    public void deleteCategory(Long id) { categoryRepository.deleteById(id);
    }


}
