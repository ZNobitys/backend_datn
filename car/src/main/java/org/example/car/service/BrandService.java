package org.example.car.service;

import org.example.car.entity.Brand;
import org.example.car.repository.BrandRepository;
import org.example.car.request.BrandRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class BrandService {
    @Autowired
    private BrandRepository brandRepository;

    public Brand createBrand(@RequestBody BrandRequest brandRequest) {
        Brand brand = new Brand();
        brand.setBrandName(brandRequest.getBrandName());
        brand.setBrandDescription(brandRequest.getBrandDescription());
        if (brandRepository.findBrandByBrandName(brandRequest.getBrandName()).isPresent()) {
            throw new RuntimeException("Hãng xe đã tồn tại");
        }
        return brandRepository.save(brand);
    }

    public List<Brand> findAllBrands() {
        return brandRepository.findAll();
    }

    public void deleteBrandById(Integer brandId) {
        if (!brandRepository.findById(brandId).isPresent()) { // ! để kiểm tra nếu KHÔNG tồn tại
            throw new RuntimeException("Hãng xe không tồn tại");
        }
        brandRepository.deleteById(brandId);
    }

    public Brand brandById(Integer brandId) {
        return brandRepository.findById(brandId).orElseThrow(() -> new RuntimeException("Hãng xe không tồn tại"));
    }

    public Brand updateBrandById(Integer brandId, BrandRequest brandRequest) {
        Brand brand = brandRepository.findById(brandId).orElseThrow(() -> new RuntimeException("Hãng xe không tồn tại"));
        brandRepository.findBrandByBrandName(brandRequest.getBrandName()).ifPresent(existing -> {
            if (existing.getBrandId() != brandId) {
                throw new RuntimeException("Hãng xe đã tồn tại");
            }
        });
        brand.setBrandName(brandRequest.getBrandName());
        brand.setBrandDescription(brandRequest.getBrandDescription());

        return brandRepository.save(brand);
    }

}
