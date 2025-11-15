package org.example.car.repository;

import org.example.car.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BrandRepository extends JpaRepository<Brand, Integer> {
    Optional<Brand> findBrandByBrandName(String brandName);

    Optional<Brand> findBrandByBrandId(Integer brandId);

}
