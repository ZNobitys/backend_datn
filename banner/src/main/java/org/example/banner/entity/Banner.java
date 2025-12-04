package org.example.banner.entity;


import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table (name = "BANNER")
public class Banner {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;
    @Column(name = "BANNER_NANE")
    private String bannerNane;
    @Column(name = "STATUS")
    private boolean status;
    @Column(name = "BANNER_IMG")
    private String bannerImg;
    @Column(name ="START_DATE")
    private Date startDate;
    @Column(name = "END_DATE")
    private Date endDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBannerNane() {
        return bannerNane;
    }

    public void setBannerNane(String bannerNane) {
        this.bannerNane = bannerNane;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getBannerImg() {
        return bannerImg;
    }

    public void setBannerImg(String bannerImg) {
        this.bannerImg = bannerImg;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
