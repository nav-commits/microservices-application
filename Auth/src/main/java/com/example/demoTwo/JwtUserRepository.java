package com.example.demoTwo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

public interface JwtUserRepository extends JpaRepository<JwtUsers, Long> {
	
	List<JwtUsers> findAll();
	JwtUsers findByUsername(String username);;

}
