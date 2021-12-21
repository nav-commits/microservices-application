package com.example.demoTwo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	JwtUserRepository jwtUserRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		JwtUsers jwtUsers = jwtUserRepository.findByUsername(username);
		
		if (jwtUsers == null) {
			throw new UsernameNotFoundException("username not found" + username);
		}
		
		return new User(jwtUsers.getUsername(), jwtUsers.getPassword(), new ArrayList<>());
	}


}
