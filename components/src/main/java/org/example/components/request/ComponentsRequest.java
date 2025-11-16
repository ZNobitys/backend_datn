package org.example.components.request;

import jakarta.persistence.Column;

public class ComponentsRequest {
    private String componentsName;
    private Integer quantity;
    private Integer price;
    private String componentsDescription;
    private String img;

    public String getComponentsName() {
        return componentsName;
    }

    public void setComponentsName(String componentsName) {
        this.componentsName = componentsName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getComponentsDescription() {
        return componentsDescription;
    }

    public void setComponentsDescription(String componentsDescription) {
        this.componentsDescription = componentsDescription;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
