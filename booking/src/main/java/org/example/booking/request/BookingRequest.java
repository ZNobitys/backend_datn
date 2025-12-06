package org.example.booking.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.example.booking.entity.Status;

import java.time.LocalDateTime;
import java.util.List;

public class BookingRequest {
    private String bookingDate;
    private String details;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime bookingCreateDate;
    private Integer userId;
    private Integer carId;
    private Integer statusId;
    private List<Integer> quantityComponents;
    private List<Integer> componentsId;

    public List<Integer> getQuantityComponents() {
        return quantityComponents;
    }

    public void setQuantityComponents(List<Integer> quantityComponents) {
        this.quantityComponents = quantityComponents;
    }

    public List<Integer> getComponentsId() {
        return componentsId;
    }

    public void setComponentsId(List<Integer> componentsId) {
        this.componentsId = componentsId;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
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
