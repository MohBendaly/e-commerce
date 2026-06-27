package e_commerce.produit.repository;

import e_commerce.produit.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    // Méthodes personnalisées (optionnelles)
    List<Order> findByStatus(String status);
    List<Order> findByUserId(Long userId);

    default List<Order>  findOrderByUserId(Long userId){
        return null;
    }

}
