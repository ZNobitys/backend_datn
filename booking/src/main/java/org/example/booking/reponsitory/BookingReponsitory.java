package org.example.booking.reponsitory;

import org.example.booking.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookingReponsitory extends JpaRepository<Booking,Integer> {
    Optional<Booking> findByBookingId(Integer bookingId);

}
