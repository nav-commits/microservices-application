package com.example.demo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;

@Entity
@Table(name = "Transactions")
public class Transactions {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Transactionid")
	private Long Transactionid;
	@Column(name = "userid")
	private String userid;
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
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm")
	@Column(name = "createdAt")
	private Date createdAt = new Date();
	
	
	public Transactions(Long Transactionid, String userid, Long productid, String imageurl, String productname,int quantity, int price, Date createdAt) {
		
		this.Transactionid = Transactionid;
		this.userid = userid;
		this.productid = productid;
		this.imageurl = imageurl;
		this.productname = productname;
		this.quantity = quantity;
		this.price = price;
		this.createdAt = createdAt;
	}
	
	
     public Transactions() {
		
		
	}
	

	
	public Long getTransactionid() {
		return Transactionid;
	}
	public void setTransactionid(Long Transactionid) {
		this.Transactionid = Transactionid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
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
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	




	
	
	
	

}
