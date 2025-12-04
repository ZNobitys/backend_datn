package org.example.banner.request;

import java.util.Date;

public class BannerRequest {
    private String bannerNane;
    private boolean status;
    private String bannerImg;
    private Date startDate;
    private Date endDate;

    public String getBannerNane() {
        return bannerNane;
    }

    public void setBannerNane(String bannerNane) {
        this.bannerNane = bannerNane;
    }

    public String getBannerImg() {
        return bannerImg;
    }

    public void setBannerImg(String bannerImg) {
        this.bannerImg = bannerImg;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
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
