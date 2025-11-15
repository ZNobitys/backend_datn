package org.example.car.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.example.car.entity.Car;
import org.example.car.request.CarRequest;
import org.example.car.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/car")
public class CarController {

    @Autowired
    private CarService carService;

    @PostMapping("/add")
    public Car addCar(@RequestBody CarRequest request, @RequestHeader("Authorization") String token) {
        return carService.createCar(request, token);
    }

    @GetMapping("/user/{userId}")
    public List<Car> getCarsByUser(@PathVariable Integer userId, @RequestHeader("Authorization") String token) {
        return carService.getCarsByUser(userId, token);
    }

    @GetMapping("/all")
    public List<Car> getAllCars(@RequestHeader("Authorization") String token) {
        return carService.getAllCars(token);
    }

    @PutMapping("/update/{carId}")
    public ResponseEntity<String> updateCar(@PathVariable Integer carId,
                                            @RequestBody CarRequest request,
                                            @RequestHeader("Authorization") String token) throws JsonProcessingException {
        Car updatedCar = carService.updateCar(carId, request, token);
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        return ResponseEntity.ok(mapper.writeValueAsString(updatedCar));
    }

    @DeleteMapping("/delete/{carId}")
    String  deleteCar(@PathVariable Integer carId, @RequestHeader("Authorization") String token) {
        carService.deleteCar(carId, token);
        return "Đã xóa thành công";
    }
}
