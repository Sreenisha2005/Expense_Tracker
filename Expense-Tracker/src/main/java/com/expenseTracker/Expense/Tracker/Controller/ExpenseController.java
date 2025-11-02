package com.expenseTracker.Expense.Tracker.Controller;

import com.expenseTracker.Expense.Tracker.DTO.ExpenseDTO;
import com.expenseTracker.Expense.Tracker.Entity.Expense;
import com.expenseTracker.Expense.Tracker.Entity.Users;
import com.expenseTracker.Expense.Tracker.Service.ExpenseService;
import com.expenseTracker.Expense.Tracker.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {
    private final ExpenseService expenseService;
    private final UserService userService;


    @GetMapping
    public ResponseEntity<?> list(Authentication auth) {
        Users user = userService.findByUsername(auth.getName());
        List<Expense> list = expenseService.getExpensesForUser(user);
        return ResponseEntity.ok(list);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Expense e, Authentication auth) {
        Expense savedExpense = expenseService.addExpense(e, auth.getName());
        return ResponseEntity.ok(savedExpense);
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody ExpenseDTO updated, Authentication auth) {
        if (auth.isAuthenticated()){
            return ResponseEntity.ok().body(expenseService.update(id, updated));
        }
        return ResponseEntity.status(403).body("User not found! Try again");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, Authentication auth) {
        Users user = userService.findByUsername(auth.getName());
        boolean isOwner = expenseService.getExpensesForUser(user).stream().anyMatch(e -> e.getId().equals(id));
        if (!isOwner) return ResponseEntity.status(403).body("Forbidden");
        expenseService.deleteById(id);
        return ResponseEntity.ok(Map.of("deleted", id));
    }
}



//        Users user = userService.findByUsername(auth.getName());
//        Expense existing = expenseService.getExpensesForUser(user).stream().filter(x -> x.getId().equals(id)).findFirst().orElse(null);
//        if (auth.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
//            return ResponseEntity.status(403).body("Forbidden");
//        }
//        if (existing == null) existing = expenseService.saveExpense(updated);
//        existing = Expense.builder()
//                .title(updated.getTitle())
//                .date(updated.getDate())
//                .amount(updated.getAmount())
//                .category(updated.getCategory())
//                .build();
//
//        Expense saved = expenseService.saveExpense(existing);
//        return ResponseEntity.ok(saved);


//        Users user = userService.findByUsername(auth.getName());
//        e.setUser(user);
//        Expense saved = expenseService.saveExpense(e);
//        return ResponseEntity.ok(saved);