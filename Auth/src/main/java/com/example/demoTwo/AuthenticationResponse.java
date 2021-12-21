package com.example.demoTwo;

import java.io.Serializable;

public class AuthenticationResponse implements Serializable  {
	private String jwt;
	private String username;

	public AuthenticationResponse(String jwt, String username) {
		this.jwt = jwt;
		this.username = username;
	}

	public AuthenticationResponse() {
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public AuthenticationResponse(String jwt) {
		this.jwt = jwt;
	}

	public String getJwt() {
		return jwt;
	}

}
