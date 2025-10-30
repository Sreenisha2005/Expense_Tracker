package com.expenseTracker.Expense.Tracker.Service;

import com.expenseTracker.Expense.Tracker.Entity.Users;
import com.expenseTracker.Expense.Tracker.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public Users registerUser(String username, String password) {
        Users u = Users.builder()
                .username(username)
                .password(encoder.encode(password))
                .role("ROLE_USER")
                .build();
        return userRepository.save(u);
    }

    public Users registerAdmin(String username, String password) {
        Users u = Users.builder()
                .username(username)
                .password(encoder.encode(password))
                .role("ROLE_ADMIN")
                .build();
        return userRepository.save(u);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users u = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new User(u.getUsername(), u.getPassword(),
                List.of(new SimpleGrantedAuthority(u.getRole())));
    }

    public Users findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }
}