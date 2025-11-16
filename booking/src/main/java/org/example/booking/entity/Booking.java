package org.example.booking.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "BOOKING")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "BOOKING_ID")
    private int bookingId;
    @Column(name = "BOOKING_DATE")
    private String bookingDate;
    @Column(name = "DETAILS")
    private String details;
    @Column(name = "BOOKING_CREATEDATE")
    private LocalDateTime bookingCreateDate;
    @Column(name = "USER_ID")
    private int userId;
    @Column(name = "CAR_ID")
    private int carId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(
            name = "STATUS_BOOKING",
            joinColumns = @JoinColumn(name = "BOOKING_ID"),
            inverseJoinColumns = @JoinColumn(name = "STATUS_ID")
    )
    private Booking status;

    public Booking getStatus() {
        return status;
    }

    public void setStatus(Booking status) {
        this.status = status;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public LocalDateTime getBookingCreateDate() {
        return bookingCreateDate;
    }

    public void setBookingCreateDate(LocalDateTime bookingCreateDate) {
        this.bookingCreateDate = bookingCreateDate;
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
}
