package org.example.banner.service;

import org.example.banner.entity.Banner;
import org.example.banner.reponsitory.BannerReponsotory;
import org.example.banner.request.BannerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.PublicKey;
import java.util.List;

@Service
public class BannerService {
    @Autowired
    private BannerReponsotory bannerReponsotory;

    public Banner createBanner(BannerRequest bannerRequest) {
        Banner banner = new Banner();
        banner.setBannerNane(bannerRequest.getBannerNane());
        banner.setBannerImg(bannerRequest.getBannerImg());
        banner.setStartDate(bannerRequest.getStartDate());
        banner.setEndDate(bannerRequest.getEndDate());
        banner.setStatus(bannerRequest.isStatus());
        return bannerReponsotory.save(banner);

    }

    public void deleteBanner(Banner banner) {
        bannerReponsotory.delete(banner);
    }

    public List<Banner> getAllBanners() {
        return bannerReponsotory.findAll();
    }

    public Banner getBannerById(Integer bannerId) {
        Banner banner = bannerReponsotory.findById(bannerId).orElseThrow(() -> new RuntimeException("Không tồn tại "));
        return banner;
    }

    public Banner updateBannerById(Integer bannerId, BannerRequest bannerRequest) {
        Banner banner = getBannerById(bannerId);
        banner.setStartDate(bannerRequest.getStartDate());
        banner.setEndDate(bannerRequest.getEndDate());
        banner.setStatus(bannerRequest.isStatus());
        banner.setBannerNane(bannerRequest.getBannerNane());
        banner.setBannerImg(bannerRequest.getBannerImg());
        return bannerReponsotory.save(banner);
    }
}
