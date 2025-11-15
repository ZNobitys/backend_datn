package org.example.user.request;
import jakarta.validation.constraints.Size;

import java.util.List;

public class CreateUserRequest {
    private String fullName;
    private String email;
    @Size(min = 6,  message = "Mật khẩu bao gồm ít nhất 6 ký tự", max = 50)
    private String password;
    private String role;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
