package org.example.booking.request;

import java.time.LocalDateTime;

public class BookingRequest {
    private int bookingId;
    private String bookingDate;
    private String details;
    private LocalDateTime bookingCreateDate;
    private Integer userId;
    private Integer carId;
    private  Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }
}
