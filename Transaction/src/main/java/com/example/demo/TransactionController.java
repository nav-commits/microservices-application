package com.example.demo;



import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.bind.annotation.PostMapping;


import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

    @RestController
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public class TransactionController {

	@Autowired
	private RestTemplate restTemplate;
	

	@Autowired
	TransactionRepository transactionRepository;

	// get Transactions
    @HystrixCommand(fallbackMethod = "getFallbackTransaction",commandProperties = {
	        @HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE")
  })
	@PostMapping("/Transactions/{userid}/{productid}")
	 public ResponseEntity<?>  Transactions (@PathVariable String userid, @PathVariable Long productid) {

	       // step 1 call productpricing microservice
	   	String urlProductPricing = "http://Product-Pricing-Service/ProductPricing/" + productid;
	   	UsersProductsPricing usersProductsPricing = restTemplate.getForObject(urlProductPricing,UsersProductsPricing.class);
	       
	   	Transactions transaction = new Transactions();
			usersProductsPricing.getProductsPricing().stream().forEach(productpricing -> {
			
			//step 2 call productpricing productinfo
			ProductInfo productinfo = restTemplate.getForObject("http://Product-Info-Service/Product/"+ productid, ProductInfo.class);
			transaction.setUserid(userid);
			transaction.setProductid(productpricing.getProductid());
			transaction.setImageurl(productinfo.getImageurl());
			transaction.setProductname(productinfo.getProductname());
			transaction.setQuantity(productinfo.getQuantity());
			transaction.setPrice(productpricing.getPrice());
			});
			
			// step 3 save it into the transactions database
			transactionRepository.save(transaction);
			return ResponseEntity.ok(transaction);

	}

	// find transaction ById
	@GetMapping("/Transaction/{userid}")
    public List<Transactions> getTransaction(@PathVariable String userid) {
	return transactionRepository.findByuserid(userid);
}

	

//        //fallback method for if transaction fails
	    public ResponseEntity<?> getFallbackTransaction(@PathVariable("userid") String userid,@PathVariable("productid") Long productid) {
			
	    	Transactions transaction = new Transactions();
			transaction.setUserid("0");
			transaction.setProductid((long) 0);
			transaction.setImageurl("Image not found");
			transaction.setProductname("product name not found");
			transaction.setQuantity(0);
			transaction.setPrice(0);
			transaction.setCreatedAt(null);
			
			return ResponseEntity.ok(transaction);
	    	
	  }

}
