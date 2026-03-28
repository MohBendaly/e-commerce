package e_commerce.produit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import e_commerce.produit.entity.User;
import e_commerce.produit.repository.UserRepository;
import java.util.List;

@Service
public class UserService {

    private  final UserRepository userRepository;
    @Autowired

    public UserService(UserRepository userRepository) {
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

    public User register(User user) { return userRepository.save(user);
    }

    public User login(User user) {return userRepository.findByEmail(user.getEmail());
    }
}
