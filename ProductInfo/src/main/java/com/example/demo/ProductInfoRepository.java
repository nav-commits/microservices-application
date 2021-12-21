package com.example.demo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductInfoRepository extends JpaRepository<ProductInfo, Long> {




	List<ProductInfo> findByProductid(Optional<Long> productid);
	

	ProductInfo findByProductid(Long productid);

}
