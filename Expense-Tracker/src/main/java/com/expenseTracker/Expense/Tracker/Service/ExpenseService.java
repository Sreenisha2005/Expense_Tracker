package com.expenseTracker.Expense.Tracker.Service;

import com.expenseTracker.Expense.Tracker.DTO.ExpenseDTO;
import com.expenseTracker.Expense.Tracker.Entity.Expense;
import com.expenseTracker.Expense.Tracker.Entity.Users;
import com.expenseTracker.Expense.Tracker.Repository.ExpenseRepository;
import com.expenseTracker.Expense.Tracker.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ExpenseService {
    private final ExpenseRepository repo;
    private final UserRepository userRepository;

    public List<Expense> getExpensesForUser(Users user) {
        return repo.findByUser(user);
    }

    public Expense saveExpense(ExpenseDTO e) {
        Expense expense = Expense.builder()
                .title(e.getTitle())
                .amount(e.getAmount())
                .date(e.getDate())
                .category(e.getCategory())
                .build();
        return repo.save(expense);
    }

    public Expense update(Long id, ExpenseDTO updated){
        Expense expense = repo.getReferenceById(id);
        if (expense == null){
            return Expense.builder()
                    .title(updated.getTitle())
                    .date(updated.getDate())
                    .amount(updated.getAmount())
                    .category(updated.getCategory())
                    .user(userRepository.getReferenceById(id))
                    .build();
        }
        return saveExpense(updated);
    }

    public void deleteById(Long id) { repo.deleteById(id); }

    public List<Expense> getExpensesForUserBetween(Users user, LocalDate from, LocalDate to) {
        return repo.findByUserAndDateBetween(user, from, to);
    }

    public Expense addExpense(Expense expense, String username) {
        Users user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        expense.setUser(user);
        return repo.save(expense);
    }
}