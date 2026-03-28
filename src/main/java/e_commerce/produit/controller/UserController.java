package e_commerce.produit.controller;

import org.springframework.web.bind.annotation.*;
import e_commerce.produit.entity.User;
import e_commerce.produit.service.UserService;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping
    List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PostMapping("/create")
    User saveUser(@RequestBody User User) {
        return  userService.saveUser( User);
    }

    @PutMapping("/update")
    List<User> updateUser(@PathVariable Long id, @RequestBody User User) {
        return (List<User>) userService.updateUser(id, User);
    }


    @DeleteMapping("/delete")
    void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
