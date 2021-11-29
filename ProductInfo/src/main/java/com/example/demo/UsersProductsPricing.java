package com.example.demo;

import java.util.Arrays;
import java.util.List;

public class UsersProductsPricing {

	private int userId;
	List<ProductPricing> usersProductsPricing;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public List<ProductPricing> getUsersProductsPricing() {
		return usersProductsPricing;
	}

	public void setUsersProductsPricing(List<ProductPricing> usersProductsPricing) {
		this.usersProductsPricing = usersProductsPricing;
	}

	public void initData(int userId) {
		this.setUserId(userId);
	}

}