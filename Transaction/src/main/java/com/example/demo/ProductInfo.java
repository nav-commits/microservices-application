package com.example.demo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;


@Entity
@Table(name = "productinfo")
public class ProductInfo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "productinfoid")
	private Long productinfoid;
	@Column(name = "productid")
	private Long productid;
	@Column(name = "imageurl")
	private String imageurl;
	@Column(name = "productname")
	private String productname;
	@Column(name = "quantity")
	private int quantity;
	@Column(name = "price")
	private int price;

	public ProductInfo( Long productid, String imageurl, String productname, int quantity,int price, Long productinfoid) {
		this.productinfoid = productinfoid;
		this.productid = productid;
		this.imageurl = imageurl;
		this.productname = productname;
		this.quantity = quantity;
		this.price = price;
	}
	
	
	public ProductInfo( ) {
		
	}
	
	
	
	public Long getProductinfoid() {
		return productinfoid;
	}
	public void setProductinfoid(Long productinfoid) {
		this.productinfoid = productinfoid;
	}
	public Long getProductid() {
		return productid;
	}
	public void setProductid(Long productid) {
		this.productid = productid;
	}
	public String getImageurl() {
		return imageurl;
	}
	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}
	public String getProductname() {
		return productname;
	}
	public void setProductname(String productname) {
		this.productname = productname;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	

	
	
	


}