package com.example.demoTwo;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ch.qos.logback.core.boolex.Matcher;

import java.util.regex.Pattern;

import javax.annotation.security.RolesAllowed;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class HomeResource {

	@Autowired
	JwtUserRepository userRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	private CustomUserDetailsService customUserdetailsservice;
	
	@Autowired
	private JwtUserUtils jwtTokenUtil;
	
	
    
	
	   @GetMapping("user")
	    public String helloUser() {
	        return "Hello User";
	    }

	
   
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@RequestBody JwtUsers jwtusers) {

		// to check all values are empty

		// if check, if the username is already used
		if (userRepository.findByUsername(jwtusers.getUsername()) != null) {
			return ResponseEntity.badRequest().body("username already in use");
		}

		// if username is empty
		if (jwtusers.getUsername() == "") {
			return ResponseEntity.badRequest().body("username empty" + jwtusers.getUsername());

		}

		// if contact is empty
		if (jwtusers.getContact() == "") {
			return ResponseEntity.badRequest().body("Contact information empty" + jwtusers.getContact());

		}

		// if password is empty
		if (jwtusers.getPassword() == "") {
			return ResponseEntity.badRequest().body("password is empty!" + jwtusers.getPassword());

		}

		// if address is empty
		if (jwtusers.getAddress() == "") {
			return ResponseEntity.badRequest().body("address is empty!" + jwtusers.getAddress());

		}

		// Creating user's account
		JwtUsers jwtUsers = new JwtUsers();

		// Regex for contact
		Pattern pattern = Pattern.compile("\\d{10}");
		java.util.regex.Matcher matcher = pattern.matcher(jwtusers.getContact());

		// Regex for password
		Pattern passwordPattern = Pattern
				.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$");
		java.util.regex.Matcher matchPassword = passwordPattern.matcher(jwtusers.getPassword());

		// Regex for username
		Pattern usernamePattern = Pattern.compile("^[a-z0-9_-]{5,15}$");
		java.util.regex.Matcher matchUsername = usernamePattern.matcher(jwtusers.getUsername());
		
		// Regex for address
		Pattern addressPattern = Pattern.compile("\\d+\\s[\\d\\w]+\\s(.*)");
		java.util.regex.Matcher matchAddress = addressPattern.matcher(jwtusers.getAddress());
		
		

		// if check to match contact regex
		if (matcher.matches()) {
			jwtUsers.setContact(jwtusers.getContact());
		} else {
			return ResponseEntity.badRequest().body("Must have 10 digits in Phone Number");
		}
		

		// if check password regex
		if (matchPassword.matches()) {
			jwtUsers.setPassword(passwordEncoder.encode(jwtusers.getPassword()));

		} else {
			return ResponseEntity.badRequest().body(
					"incorrect Password, "
					+ "must contain at least one digit [0-9]."
					+ "Password must contain at least one lowercase Latin character [a-z]."
					+ "Password must contain at least one uppercase Latin character [A-Z]."
					+ "Password must contain at least one special character like ! @ # & ( )."
					+ "Password must contain a length of at least 8 characters and a maximum of 20 characters.");
		}

		// if check username regex
		if (matchUsername.matches()) {
			jwtUsers.setUsername(jwtusers.getUsername());

		} else {
			return ResponseEntity.badRequest().body("incorrect username, accepts 5 to 15 characters with any lower case character, digit or special symbol “_-” only.");
		}
		
		
		// if check address regex
		if (matchAddress.matches()) {
			jwtUsers.setAddress(jwtusers.getAddress());

		} else {
			return ResponseEntity.badRequest().body("incorrect address must only start with numbers only");
		}
		

		// saves the user created into the databsase
		userRepository.save(jwtUsers);
		return ResponseEntity.ok(jwtUsers);

	}


	@PostMapping("/signin")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest){
		
		
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUsername(), authenticationRequest.getPassword()));
		

		final UserDetails userDetails = customUserdetailsservice
				.loadUserByUsername(authenticationRequest.getUsername());

		final String jwt = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new AuthenticationResponse(jwt,authenticationRequest.getUsername()));
	}
    

	
}
