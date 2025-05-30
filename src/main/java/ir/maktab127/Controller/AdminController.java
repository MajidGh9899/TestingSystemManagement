package ir.maktab127.Controller;

import ir.maktab127.entity.user.Admin;
import ir.maktab127.service.AdminService;

import java.util.Optional;

public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    public Optional<Admin> findByUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        if (username.length() < 3 || username.length() > 50) {
            throw new IllegalArgumentException("Username must be between 3 and 50 characters");
        }
        return adminService.findByUsername(username);
    }
} 