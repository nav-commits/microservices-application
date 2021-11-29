package com.example.demo;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin(origins="http://localhost:3000")
@RestController
public class ProductsInfoController {

	
	@Autowired
	ProductInfoRepository productInfoRepository;
	
	@Autowired
	private RestTemplate restTemplate;
	
	// create a ProductPricing
	@PostMapping("/ProductInfo")
	public ProductInfo createProductPricing(@RequestBody ProductInfo productInfo) {
		productInfoRepository.save(productInfo);
		return productInfo;
	}

	// find a productinfo by productid
	@GetMapping("/Product/ProductInfo/{productid}")
	public ProductInfo getProductInfo(@PathVariable Long productid) {
		return productInfoRepository.findById(productid).orElse(null);

	}

	// get product pricing by productid
	@GetMapping("/ProductInfo/{userid}")
	public List<ProductInfo> getProductPrice(@PathVariable int userid) {
		// step 1 call the productpricing microservice
		String uriProductPricing = "http://localhost:8084/ProductPricing/" + userid;
		UsersProductsPricing usersProductsPricing = restTemplate.getForObject(uriProductPricing,
				UsersProductsPricing.class);

		// step 2 loop over usersProductPricing array and get the price
		return usersProductsPricing.getUsersProductsPricing().stream().map(productpricing -> {
			ProductInfo productinfo = restTemplate.getForObject(
					"http://localhost:8082/Product/ProductInfo/" + productpricing.getProductid(), ProductInfo.class);
			return productInfoRepository.save(new ProductInfo(productpricing.getProductid(), productinfo.getImageurl(),
					productinfo.getImageurl(), productinfo.getQuantity(), productpricing.getPrice()));
		}).collect(Collectors.toList());

	}
	
	 
	


}