package controllers;

import models.User;
import models.account.utils.AppException;
import play.Logger;
import play.i18n.Messages;
import play.mvc.Http;
import play.mvc.Result;

import com.accounts.service.AuthenticationService;
import com.accounts.service.impl.AuthenticationServiceImpl;

/**
 * @Author Pramod Email:sendpramod@gmail.com
 */
public class AuthenticationController extends Application {

	/**
	 * 
	 * @return Dashboard if auth OK or login form if auth KO
	 * @throws AppException
	 */
	public static Result authenticate(String email, String password)
			throws AppException {
		AuthenticationService authenticationService = new AuthenticationServiceImpl();

		if (email != null || password != null) {
			User authenticateUser = authenticationService.authenticateUser(
					email, password);

			if (authenticateUser == null) {
				return jsonResponse(Messages.get("invalid.user.or.password"),
						401);
			} else if (!authenticateUser.isValidated()) {
				return jsonResponse(
						Messages.get("account.not.validated.check.mail"), 400);
			}
			session("email", authenticateUser.getEmail());
			session("passwordHash", authenticateUser.getPasswordHash());
			session("id", authenticateUser.getId());
			session("name", authenticateUser.getFullname());
			return jsonResponse(Messages.get("account.successfully.validated"),
					200);
		} else {
			session().clear();
			return jsonResponse(Messages.get("invalid.user.or.password"), 400);
		}
	}

	public static Result authCheck() {
		// Check that the email matches a confirmed user before we redirect
		String email = ctx().session().get("email");
		Logger.info("email-----------"+email);
		if (email != null) {
			User user = User.findByEmail(email);
			if (user != null && user.isValidated()) {
				return jsonResponse("success", 200);
			} else {
				Logger.debug("Clearing invalid session credentials");
				session().clear();
				return jsonResponse("failure", 401);
			}
		}
		return jsonResponse("failure", 401);
	}

	/**
	 * Logout and clean the session.
	 * 
	 * @return Index page
	 */
	public static Result logout() {
		session().clear();
		flash("success", Messages.get("youve.been.logged.out"));
		return jsonResponse(Messages.get("youve.been.logged.out"), 200);
	}
}
