package com.expenseTracker.Expense.Tracker.Controller;

import com.expenseTracker.Expense.Tracker.Entity.Users;
import com.expenseTracker.Expense.Tracker.Repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/admin")
public class AdminController {
    private final UserRepository userRepository;

    public AdminController(UserRepository userRepository) { this.userRepository = userRepository; }

    @GetMapping("/users")
    public List<Users> listUsers() {
        return userRepository.findAll();
    }
}