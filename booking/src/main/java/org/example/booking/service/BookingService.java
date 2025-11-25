package org.example.booking.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.example.booking.entity.Booking;
import org.example.booking.entity.Status;
import org.example.booking.reponsitory.BookingReponsitory;
import org.example.booking.reponsitory.StatusReponsitory;
import org.example.booking.request.BookingRequest;
import org.example.booking.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingService {

    @Value("${jwt.secret}")
    private String SECRET_KEY;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private BookingReponsitory bookingReponsitory;
    @Autowired
    private StatusReponsitory statusReponsitory;
    @Autowired
    private RestTemplate restTemplate;

    public Booking createBooking(BookingRequest bookingRequest, String token) {

        Claims claims = Jwts.parserBuilder().setSigningKey(SECRET_KEY.getBytes()).build().parseClaimsJws(token.replace("Bearer ", "")).getBody();
        Integer userIdFromToken = Integer.parseInt(claims.getSubject());
        List<String> roles = claims.get("roles", List.class);
        Integer bookingUserId = bookingRequest.getUserId() != null ? bookingRequest.getUserId() : userIdFromToken;

        if (!bookingUserId.equals(userIdFromToken) && !roles.contains("ROLE_ADMIN")) {
            throw new AccessDeniedException("Bạn không có quyền thêm xe cho người khác");
        }

        Booking booking = new Booking();
        booking.setBookingDate(bookingRequest.getBookingDate());
        booking.setDetails(bookingRequest.getDetails());
        booking.setUserId(bookingUserId);
        booking.setCarId(bookingRequest.getCarId());
        booking.setComponentsId(bookingRequest.getComponentsId());
        booking.setBookingCreateDate(LocalDateTime.now());
        Status status = statusReponsitory.findById(1)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy status id = 1"));
        booking.setStatus(status);
        int quantity = bookingRequest.getQuantityComponents() != null ? bookingRequest.getQuantityComponents() : 1;
        booking.setQuantityComponents(quantity);

        Booking savedBooking = bookingReponsitory.save(booking);
        String url = "http://localhost:8005/components/decreasequantity/"
                + bookingRequest.getComponentsId()
                + "?quantity=" + quantity;
        try{
            restTemplate.put(url, null);
        }
        catch (Exception e){
            bookingReponsitory.delete(savedBooking);
            throw new RuntimeException("Đặt lịch thất bại");
        }
        return booking;
    }

    public Booking updateBooking(Integer bookingId, BookingRequest bookingRequest, String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY.getBytes())
                .build()
                .parseClaimsJws(token.replace("Bearer ", ""))
                .getBody();
        Integer userIdFromToken = Integer.parseInt(claims.getSubject());
        List<String> roles = claims.get("roles", List.class);

        Booking booking = bookingReponsitory.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy booking id = " + bookingId));

        // Cập nhật thông tin nếu có
        if (bookingRequest.getBookingDate() != null) booking.setBookingDate(bookingRequest.getBookingDate());
        if (bookingRequest.getDetails() != null) booking.setDetails(bookingRequest.getDetails());
        if (bookingRequest.getCarId() != null) booking.setCarId(bookingRequest.getCarId());
        if (bookingRequest.getComponentsId() != null) booking.setComponentsId(bookingRequest.getComponentsId());
        if (bookingRequest.getStatusId() != null) booking.setStatus(booking.getStatus());

        int oldQuantity = booking.getQuantityComponents();
        int newQuantity = bookingRequest.getQuantityComponents() != null ? bookingRequest.getQuantityComponents() : oldQuantity;
        if (newQuantity < 0) {
            throw new RuntimeException("Số lượng component không thể âm");
        }
        booking.setQuantityComponents(newQuantity);

        Booking savedBooking = bookingReponsitory.save(booking);

        if (newQuantity != oldQuantity) {
            int diff = newQuantity - oldQuantity;
            String url;

            if (diff > 0) {
                url = "http://localhost:8005/components/decreasequantity/"
                        + booking.getComponentsId() + "?quantity=" + diff;
            } else {
                url = "http://localhost:8005/components/increasequantity/"
                        + booking.getComponentsId() + "?quantity=" + (-diff);
            }

            try {
                restTemplate.put(url, null);
            } catch (Exception e) {
                booking.setQuantityComponents(oldQuantity);
                bookingReponsitory.save(booking);
                throw new RuntimeException("Cập nhật booking thất bại");
            }
        }

        return booking;
    }

    public List<Booking> findAllBookings() {
        return bookingReponsitory.findAll();
    }



}
