package com.mycompany.product.model;

import java.util.Arrays;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String reference;

    private String description;
    private String nomPhoto;
   


    @OneToMany(mappedBy = "category")
    private List<Product> products;



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



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public String getNomPhoto() {
		return nomPhoto;
	}



	public void setNomPhoto(String nomPhoto) {
		this.nomPhoto = nomPhoto;
	}



	public List<Product> getProducts() {
		return products;
	}



	public void setProducts(List<Product> products) {
		this.products = products;
	}



	@Override
	public String toString() {
		return "Category [id=" + id + ", name=" + name + ", reference=" + reference + ", description=" + description
				+ ", nomPhoto=" + nomPhoto + ", products=" + products + "]";
	}

  

}
