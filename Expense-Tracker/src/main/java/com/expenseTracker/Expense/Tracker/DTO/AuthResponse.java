package com.expenseTracker.Expense.Tracker.DTO;
public class AuthResponse {
    private String token;
    public AuthResponse(String token) { this.token = token; }
    public String getToken() { return token; }
}
