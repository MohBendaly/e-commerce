package e_commerce.produit.service;

import e_commerce.produit.Dto.ProduitDto;
import e_commerce.produit.entity.Category;
import e_commerce.produit.entity.Produit;
import e_commerce.produit.repository.CategoryRepository;
import e_commerce.produit.repository.ProduitRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
public class ProduitService {
    private final ProduitRepository produitRepository;
    private final CategoryRepository categoryRepository;
    private Category defaultCategory;

    @Autowired
    public ProduitService(ProduitRepository produitRepository, CategoryRepository categoryRepository) {
        this.produitRepository = produitRepository;
        this.categoryRepository = categoryRepository;
    }

    @PostConstruct
    public void initDefaultCategory() {
        defaultCategory = categoryRepository.findByNom("Default")
                .orElseGet(() -> {
                    Category cat = new Category();
                    cat.setNom("Default");
                    cat.setDescription("Catégorie par défaut");
                    return categoryRepository.save(cat);
                });
        System.out.println("✅ Catégorie par défaut créée : " + defaultCategory.getNom());
    }

    public List<Produit> getAllProduits() {
        return produitRepository.findAll();
    }

    public Produit saveProduit(ProduitDto dto) {
        Category category;
        if (dto.getCategoryId() == null) {
            category = defaultCategory;
        } else {
            category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category avec Id " + dto.getCategoryId() + " non trouvé"));
        }

        Produit produit = new Produit();
        produit.setNom(dto.getNom());
        produit.setDescription(dto.getDescription());
        produit.setPrix(dto.getPrix());
        produit.setQuantite(dto.getQuantite());
        produit.setCategory(category);
        return produitRepository.save(produit);
    }

    public Produit getProduitById(Long id) {
        return produitRepository.findById(id).orElse(null);
    }

    public Produit updateProduit(Long id, ProduitDto dto) {
        Produit existingProduit = produitRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produit avec l'id " + id + " non trouvé"));

        Category category;
        if (dto.getCategoryId() == null) {
            category = defaultCategory;
        } else {
            category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category avec Id " + dto.getCategoryId() + " non trouvé"));
        }

        existingProduit.setNom(dto.getNom());
        existingProduit.setDescription(dto.getDescription());
        existingProduit.setPrix(dto.getPrix());
        existingProduit.setQuantite(dto.getQuantite());
        existingProduit.setCategory(category);
        return produitRepository.save(existingProduit);
    }

    public void deleteProduit(Long id) {
         produitRepository.deleteById(id);
    }

    }
