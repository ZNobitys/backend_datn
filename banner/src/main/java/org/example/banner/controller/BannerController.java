package org.example.banner.controller;

import org.example.banner.entity.Banner;
import org.example.banner.request.BannerRequest;
import org.example.banner.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/banner")
public class BannerController {

    @Autowired
    private BannerService bannerService;

    @PostMapping("/create")
    public ResponseEntity<?> createBanner(@RequestBody BannerRequest bannerRequest) {
        bannerService.createBanner(bannerRequest);
        return ResponseEntity.ok(bannerRequest);
    }

    @GetMapping("/getall")
    public List<Banner> getBanners() {
        return bannerService.getAllBanners();
    }

    @GetMapping("/get/{bannerId}")
    Banner getBanner(@PathVariable Integer bannerId) {
        return bannerService.getBannerById(bannerId);
    }

    @PutMapping("/update/{bannerId}")
    Banner updateBanner(@RequestBody BannerRequest bannerRequest, @PathVariable Integer bannerId) {
        return bannerService.updateBannerById(bannerId, bannerRequest);

    }

}
