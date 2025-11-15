package org.example.car.service;

import org.example.car.entity.Brand;
import org.example.car.entity.Car;
import org.example.car.entity.Category;
import org.example.car.repository.BrandRepository;
import org.example.car.repository.CarRepository;
import org.example.car.repository.CategoryRepository;
import org.example.car.request.CarRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private final String SECRET_KEY = "MySuperSecretKeyThatIsAtLeast32Chars!";

    // Add car
    public Car createCar(CarRequest carRequest, String token) {

        Claims claims = Jwts.parserBuilder().setSigningKey(SECRET_KEY.getBytes()).build().parseClaimsJws(token.replace("Bearer ", "")).getBody();
        Integer userIdFromToken = Integer.parseInt(claims.getSubject());
        List<String> roles = claims.get("roles", List.class);

        // Nếu ownerId được truyền trong request, phải là admin mới được thêm cho người khác
        Integer ownerId = carRequest.getOwnerId() != null ? carRequest.getOwnerId() : userIdFromToken;

        if (!ownerId.equals(userIdFromToken) && !roles.contains("ROLE_ADMIN")) {
            throw new AccessDeniedException("Bạn không có quyền thêm xe cho người khác");
        }
        Car car = new Car();
        car.setNameCar(carRequest.getNameCar());
        car.setColor(carRequest.getColor());
        car.setLicensePlate(carRequest.getLicensePlate());
        car.setImg(carRequest.getImg());
        car.setYear(carRequest.getYear());
        car.setOwnerId(ownerId);
        car.setCreatedAt(new Date());

        if (carRequest.getCategory() != null) {
            List<Category> categories = carRequest.getCategory().stream().map(id -> categoryRepository.findCategoryByCategoryId(Integer.parseInt(id)).orElseThrow(() -> new RuntimeException("Loại xe không tồn tại"))).toList();
            car.setCategories(categories);
        }

        if (carRequest.getBrand() != null) {
            List<Brand> brands = carRequest.getBrand().stream().map(id -> brandRepository.findBrandByBrandId(Integer.parseInt(id)).orElseThrow(() -> new RuntimeException("Hãng xe không tồn tại"))).toList();
            car.setBrands(brands);
        }

        return carRepository.save(car);
    }

    public List<Car> getCarsByUser(Integer userId, String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(SECRET_KEY.getBytes()).build().parseClaimsJws(token.replace("Bearer ", "")).getBody();
        Integer userIdFromToken = Integer.parseInt(claims.getSubject());
        List<String> roles = claims.get("roles", List.class);

        if (!userId.equals(userIdFromToken) && !roles.contains("ROLE_ADMIN")) {
            throw new AccessDeniedException("Bạn không có quyền xem xe của người khác");
        }

        return carRepository.findCarByOwnerId(userId);
    }

    public List<Car> getAllCars(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(SECRET_KEY.getBytes()).build().parseClaimsJws(token.replace("Bearer ", "")).getBody();
        List<String> roles = claims.get("roles", List.class);

        if (!roles.contains("ROLE_ADMIN")) {
            throw new AccessDeniedException("Chỉ admin mới xem được tất cả xe");
        }

        return carRepository.findAll();
    }

    public Car updateCar(Integer carId, CarRequest carRequest, String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(SECRET_KEY.getBytes()).build().parseClaimsJws(token.replace("Bearer ", "")).getBody();

        Integer userIdFromToken = Integer.parseInt(claims.getSubject());
        List<String> roles = claims.get("roles", List.class);

        // Lấy xe từ DB
        Car car = carRepository.findById(carId).orElseThrow(() -> new RuntimeException("Xe không tồn tại"));

        if (!roles.contains("ROLE_ADMIN") && (car.getOwnerId() == null || !car.getOwnerId().equals(userIdFromToken))) {
            throw new AccessDeniedException("Bạn không có quyền xóa xe này");
        }

        // Update các trường cơ bản
        if (carRequest.getNameCar() != null) car.setNameCar(carRequest.getNameCar());
        if (carRequest.getColor() != null) car.setColor(carRequest.getColor());
        if (carRequest.getYear() != null) car.setYear(carRequest.getYear());
        if (carRequest.getLicensePlate() != null) car.setLicensePlate(carRequest.getLicensePlate());
        if (carRequest.getImg() != null) car.setImg(carRequest.getImg()); // kiểu String
        if (carRequest.getCreatedAt() != null) car.setCreatedAt(carRequest.getCreatedAt());

        if (carRequest.getBrand() != null && !carRequest.getBrand().isEmpty()) {
            List<Brand> brands = new ArrayList<>(carRequest.getBrand().stream().map(id -> brandRepository.findBrandByBrandId(Integer.parseInt(id)).orElseThrow(() -> new RuntimeException("Hãng xe không tồn tại"))).toList());
            car.setBrands(brands);
        }

        if (carRequest.getCategory() != null && !carRequest.getCategory().isEmpty()) {
            List<Category> categories = new ArrayList<>(carRequest.getCategory().stream().map(id -> categoryRepository.findCategoryByCategoryId(Integer.parseInt(id)).orElseThrow(() -> new RuntimeException("Loại xe không tồn tại"))).toList());
            car.setCategories(categories);
        }

        return carRepository.save(car);
    }

    public void deleteCar(Integer carId, String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY.getBytes())
                .build()
                .parseClaimsJws(token.replace("Bearer ", ""))
                .getBody();
        Integer userIdFromToken = Integer.parseInt(claims.getSubject());
        List<String> roles = claims.get("roles", List.class);


        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new RuntimeException("Xe không tồn tại"));

        if (!roles.contains("ROLE_ADMIN") && (car.getOwnerId() == null || !car.getOwnerId().equals(userIdFromToken))) {
            throw new AccessDeniedException("Bạn không có quyền xóa xe này");
        }

        carRepository.delete(car);
    }
}
