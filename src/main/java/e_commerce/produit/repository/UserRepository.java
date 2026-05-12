package e_commerce.produit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import e_commerce.produit.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {


    static User updateUser(Long id, User user) {
        return null;
    }

    User findByEmail(String email) ;

      Optional<User> findById(Long id);
}
