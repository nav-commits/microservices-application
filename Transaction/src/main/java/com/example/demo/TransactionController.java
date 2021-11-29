package com.example.demo;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.bind.annotation.PostMapping;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class TransactionController {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	TransactionRepository transactionRepository;

	// get Transactions
	@PostMapping("/Transactions/{userid}")
	public List<Transactions> Transactions(@PathVariable Long userid) {

		// step 1 get product pricing
		String uriProductPricing = "http://Product-Pricing-Service/ProductPricing/" + userid;
		UsersProductsPricing usersProductsPricing = restTemplate.getForObject(uriProductPricing,
				UsersProductsPricing.class);

		// step 2 get pricing and product for user
		return usersProductsPricing.getUsersProductsPricing().stream().map(productpricing -> {
			ProductInfo productinfo = restTemplate.getForObject(
					"http://Product-Info-Service/Product/ProductInfo/" + productpricing.getProductid(),
					ProductInfo.class);
			return transactionRepository.save(new Transactions(productinfo.getImageurl(), productinfo.getProductname(),
					productinfo.getQuantity(), productinfo.getProductid(), productpricing.getPrice()));
		}).collect(Collectors.toList());

	}

	// find transaction ById
	@GetMapping("/Transaction/{userid}")
	public Optional<com.example.demo.Transactions> getProductInfo(@PathVariable Long userid) {
		return transactionRepository.findById(userid);

	}
	
	// find All
		@GetMapping("/Transactions")
		public List<Transactions> getAllTransactions() {
			return transactionRepository.findAll();

		}
	

	// Get Transactions
	@GetMapping("/Transactions/{userid}")
	@HystrixCommand(fallbackMethod = "getFallbackTransaction")
	public List<Transactions> getTransactions(@PathVariable Long userid) {

		// step 1 get product pricing
		String uriProductPricing = "http://Product-Pricing-Service/ProductPricing/" + userid;
		UsersProductsPricing usersProductsPricing = restTemplate.getForObject(uriProductPricing,
				UsersProductsPricing.class);

		// step 2 get pricing and product for user
		return usersProductsPricing.getUsersProductsPricing().stream().map(productpricing -> {
			ProductInfo productinfo = restTemplate.getForObject(
					"http://Product-Info-Service/Product/ProductInfo/" + productpricing.getProductid(),
					ProductInfo.class);
			return new Transactions(productinfo.getImageurl(), productinfo.getProductname(), productinfo.getQuantity(),
					productinfo.getProductid(), productpricing.getPrice());
		}).collect(Collectors.toList());

	}

	// fallback method for Transactions
	public List<Transactions> getFallbackTransaction(@PathVariable Long userid) {
		return Arrays.asList(new Transactions("no product images", "productname", 0, (long) 0, 0));
	}

}
