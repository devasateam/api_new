package com.accounts.service;

import models.User;
import models.account.utils.AppException;

public interface AuthenticationService {
	static String SESSION_USER_ATTR = "SESSION_USER_ATTR";

	public User authenticateUser(String email, String password)
			throws AppException;
}
