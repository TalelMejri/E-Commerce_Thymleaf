package com.mycompany.product.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String reference;
    private double price;
    private int quantityInStock;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "photo")
    private String photo;

    // Constructeur par défaut
    public Product() {
    }

    // Constructeur avec paramètres
    public Product(String name, String reference, double price, int quantityInStock, Category category, String photo) {
        this.name = name;
        this.reference = reference;
        this.price = price;
        this.quantityInStock = quantityInStock;
        this.category = category;
        this.photo = photo;
    }

    // Getters et setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(int quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    // Méthode toString
    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", reference='" + reference + '\'' +
                ", price=" + price +
                ", quantityInStock=" + quantityInStock +
                ", category=" + category +
                ", photo='" + photo + '\'' +
                '}';
    }
}
