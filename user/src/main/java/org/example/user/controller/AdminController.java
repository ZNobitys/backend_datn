package org.example.user.controller;

import org.bouncycastle.pqc.crypto.util.PQCOtherInfoGenerator;
import org.example.user.entity.User;
import org.example.user.request.AdminUpdateUserRequest;
import org.example.user.security.JwtTokenProvider;
import org.example.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    User adminUpdate(@PathVariable Integer id, @RequestBody AdminUpdateUserRequest adminUpdateUserRequest) {
        return userService.adminUpdateUserById(id, adminUpdateUserRequest);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    String deleteUser(@PathVariable Integer id) {
        userService.deleteUserById(id);
        return "Tài khoản đã được xóa thành công";
    }

}
