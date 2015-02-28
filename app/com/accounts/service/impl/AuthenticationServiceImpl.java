package com.accounts.service.impl;

import models.User;
import models.account.utils.AppException;

import com.accounts.service.AuthenticationService;

/**
 * @Author Pramod Email:sendpramod@gmail.com
 */
public class AuthenticationServiceImpl implements AuthenticationService {

	@Override
	public User authenticateUser(String email, String password)
			throws AppException {

		User user = null;
		user = User.authenticate(email, password);
		
		return user;
	}

}
