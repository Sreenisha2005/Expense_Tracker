package com.expenseTracker.Expense.Tracker.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "expenses")
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private double amount;
    private String category;
    private LocalDate date;

    // Link to user
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    // getters & setters
//    public Long getId() { return id; }
//    public void setId(Long id) { this.id = id; }
//    public String getTitle() { return title; }
//    public void setTitle(String title) { this.title = title; }
//    public double getAmount() { return amount; }
//    public void setAmount(double amount) { this.amount = amount; }
//    public String getCategory() { return category; }
//    public void setCategory(String category) { this.category = category; }
//    public LocalDate getDate() { return date; }
//    public void setDate(LocalDate date) { this.date = date; }
//    public User getUser() { return user; }
//    public void setUser(User user) { this.user = user; }
}