package org.example.components.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "COMPONENTS")
public class Components {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "COMPONENTS_ID")
    private Integer componentsId;
    @Column(name = "COMPONENTS_NAME")
    private String componentsName;
    @Column(name = "COMPONENTS_QUANTITY")
    private Integer quantity;
    @Column(name = "PRICE")
    private Integer price;
    @Column(name = "IMG")
    private String img;
    @Column(name = "COMPONENTS_DESCRIPTION")
    private String componentsDescription;

    public String getComponentsName() {
        return componentsName;
    }

    public void setComponentsName(String componentsName) {
        this.componentsName = componentsName;
    }

    public Integer getComponentsId() {
        return componentsId;
    }

    public void setComponentsId(Integer componentsId) {
        this.componentsId = componentsId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getComponentsDescription() {
        return componentsDescription;
    }

    public void setComponentsDescription(String componentsDescription) {
        this.componentsDescription = componentsDescription;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
