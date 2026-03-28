package e_commerce.produit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import e_commerce.produit.entity.Category;
import org.springframework.stereotype.Repository;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {


}
