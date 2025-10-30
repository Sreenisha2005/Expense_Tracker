package com.expenseTracker.Expense.Tracker.Controller;

import com.expenseTracker.Expense.Tracker.DTO.AuthRequest;
import com.expenseTracker.Expense.Tracker.DTO.AuthResponse;
import com.expenseTracker.Expense.Tracker.Entity.Users;
import com.expenseTracker.Expense.Tracker.Security.JwtUtil;
import com.expenseTracker.Expense.Tracker.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authManager;

    public AuthController(UserService userService, JwtUtil jwtUtil, AuthenticationManager authManager) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.authManager = authManager;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequest req) {
        Users u = userService.registerUser(req.getUsername(), req.getPassword());
        return ResponseEntity.ok(u.getUsername());
    }

    @PostMapping("/register-admin")
    public ResponseEntity<?> registerAdmin(@RequestBody AuthRequest req) {
        Users u = userService.registerAdmin(req.getUsername(), req.getPassword());
        return ResponseEntity.ok(u.getUsername());
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest req) {
        Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));
        String role = auth.getAuthorities().stream().findFirst().get().getAuthority();
        String token = jwtUtil.generateToken(req.getUsername(), role);
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
