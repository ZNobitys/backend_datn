package org.example.booking.entity;

import org.example.booking.repository.StatusRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DBStatus {

    @Bean
    CommandLineRunner initStatus(StatusRepository statusRepository) {
        return args -> {
            if (statusRepository.findByStatusName("PENDING").isEmpty()) {
                statusRepository.save(new Status("STATUS_PENDING", "Người dùng vừa đặt, chờ duyệt"));
            }
            if (statusRepository.findByStatusName("CONFIRMED").isEmpty()) {
                statusRepository.save(new Status("STATUS_CONFIRMED", "Admin duyệt, đặt xe thành công"));
            }
            if (statusRepository.findByStatusName("CANCELLED").isEmpty()) {
                statusRepository.save(new Status("STATUS_CANCELLED", "Người dùng/ Admin huỷ"));
            }
            if (statusRepository.findByStatusName("REJECTED").isEmpty()) {
                statusRepository.save(new Status("STATUS_REJECTED", "Admin từ chối"));
            }
            if (statusRepository.findByStatusName("COMPLETED").isEmpty()) {
                statusRepository.save(new Status("STATUS_COMPLETED", "Đã hoàn thành"));
            }
        };
    }


}
