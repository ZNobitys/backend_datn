package org.example.banner.reponsitory;

import org.example.banner.entity.Banner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BannerReponsotory extends JpaRepository<Banner, Integer> {
    Optional<Banner> findById(int id);
}
