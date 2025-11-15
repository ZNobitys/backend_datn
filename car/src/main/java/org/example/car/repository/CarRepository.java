package org.example.car.repository;

import org.example.car.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface CarRepository extends JpaRepository<Car, Integer> {
    Optional<Car> findCarByNameCar(String carName);

    Optional<Car> findCarByCarId(Integer carId);

    List<Car> findCarByOwnerId(Integer ownerId);
}
