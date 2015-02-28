package controllers.account;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import models.User;
import models.account.utils.AppException;
import models.account.utils.Hash;
import models.account.utils.Mail;
import models.account.utils.MailContent;

import org.apache.commons.mail.EmailException;

import play.Configuration;
import play.Logger;
import play.i18n.Messages;
import play.mvc.Result;
import controllers.Application;

/**
 * Signup to onliofli : save and send confirm mail.
 * <p/>
 * 
 * @author samir
 */
public class Signup extends Application {

	/**
	 * Save the new user.
	 * 
	 * @return Successfull page or created form if bad
	 */
	public static Result save(String name, String email, String password) {
		Result resultError = checkBeforeSave(email);

		if (resultError != null) {
			return resultError;
		}

		try {
			User user = new User();
			user.setEmail(email);
			user.setFullname(name);
			user.setPasswordHash(password);
			user.setConfirmationToken(Hash.createPassword(password));
			user.setConfirmationToken(UUID.randomUUID().toString());
			User.create(user);

			sendMailAskForConfirmation(user);

			return jsonResponse(Messages.get("account.successfully.validated"),
					200);
		} catch (EmailException e) {
			Logger.debug("Signup.save Cannot send email", e);
			flash("error", Messages.get("error.sending.email"));
		} catch (Exception e) {
			Logger.error("Signup.save error", e);
			flash("error", Messages.get("error.technical"));
		}

		return jsonResponse(Messages.get("error.email.already.exist"), 200);
	}

	/**
	 * Check if the email already exists.
	 * 
	 * @param registerForm
	 *            User Form submitted
	 * @param email
	 *            email address
	 * @return Index if there was a problem, null otherwise
	 */
	private static Result checkBeforeSave(String email) {
		// Check unique email
		if (User.findByEmail(email) != null) {
			flash("error", Messages.get("error.email.already.exist"));
			return jsonResponse(Messages.get("error.email.already.exist"), 200);
		}

		return null;
	}

	/**
	 * Send the welcome Email with the link to confirm.
	 * 
	 * @param user
	 *            user created
	 * @throws EmailException
	 *             Exception when sending mail
	 */
	private static void sendMailAskForConfirmation(User user)
			throws EmailException, MalformedURLException {
		String subject = Messages.get("mail.confirm.subject");
		String urlString = "http://"
				+ Configuration.root().getString("server.hostname");
		urlString += "/confirm/" + user.getConfirmationToken();
		URL url = new URL(urlString); // validate the URL, will throw an
										// exception if bad.
		String message = Messages.get("mail.confirm.message", url.toString());

		// Mail.Envelop envelop = new Mail.Envelop(subject, message,
		// user.email);
		List<String> emailList = new ArrayList<String>();
		emailList.add(user.getEmail());
		MailContent content = new MailContent(subject, message, emailList);
		Mail.sendMail(content);
	}

	/**
	 * Valid an account with the url in the confirm mail.
	 * 
	 * @param token
	 *            a token attached to the user we're confirming.
	 * @return Confirmationpage
	 */
	public static Result confirm(String token) {
		Logger.info("----------------------------------token------------"
				+ token);
		User user = User.findByConfirmationToken(token);
		if (user == null) {
			flash("error", Messages.get("error.unknown.email"));
			return jsonResponse(Messages.get("error.unknown.email"), 200);
		}

		if (user.isValidated()) {
			flash("error", Messages.get("error.account.already.validated"));
			return jsonResponse(
					Messages.get("error.account.already.validated"), 200);
		}

		try {
			if (User.confirm(user)) {
				sendMailConfirmation(user);
				flash("success", Messages.get("account.successfully.validated"));
				return jsonResponse(
						Messages.get("account.successfully.validated"), 200);
			} else {
				Logger.debug("Signup.confirm cannot confirm user");
				flash("error", Messages.get("error.confirm"));
				return jsonResponse(
						Messages.get("Signup.confirm cannot confirm user"), 200);
			}
		} catch (AppException e) {
			Logger.error("Cannot signup", e);
			flash("error", Messages.get("error.technical"));
		} catch (EmailException e) {
			Logger.debug("Cannot send email", e);
			flash("error", Messages.get("error.sending.confirm.email"));
		}
		return jsonResponse(Messages.get("error.sending.confirm.email"), 200);
	}

	/**
	 * Send the confirm mail.
	 * 
	 * @param user
	 *            user created
	 * @throws EmailException
	 *             Exception when sending mail
	 */
	private static void sendMailConfirmation(User user) throws EmailException {
		String subject = Messages.get("mail.welcome.subject");
		String message = Messages.get("mail.welcome.message");
		List<String> emailList = new ArrayList<String>();
		emailList.add(user.getEmail());
		MailContent content = new MailContent(subject, message, emailList,
				null, null, null);
		Mail.sendMail(content);
	}
}
