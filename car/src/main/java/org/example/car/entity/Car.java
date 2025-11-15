package org.example.car.entity;


import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "CAR")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "CAR_ID")
    private int carId;
    @Column(name = "NAMECAR")
    private String nameCar;
    @Column(name = "Color")
    private String color;
    @Column(name = "IMG")
    private String img;
    @Column(name = "YEAR")
    private String year;
    @Column(name = "LICENSE_PLATE")
    private String licensePlate;
    @Column(name = "Created_at")
    private Date createdAt;
    @Column(name = "OWNER_ID")
    private Integer ownerId;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "CAR_BRAND", joinColumns = @JoinColumn(name = "CAR_ID"), inverseJoinColumns = @JoinColumn(name = "BRAND_ID"))
    private List<Brand> brands;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "CAR_CATEGORY", joinColumns = @JoinColumn(name = "CAR_ID"), inverseJoinColumns = @JoinColumn(name = "CATEGORY_ID"))
    private List<Category> categories;


    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public String getNameCar() {
        return nameCar;
    }

    public void setNameCar(String nameCar) {
        this.nameCar = nameCar;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public List<Brand> getBrands() {
        return brands;
    }

    public void setBrands(List<Brand> brands) {
        this.brands = brands;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }
}
