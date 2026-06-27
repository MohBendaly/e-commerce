package e_commerce.produit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import e_commerce.produit.entity.User;
import e_commerce.produit.repository.UserRepository;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private  final UserRepository userRepository;

    @Autowired

    public UserService( UserRepository userRepository) {
        this.userRepository= userRepository;

    }

    public  void deleteUser(Long id) {
    }

    public  User getUserById(Long id) { return userRepository.findById(id).orElse(null);
    }

    public User updateUser(Long id, User updateUser) { UserRepository.updateUser(id, updateUser); return updateUser;
    }

    public  List<User> getAllUsers() { return userRepository.findAll();
    }

    public  User saveUser(User User) { return userRepository.save(User);
    }

    public User register(User user) {
        // Vérifier si l'email existe déjà pour éviter l'exception de contrainte unique
        if (user.getEmail() != null && !user.getEmail().isBlank()) {
            java.util.List<User> existing = userRepository.findAllByEmail(user.getEmail());
            if (!existing.isEmpty()) {
                throw new IllegalArgumentException("Email already exists: " + user.getEmail());
            }
        }
        return userRepository.save(user);
    }


   public Optional<User> findbyEmail(String email) {
    List<User> users = userRepository.findAllByEmail(email); // Utiliser une méthode qui retourne une liste
    if (users.isEmpty()) {
        return Optional.empty(); // Si aucun utilisateur trouvé, retourner vide
    }
    if (users.size() > 1) {
        // Solution : lever une exception spécifique ou gérer ce conflit différemment
        throw new IllegalArgumentException("Duplicate users found with the same email: " + email); 
    }
    return Optional.of(users.get(0)); // Retourne l'utilisateur unique si présent
}
}