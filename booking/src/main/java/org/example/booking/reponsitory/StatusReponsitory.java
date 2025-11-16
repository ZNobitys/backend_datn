package org.example.booking.reponsitory;

import org.example.booking.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StatusReponsitory extends JpaRepository<Status, Integer> {
    Optional<Status> findByStatusName(String statusName);
}
