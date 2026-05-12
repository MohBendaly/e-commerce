package e_commerce.produit.service;

import e_commerce.produit.entity.Produit;
import e_commerce.produit.repository.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProduitService {
    private final ProduitRepository ProduitRepository;

    @Autowired
    public ProduitService(ProduitRepository ProduitRepository) {
        this.ProduitRepository = ProduitRepository;
    }

    public List<Produit> getAllProduits() {
        return ProduitRepository.findAll();
    }

    public Produit saveProduit(Produit Produit) {
        return ProduitRepository.save(Produit);
    }

    public Produit getProduitById(Long id) {
        return ProduitRepository.findById(id).orElse(null);
    }

    public Produit updateProduit(Long id, Produit Produit) {
        return ProduitRepository.save(Produit);
    }

    public void deleteProduit(Long id) {
         ProduitRepository.deleteById(id);

    }
}
