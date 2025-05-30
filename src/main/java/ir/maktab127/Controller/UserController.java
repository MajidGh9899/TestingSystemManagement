package ir.maktab127.Controller;

import ir.maktab127.entity.enumeration.UserStatus;
import ir.maktab127.entity.user.User;
import ir.maktab127.service.UserService;

import java.util.List;
import java.util.Optional;

public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public Optional<User> findByUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        
        return userService.findByUsername(username);
    }

    public List<User> findByStatus(UserStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }
        return userService.findByStatus(status);
    }

    public List<User> findByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
      

        return userService.findByName(name);
    }

    public boolean existsByUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        if (username.length() < 3 || username.length() > 50) {
            throw new IllegalArgumentException("Username must be between 3 and 50 characters");
        }
        return userService.existsByUsername(username);
    }

    public void updateStatus(Long userId, UserStatus status) {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }
        if (status == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }
        userService.updateStatus(userId, status);
    }
} 