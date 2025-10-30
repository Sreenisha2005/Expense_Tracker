package com.expenseTracker.Expense.Tracker.Repository;


import com.expenseTracker.Expense.Tracker.Entity.Expense;
import com.expenseTracker.Expense.Tracker.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByUser(Users user);
    List<Expense> findByUserAndDateBetween(Users user, LocalDate from, LocalDate to);
}