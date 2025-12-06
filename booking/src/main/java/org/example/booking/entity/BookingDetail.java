package org.example.booking.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "BOOKING_DETAIL")
public class BookingDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int componentId;
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    @JsonBackReference
    private Booking booking;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getComponentId() {
        return componentId;
    }

    public void setComponentId(int componentId) {
        this.componentId = componentId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }
}
