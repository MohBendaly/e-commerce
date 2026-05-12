package e_commerce.produit.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import e_commerce.produit.entity.Order;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    // Méthode personnalisée (Spring la génère automatiquement)
    List<Order> findByStatus(String status);

    List<Order> findByTotalGreaterThan(double total);
}