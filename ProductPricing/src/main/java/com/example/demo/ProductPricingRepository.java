package com.example.demo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductPricingRepository extends JpaRepository<ProductPricing, Long>  {
  


List<ProductPricing> findByProductid(Long productid);

	
}
