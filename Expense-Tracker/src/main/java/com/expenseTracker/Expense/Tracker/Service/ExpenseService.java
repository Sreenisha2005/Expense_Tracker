package com.expenseTracker.Expense.Tracker.Service;

import com.expenseTracker.Expense.Tracker.Entity.Expense;
import com.expenseTracker.Expense.Tracker.Entity.Users;
import com.expenseTracker.Expense.Tracker.Repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ExpenseService {
    private final ExpenseRepository repo;

    public ExpenseService(ExpenseRepository repo) { this.repo = repo; }

    public List<Expense> getExpensesForUser(Users user) {
        return repo.findByUser(user);
    }

    public Expense saveExpense(Expense e) { return repo.save(e); }

    public void deleteById(Long id) { repo.deleteById(id); }

    public List<Expense> getExpensesForUserBetween(Users user, LocalDate from, LocalDate to) {
        return repo.findByUserAndDateBetween(user, from, to);
    }
}