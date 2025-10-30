package com.expenseTracker.Expense.Tracker.Controller;

import com.expenseTracker.Expense.Tracker.Entity.Expense;
import com.expenseTracker.Expense.Tracker.Entity.Users;
import com.expenseTracker.Expense.Tracker.Service.ExpenseService;
import com.expenseTracker.Expense.Tracker.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/expenses")
public class ExpenseController {
    private final ExpenseService expenseService;
    private final UserService userService;

    public ExpenseController(ExpenseService expenseService, UserService userService) {
        this.expenseService = expenseService; this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<?> list(Authentication auth) {
        Users user = userService.findByUsername(auth.getName());
        List<Expense> list = expenseService.getExpensesForUser(user);
        return ResponseEntity.ok(list);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Expense e, Authentication auth) {
        Users user = userService.findByUsername(auth.getName());
        e.setUser(user);
        Expense saved = expenseService.saveExpense(e);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Expense updated, Authentication auth) {
        // naive: ensure owner or admin
        Users user = userService.findByUsername(auth.getName());
        Expense existing = expenseService.getExpensesForUser(user).stream().filter(x -> x.getId().equals(id)).findFirst().orElse(null);
        if (existing == null && !auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return ResponseEntity.status(403).body("Forbidden");
        }
        if (existing == null) existing = expenseService.saveExpense(updated); // admin edit on any
        existing.setTitle(updated.getTitle());
        existing.setAmount(updated.getAmount());
        existing.setCategory(updated.getCategory());
        existing.setDate(updated.getDate());
        Expense saved = expenseService.saveExpense(existing);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, Authentication auth) {
        Users user = userService.findByUsername(auth.getName());
        boolean isOwner = expenseService.getExpensesForUser(user).stream().anyMatch(e -> e.getId().equals(id));
        boolean isAdmin = auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        if (!isOwner && !isAdmin) return ResponseEntity.status(403).body("Forbidden");
        expenseService.deleteById(id);
        return ResponseEntity.ok(Map.of("deleted", id));
    }
}