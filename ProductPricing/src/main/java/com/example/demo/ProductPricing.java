package com.example.demo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "productpricing")
public class ProductPricing {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "productpricingid")
	private Long productpricingid;
	@Column(name = "productid")
	private Long productid;
	@Column(name = "price")
	private int price;


	public Long getProductpricingid() {
		return productpricingid;
	}
	public void setProductpricingid(Long productpricingid) {
		this.productpricingid = productpricingid;
	}
	public Long getProductid() {
		return productid;
	}
	public void setProductid(Long productid) {
		this.productid = productid;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}


}
