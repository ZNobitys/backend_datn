package org.example.booking.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.example.booking.entity.Booking;
import org.example.booking.request.BookingRequest;
import org.example.booking.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("/create")
    public Booking createBooking(@RequestBody BookingRequest request, @RequestHeader("Authorization") String token) {
        return bookingService.createBooking(request, token);
    }

    @PutMapping("/update/{bookingId}")
    public ResponseEntity<String> updateBooking(
            @PathVariable Integer bookingId,
            @RequestBody BookingRequest bookingRequest,
            @RequestHeader("Authorization") String token) throws Exception {
        Booking booking = bookingService.updateBooking(bookingId, bookingRequest, token);
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        String json = objectMapper.writeValueAsString(booking);
        return ResponseEntity.ok(json);
    }


    @GetMapping("/get")
    public List<Booking> getBookings(@RequestHeader("Authorization") String token) {
        return bookingService.findAllBookings(token);
    }

    @PutMapping("/cancel/{id}")
    public ResponseEntity<Booking> cancelBooking(
            @PathVariable Integer id,
            @RequestHeader("Authorization") String token) {

        Booking canceledBooking = bookingService.cancelBooking(id, token);
        return ResponseEntity.ok(canceledBooking);
    }

    @GetMapping("/myBooking")
    public ResponseEntity<List<Booking>> getMyBookings(
            @RequestHeader("Authorization") String token) {
        List<Booking> bookings = bookingService.getMyBookings(token);
        if (bookings.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(bookings);
    }

}



