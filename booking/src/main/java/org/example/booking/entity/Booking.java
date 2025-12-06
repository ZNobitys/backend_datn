package org.example.booking.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "BOOKING")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookingId;

    private LocalDateTime bookingDate;
    private String details;
    private LocalDateTime bookingCreateDate;
    private int userId;
    private int carId;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<BookingDetail> bookingDetails;

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public LocalDateTime getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDateTime bookingDate) {
        this.bookingDate = bookingDate;
    }

    public LocalDateTime getBookingCreateDate() {
        return bookingCreateDate;
    }

    public void setBookingCreateDate(LocalDateTime bookingCreateDate) {
        this.bookingCreateDate = bookingCreateDate;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<BookingDetail> getBookingDetails() {
        return bookingDetails;
    }

    public void setBookingDetails(List<BookingDetail> bookingDetails) {
        this.bookingDetails = bookingDetails;
    }
}
