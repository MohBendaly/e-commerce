package e_commerce.produit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import e_commerce.produit.entity.OrderItem;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByStatus(String status);


}
