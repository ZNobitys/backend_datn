package org.example.user.controller;

import org.example.user.entity.User;
import org.example.user.request.CreateUserRequest;
import org.example.user.request.LoginUserRequest;
import org.example.user.request.UpdateUserRequest;
import org.example.user.security.CustomUserDetails;
import org.example.user.security.JwtTokenProvider;
import org.example.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody CreateUserRequest createUserRequest) {
        User user = userService.createUser(createUserRequest);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginUserRequest loginUserRequest) {
        User user = userService.getUserByEmail(loginUserRequest.getEmail());
        CustomUserDetails customUserDetails = new CustomUserDetails(user);
        String token = jwtTokenProvider.generateToken(customUserDetails);
        String roleName = user.getRole().toString();

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Đăng nhập thành công");
        response.put("token", token);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/getall")
    @PreAuthorize("hasRole('USER')")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/getuser/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public User getUserById(@PathVariable Integer id) {
        return userService.getUserById(id);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    User updateUser(@PathVariable Integer id, @RequestBody UpdateUserRequest updateUserRequest) {
        return userService.updateUserById(id, updateUserRequest);
    }
}
