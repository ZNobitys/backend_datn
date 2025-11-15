package org.example.user.entity;

import org.example.user.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DBRole {

    @Bean
    CommandLineRunner initRoles(RoleRepository roleReponsitory) {
        return args -> {
            if (roleReponsitory.findByRoleName("ROLE_ADMIN").isEmpty()) {
                roleReponsitory.save(new Role("ROLE_ADMIN", "Quản lý"));
            }

            if (roleReponsitory.findByRoleName("ROLE_USER").isEmpty()) {
                roleReponsitory.save(new Role("ROLE_USER", "Người dùng hệ thống"));
            }

            if (roleReponsitory.findByRoleName("ROLE_EMPLOYEE").isEmpty()) {
                roleReponsitory.save(new Role("ROLE_EMPLOYEE", "Nhân viên"));
            }
        };
    }
}
