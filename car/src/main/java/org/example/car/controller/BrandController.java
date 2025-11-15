package org.example.car.controller;


import org.example.car.entity.Brand;
import org.example.car.repository.BrandRepository;
import org.example.car.request.BrandRequest;
import org.example.car.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/brand")
public class BrandController {
    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private BrandService brandService;

    @PostMapping("/create")
    public ResponseEntity<?> createBrand(@RequestBody BrandRequest brandRequest) {
        Brand brand = brandService.createBrand(brandRequest);
        return ResponseEntity.ok().body(brand);
    }

    @GetMapping("/get")
    public List<Brand> findAllBrands() {
        return brandRepository.findAll();
    }

    @GetMapping("/get/{brandId}")
    public Brand getBrandById(@PathVariable Integer brandId) {
        return brandService.brandById(brandId);
    }

    @PutMapping("/update/{brandId}")
    Brand updateBrand(@PathVariable Integer brandId, @RequestBody BrandRequest brandRequest) {
        return brandService.updateBrandById(brandId, brandRequest);
    }

    @DeleteMapping("/delete/{brandId}")
    String deleteBrand(@PathVariable Integer brandId) {
        brandService.deleteBrandById(brandId);
        return ("Đã xóa thành công");
    }

}
