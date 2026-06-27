package e_commerce.produit.controller;

import e_commerce.produit.Dto.ProduitDto;
import e_commerce.produit.entity.Produit;
import e_commerce.produit.service.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/produit")
@CrossOrigin(origins = "http://localhost:4200")
 public class ProduitController {

    private final ProduitService produitService;

    @Autowired
    public ProduitController(ProduitService produitService) {
        this.produitService = produitService;
    }

    @GetMapping("/all")
    public List<ProduitDto> getAllProduits() {  // ← Changé List<Object> en List<ProduitDto>
        List<Produit> produits = produitService.getAllProduits();
        return produits.stream()
                .map(ProduitDto::fromEntity)
                .collect(Collectors.toList());
    }

    private ProduitDto convertToDto(Produit produit) {
        ProduitDto dto = new ProduitDto();
        dto.setId(produit.getId());
        dto.setNom(produit.getNom());
        dto.setDescription(produit.getDescription());
        dto.setPrix(produit.getPrix());
        dto.setQuantite(produit.getQuantite());
        dto.setImageUrl(produit.getImageUrl());  // ← important
        return dto;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProduitDto> getProduitById(@PathVariable Long id) {
        Produit produit = produitService.getProduitById(id);
        if (produit == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ProduitDto.fromEntity(produit));
    }

    @PostMapping("/create")
    public ResponseEntity<ProduitDto> createProduit(@RequestBody ProduitDto produitDto) {
        Produit savedProduit = produitService.saveProduit(produitDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ProduitDto.fromEntity(savedProduit));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ProduitDto> updateProduit(@PathVariable Long id, @RequestBody ProduitDto produitDto) {
        Produit updatedProduit = produitService.updateProduit(id, produitDto);
        if (updatedProduit == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ProduitDto.fromEntity(updatedProduit));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProduit(@PathVariable Long id) {
        Produit existingProduit = produitService.getProduitById(id);
        if (existingProduit == null) {
            return ResponseEntity.notFound().build();
        }
        produitService.deleteProduit(id);
        return ResponseEntity.noContent().build();
    }
}