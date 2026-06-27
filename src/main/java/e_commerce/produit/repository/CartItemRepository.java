package e_commerce.produit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import e_commerce.produit.entity.CartItem;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long>{
    List<CartItem> findByStatus(String status);

    List<CartItem> findByTotalGreaterThan(double total);

    @Query("SELECT ci FROM CartItem ci LEFT JOIN FETCH ci.produit")
    List<CartItem> findAllWithProduit();

}
