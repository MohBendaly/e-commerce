package e_commerce.produit.controller;

import org.springframework.web.bind.annotation.*;
import e_commerce.produit.entity.Produit;
import e_commerce.produit.service.ProduitService;
import java.util.List;

@RestController
@RequestMapping("/produit")
public class ProduitController {

    private final ProduitService produitService;

    public ProduitController(ProduitService produitService) {
        this.produitService = produitService;
    }

    @GetMapping("/all")
     List<Produit> getAllProduits() {
        return produitService.getAllProduits();
    }

    @GetMapping("/{id}")
     Produit getProduitById(@PathVariable Long id) {
        return produitService.getProduitById(id);
    }

    @PostMapping("/create")
     Produit saveProduit(@RequestBody Produit produit) {
        return produitService.saveProduit( produit);
    }
    @PutMapping("/update/{id}")
     Produit updateProduit(@PathVariable Long id, @RequestBody Produit produit) {
        return produitService.updateProduit(id, produit);
    }

    @DeleteMapping("/delete/{id}")
     void deleteProduit(@PathVariable Long id) {
        produitService.deleteProduit(id);
    }

}
