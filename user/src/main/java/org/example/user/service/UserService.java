package org.example.user.service;

import org.example.user.entity.Role;
import org.example.user.entity.User;
import org.example.user.repository.RoleRepository;
import org.example.user.repository.UserRepository;
import org.example.user.request.AdminUpdateUserRequest;
import org.example.user.request.CreateUserRequest;
import org.example.user.request.UpdateUserRequest;
import org.example.user.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createUser(@RequestBody @Validated CreateUserRequest createUserRequest) {
        if (userRepository.findByEmail(createUserRequest.getEmail()).isPresent()) {
            throw new RuntimeException("Email đã tồn tại");
        }

        if (createUserRequest.getRole() == null || createUserRequest.getRole().isEmpty()) {
            throw new RuntimeException("Roles không được để trống");
        }
        Role role = roleRepository.findByRoleName(createUserRequest.getRole())
                .orElseThrow(() -> new RuntimeException("Role không tồn tại"));

        User user = new User();
        user.setFullName(createUserRequest.getFullName());
        user.setEmail(createUserRequest.getEmail());
        user.setPassword(passwordEncoder.encode(createUserRequest.getPassword()));
        user.setRole(role);
        return userRepository.save(user);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Email người dùng không tồn tại"));
    }

    public UserDetails getUserDetailById(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Id người dùng không tồn tại"));
        return new CustomUserDetails(user);
    }

    public User getUserById(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("Id người dùng không tồn tại"));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUserById(Integer id, UpdateUserRequest updateUserRequest) {
        User user = getUserById(id);
        user.setFullName(updateUserRequest.getFullName());
        user.setEmail(updateUserRequest.getEmail());
        user.setPassword(updateUserRequest.getPassword());
        user.setAge(updateUserRequest.getAge());
        user.setImg(updateUserRequest.getImg());
        if (updateUserRequest.getPassword() != null && !updateUserRequest.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(updateUserRequest.getPassword()));
        }
        return userRepository.save(user);
    }

    public void deleteUserById(Integer id) {
        userRepository.delete(getUserById(id));
    }

    public User adminUpdateUserById(Integer id, AdminUpdateUserRequest adminUpdateUserRequest) {
        User user = getUserById(id);
        user.setFullName(adminUpdateUserRequest.getFullName());
        user.setEmail(adminUpdateUserRequest.getEmail());
        user.setPassword(adminUpdateUserRequest.getPassword());
        user.setAge(adminUpdateUserRequest.getAge());

        if (adminUpdateUserRequest.getPassword() != null && !adminUpdateUserRequest.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(adminUpdateUserRequest.getPassword()));
        }
        if (adminUpdateUserRequest.getRole() != null && !adminUpdateUserRequest.getRole().isEmpty()) {
            Optional<Role> roleOptional = roleRepository.findByRoleName(adminUpdateUserRequest.getRole());
            Role roleEntity = roleOptional.orElseThrow(
                    () -> new RuntimeException("Role không tồn tại: " + adminUpdateUserRequest.getRole())
            );
            user.setRole(roleEntity);
        }
        return userRepository.save(user);
    }


}
