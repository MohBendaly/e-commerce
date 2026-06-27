package e_commerce.produit.repository;

import e_commerce.produit.entity.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProduitRepository extends JpaRepository<Produit, Long> {

    List<Produit> id(int id);
    @Query("SELECT p FROM Produit p LEFT JOIN FETCH p.category")
    List<Produit> findAllWithCategory();

    @Query("SELECT p FROM Produit p LEFT JOIN FETCH p.category WHERE p.id = :id")
    Optional<Produit> findByIdWithCategory(@Param("id") Long id);
    List<Produit> findAll();
    Optional<Produit> findById(Long id);
}
