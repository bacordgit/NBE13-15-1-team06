package com.kdt.nbe13151team06;

import jakarta.persistence.*;

@Entity
@Table(name="menus")
public class menus {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    int id;

    @Column(name="name",nullable =false)
    String name;

    @Column(name="price",nullable=false)
    int price;

    @Column(name="description",nullable=false)
    String description;

    @Column(name="available",nullable=false)
    boolean available;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="category_id",nullable=false)
    Categories categories;

    public menus(){

    }

    public menus(Categories categories,String name, int price, String description, boolean available) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.available = available;
        this.categories = categories;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public boolean isAvailable() {
        return available;
    }

    public Categories getCategory() {
        return Categories;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(Categories category) {
        Categories = category;
    }
}
