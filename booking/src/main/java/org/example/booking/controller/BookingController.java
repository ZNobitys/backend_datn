package org.example.booking.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.example.booking.entity.Booking;
import org.example.booking.request.BookingRequest;
import org.example.booking.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.parser.Entity;

@RestController
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("/create")
    public Booking createBooking (@RequestBody BookingRequest bookingRequest, @RequestHeader("Authorization") String token){
        return bookingService.createBooking(bookingRequest, token);
    }

    @PutMapping("/update/{bookingId}")
    public ResponseEntity<String> updateBooking(
            @RequestBody BookingRequest bookingRequest,
            @PathVariable Integer bookingId,
            @RequestHeader("Authorization") String token) throws JsonProcessingException {

        Booking updateBooking = bookingService.updateBooking(bookingId, bookingRequest, token);
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        return ResponseEntity.ok(objectMapper.writeValueAsString(updateBooking));
    }

}



