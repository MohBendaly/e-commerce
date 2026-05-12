package e_commerce.produit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import e_commerce.produit.entity.Cart;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByStatus(String status);

    List<Cart> findByTotalGreaterThan(double total);
}
