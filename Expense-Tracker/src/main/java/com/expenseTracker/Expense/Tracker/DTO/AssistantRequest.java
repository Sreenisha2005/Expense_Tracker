package com.expenseTracker.Expense.Tracker.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssistantRequest {
    private String username; // optional
    private String question;
    // getters/setters
}