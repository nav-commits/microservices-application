package com.example.demo;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;



@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
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


	// get product pricing by productid
	 @RequestMapping(value = {"/ProductInfo/{productid}"})
      @HystrixCommand(fallbackMethod = "getFallbackProduct",commandProperties = {
    	        @HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE")
      })
	 public List<ProductInfo> getProduct(@PathVariable Long productid) {
		 
		 
		String urlProductPricing = "http://Product-Pricing-Service/ProductPricing/" + productid;
		UsersProductsPricing usersProductsPricing = restTemplate.getForObject(urlProductPricing,
				UsersProductsPricing.class);

		return usersProductsPricing.getProductsPricing().stream().map(productpricing -> {

			ProductInfo productinfo = restTemplate
					.getForObject("http://Product-Info-Service/Product/" + productpricing.getProductid(), ProductInfo.class);

			ProductInfo productInfoObject = new ProductInfo();
			productInfoObject.setProductinfoid(productinfo.getProductinfoid());
			productInfoObject.setProductid(productpricing.getProductid());
			productInfoObject.setImageurl(productinfo.getImageurl());
			productInfoObject.setProductname(productinfo.getProductname());
			productInfoObject.setQuantity(productinfo.getQuantity());
			productInfoObject.setPrice(productpricing.getPrice());

			return productInfoObject;
		}).collect(Collectors.toList());

	}
	
	// get All Products
	@GetMapping("/ProductInfo")
    @HystrixCommand(fallbackMethod = "getFallbackProductInfo",commandProperties = {
	        @HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE")
  })
	public List<ProductInfo> getProducts() {
		String uriProductPricing = "http://Product-Pricing-Service/ProductPricing";
		UsersProductsPricing usersProductsPricings = restTemplate.getForObject(uriProductPricing,
				UsersProductsPricing.class);

		return usersProductsPricings.getProductsPricing().stream().map(productpricing -> {

			ProductInfo productinfo = restTemplate
					.getForObject("http://Product-Info-Service/Product/" + productpricing.getProductid(), ProductInfo.class);

			ProductInfo productInfoObject = new ProductInfo();
			productInfoObject.setProductinfoid(productinfo.getProductinfoid());
			productInfoObject.setProductid(productpricing.getProductid());
			productInfoObject.setImageurl(productinfo.getImageurl());
			productInfoObject.setProductname(productinfo.getProductname());
			productInfoObject.setQuantity(productinfo.getQuantity());
			productInfoObject.setPrice(productpricing.getPrice());

			return productInfoObject;
		}).collect(Collectors.toList());

	}
	
	
	
	// fallback method for a single product if productpricing microservice is down
	public List<ProductInfo> getFallbackProduct(@PathVariable Long productid) {
	return java.util.Arrays.asList(new ProductInfo((long)0, "no image", "no productname", 0, 0, (long)0));   
	  	  
	}
	
	// fallback method for all products if productpricing microservice is down
	public List<ProductInfo> getFallbackProductInfo() {
		return java.util.Arrays.asList(new ProductInfo((long)0, "no image", "no productname", 0, 0, (long)0));   
		  	  
		}
	
	 

	// find a product by productid
	   @GetMapping("/Product/{productid}")
	   public ProductInfo getSingleProduct(@PathVariable  Long productid) {
		   return productInfoRepository.findByProductid(productid);

	}


}
