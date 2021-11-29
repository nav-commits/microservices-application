package com.example.demo;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class ProductInfo {
	
	@Id
	private Long productid;
	private String imageurl;
	private String productname;
	private int quantity;
	private int price;
	
	public ProductInfo(Long productid, String imageurl, String productname, int quantity, int price) {
		this.productid = productid;
		this.imageurl = imageurl;
		this.productname = productname;
		this.quantity = quantity;
		this.price = price;
	}
	
	public ProductInfo() {
		
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