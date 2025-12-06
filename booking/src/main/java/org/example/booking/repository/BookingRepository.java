package org.example.booking.repository;

import org.example.booking.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking,Integer> {
    Optional<Booking> findByBookingId(Integer bookingId);
    List<Booking> findByUserId(Integer userId);

}
