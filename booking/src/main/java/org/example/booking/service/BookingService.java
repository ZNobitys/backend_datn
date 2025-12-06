package org.example.booking.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.transaction.Transactional;
import org.example.booking.entity.Booking;
import org.example.booking.entity.BookingDetail;
import org.example.booking.entity.Status;
import org.example.booking.repository.BookingRepository;
import org.example.booking.repository.StatusRepository;
import org.example.booking.request.BookingRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class BookingService {

    @Value("${jwt.secret}")
    private String SECRET_KEY;
    @Autowired
    private BookingRepository bookingReponsitory;
    @Autowired
    private StatusRepository statusReponsitory;
    @Autowired
    private RestTemplate restTemplate;

    public Booking createBooking(BookingRequest request, String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY.getBytes())
                .build()
                .parseClaimsJws(token.replace("Bearer ", ""))
                .getBody();
        Integer userIdFromToken = Integer.parseInt(claims.getSubject());
        List<String> roles = claims.get("roles", List.class);
        Integer bookingUserId = request.getUserId() != null ? request.getUserId() : userIdFromToken;

        if (!bookingUserId.equals(userIdFromToken) && !roles.contains("ROLE_ADMIN")) {
            throw new AccessDeniedException("Bạn không có quyền thêm booking cho người khác");
        }

        Booking booking = new Booking();
        booking.setBookingDate(LocalDateTime.parse(request.getBookingDate()));
        booking.setDetails(request.getDetails());
        booking.setUserId(bookingUserId);
        booking.setCarId(request.getCarId());
        booking.setBookingCreateDate(LocalDateTime.now());

        Status status = statusReponsitory.findById(1).orElseThrow(() -> new RuntimeException("Không tìm thấy status id = 1"));
        booking.setStatus(status);

        List<BookingDetail> detailsList = new ArrayList<>();

        for (int i = 0; i < request.getComponentsId().size(); i++) {
            BookingDetail detail = new BookingDetail();
            detail.setComponentId(request.getComponentsId().get(i));
            detail.setQuantity(request.getQuantityComponents().get(i));
            detail.setBooking(booking);

            String url = "http://localhost:8005/components/decreasequantity/" + request.getComponentsId().get(i) + "?quantity=" + request.getQuantityComponents().get(i);
            try {
                restTemplate.put(url, null);
            } catch (Exception e) {
                throw new RuntimeException("Đặt lịch thất bại cho component id = " + request.getComponentsId().get(i));
            }

            detailsList.add(detail);
        }

        booking.setBookingDetails(detailsList);

        return bookingReponsitory.save(booking);
    }


    @Transactional
    public Booking updateBooking(Integer bookingId, BookingRequest request, String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY.getBytes())
                .build()
                .parseClaimsJws(token.replace("Bearer ", ""))
                .getBody();
        Integer userIdFromToken = Integer.parseInt(claims.getSubject());
        List<String> roles = claims.get("roles", List.class);
        Integer bookingUserId = request.getUserId() != null ? request.getUserId() : userIdFromToken;

        if (!bookingUserId.equals(userIdFromToken) && !roles.contains("ROLE_ADMIN")) {
            throw new AccessDeniedException("Bạn không có quyền thêm booking cho người khác");
        }
        Booking booking = bookingReponsitory.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy booking id = " + bookingId));
        if (request.getBookingDate() != null) {
            booking.setBookingDate(LocalDateTime.parse(request.getBookingDate(), DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        }
        if (request.getDetails() != null) {
            booking.setDetails(request.getDetails());
        }
        if (request.getCarId() != null) {
            booking.setCarId(request.getCarId());
        }
        if (request.getStatusId() != null) {
            Status status = statusReponsitory.findById(request.getStatusId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy status id = " + request.getStatusId()));
            booking.setStatus(status);
        }

        if (request.getComponentsId() != null && !request.getComponentsId().isEmpty()) {
            List<BookingDetail> oldDetails = booking.getBookingDetails();
            for (BookingDetail oldDetail : oldDetails) {
                String url = "http://localhost:8005/components/decreasequantity/" + oldDetail.getComponentId()
                        + "?quantity=" + oldDetail.getQuantity();
                restTemplate.put(url, null);
            }
            oldDetails.clear();
            List<Integer> quantities = request.getQuantityComponents() != null
                    ? request.getQuantityComponents()
                    : Collections.nCopies(request.getComponentsId().size(), 1);

            for (int i = 0; i < request.getComponentsId().size(); i++) {
                BookingDetail detail = new BookingDetail();
                detail.setComponentId(request.getComponentsId().get(i));
                detail.setQuantity(quantities.get(i));
                detail.setBooking(booking);
                String url = "http://localhost:8005/components/decreasequantity/" + request.getComponentsId().get(i)
                        + "?quantity=" + quantities.get(i);
                restTemplate.put(url, null);

                oldDetails.add(detail);
            }
        }
        return bookingReponsitory.save(booking);
    }


    public List<Booking> findAllBookings(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(SECRET_KEY.getBytes()).build().parseClaimsJws(token.replace("Bearer ", "")).getBody();
        List<String> roles = claims.get("roles", List.class);

        if (!roles.contains("ROLE_ADMIN")) {
            throw new AccessDeniedException("Chỉ admin mới xem được tất cả xe");
        }
        return bookingReponsitory.findAll();
    }

    public Booking cancelBooking(Integer bookingId, String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY.getBytes())
                .build()
                .parseClaimsJws(token.replace("Bearer ", ""))
                .getBody();

        Integer userIdFromToken = Integer.parseInt(claims.getSubject());
        List<String> roles = claims.get("roles", List.class);
        Booking booking = bookingReponsitory.findByBookingId(bookingId)
                .orElseThrow(() -> new RuntimeException("Không tồn tại lịch hẹn"));
        if (!roles.contains("ADMIN") && booking.getUserId() != userIdFromToken) {
            throw new RuntimeException("Bạn không có quyền hủy lịch này");
        }
        Status cancelStatus = statusReponsitory.findById(3)
                .orElseThrow(() -> new RuntimeException("status không tồn tại"));
        booking.setStatus(cancelStatus);
        return bookingReponsitory.save(booking);
    }

    public List<Booking> getMyBookings(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY.getBytes())
                .build()
                .parseClaimsJws(token.replace("Bearer ", ""))
                .getBody();
        Integer userIdFromToken = Integer.parseInt(claims.getSubject());

        return bookingReponsitory.findByUserId(userIdFromToken);
    }


}
