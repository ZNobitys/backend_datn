package org.example.booking.entity;

import org.example.booking.reponsitory.StatusReponsitory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DBStatus {

    @Bean
    CommandLineRunner init(StatusReponsitory statusReponsitory) {
        return args -> {
            if (statusReponsitory.findByStatusName("PENDING").isEmpty()) {
                statusReponsitory.save(new Status("STATUS_PENDING", "Người dùng vừa đặt, chờ duyệt"));
            }
            if (statusReponsitory.findByStatusName("CONFIRMED").isEmpty()) {
                statusReponsitory.save(new Status("STATUS_CONFIRMED", "Admin duyệt, đặt xe thành công"));
            }
            if (statusReponsitory.findByStatusName("CANCELLED").isEmpty()) {
                statusReponsitory.save(new Status("STATUS_CANCELLED", "Người dùng/ Admin huỷ"));
            }
            if (statusReponsitory.findByStatusName("REJECTED").isEmpty()) {
                statusReponsitory.save(new Status("STATUS_REJECTED", "Admin từ chối"));
            }
            if (statusReponsitory.findByStatusName("COMPLETED").isEmpty()) {
                statusReponsitory.save(new Status("STATUS_COMPLETED", "Đã hoàn thành"));
            }
        };
    }


}
