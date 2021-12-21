package com.example.demo;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProductPricingController {

	@Autowired
	ProductPricingRepository productPricingRepository;

	// create a ProductPricing
	@PostMapping("/ProductPricing")
	public ProductPricing createProductPricing(@RequestBody ProductPricing productPricing) {
		productPricingRepository.save(productPricing);
		return productPricing;
	}

	// method returns all the prices of products
	public List<ProductPricing> getProductPricing() {
		return productPricingRepository.findAll();
	}

	// gets all product pricings
	@GetMapping("/ProductPricing")
	public UsersProductsPricing getProductPricings() {
		UsersProductsPricing usersProductsPricing = new UsersProductsPricing();
		usersProductsPricing.setProductsPricing(getProductPricing());
		return usersProductsPricing;

	}
	
	// method returns single product from array
	public List<ProductPricing> getProductAPricing(@PathVariable("productid") Long productid) {
		return productPricingRepository.findByProductid(productid);
	}

	// get a product pricing by productid
	@GetMapping("/ProductPricing/{productid}")
	public UsersProductsPricing getPricing(@PathVariable("productid") Long productid) {
		UsersProductsPricing usersProductsPricing = new UsersProductsPricing();
		usersProductsPricing.setProductsPricing(getProductAPricing(productid));
		return usersProductsPricing;

	}
	 
}


