package com.example.demo;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductPricingController {
	
	@Autowired
	ProductPricingRepository productPricingRepository;
	   
	// create a ProductPricing
	@PostMapping("/ProductPricing")
	public ProductPricing createProductPricing(@RequestBody ProductPricing productPricing) {
		productPricingRepository.save(productPricing);
		return productPricing;
	}
	
	// get a ProductPricing
	@GetMapping("/ProductPricing/{userId}")
	public UsersProductsPricing getProductPricings(@PathVariable("userId") int userId) {
		UsersProductsPricing usersProductsPricing = new UsersProductsPricing();
		usersProductsPricing.setUsersProductsPricing(getProductPricing());
		usersProductsPricing.initData(userId);
		return usersProductsPricing;
	
		
	}
		
	// get all ProductPricings
	@GetMapping("/ProductPricing")
	public List<ProductPricing> getProductPricing() {
	 return (List<ProductPricing>) productPricingRepository.findAll();
	}
	 
}
