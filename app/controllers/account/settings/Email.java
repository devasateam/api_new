package controllers.account.settings;

import java.io.IOException;
import java.net.MalformedURLException;

import models.Token;
import models.User;
import play.Logger;
import play.i18n.Messages;
import play.mvc.Result;
import play.mvc.Security;

import com.ecommerce.model.dao.TokenDao;

import controllers.Application;
import controllers.Secured;

/**
 * Settings -> Email page.
 * 
 * @author samir
 */
@Security.Authenticated(Secured.class)
public class Email extends Application {

	/**
	 * Password Page. Ask the user to change his password.
	 * 
	 * @return index settings
	 */
	public static Result index() {
		return jsonResponse(Messages.get("signup.reset.password"), 200);
	}

	/**
	 * Send a mail to confirm.
	 * 
	 * @return email page with flash error or success
	 */
	public static Result runEmail() {
		User user = User.findByEmail(request().username());
		try {
			String mail = user.getEmail();
			TokenDao.sendMailChangeMail(user, mail);
			flash("success", Messages.get("changemail.mailsent"));
			return jsonResponse(Messages.get("changemail.mailsent"), 200);
		} catch (MalformedURLException e) {
			Logger.error("Cannot validate URL", e);
			flash("error", Messages.get("error.technical"));
		}
		return jsonResponse(Messages.get("signup.reset.password"), 200);
	}

	/**
	 * Validate a email.
	 * 
	 * @return email page with flash error or success
	 * @throws IOException
	 */
	public static Result validateEmail(String token) throws IOException {
		User user = User.findByEmail(request().username());

		if (token == null) {
			flash("error", Messages.get("error.technical"));
			return jsonResponse(Messages.get("error.technical"), 400);
		}

		Token resetToken = TokenDao.findByTokenAndType(token, "email");
		if (resetToken == null) {
			flash("error", Messages.get("error.technical"));
			return jsonResponse(Messages.get("error.technical"), 400);
		}

		if (resetToken.isExpired()) {
			TokenDao.delete(resetToken);
			flash("error", Messages.get("error.expiredmaillink"));
			return jsonResponse(Messages.get("error.expiredmaillink"), 400);
		}

		user.setEmail(resetToken.getEmail()); 
		User.update(user);

		session("email", resetToken.getEmail());

		flash("success",
				Messages.get("account.settings.email.successful", user.getEmail()));

		return jsonResponse(
				Messages.get(user.getEmail() + "ccount.settings.email.successful"),
				200);
	}
}
