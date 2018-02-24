package com.dejavu.onlineshop.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Product {
    private @Id @GeneratedValue Long id;
    private String name;
    private String description;

    private Product() {}

    public Product(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
