package com.ecommerce.model.dao;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import models.Token;
import models.User;
import models.account.utils.Mail;
import models.account.utils.MailContent;
import net.vz.mongodb.jackson.JacksonDBCollection;
import play.Configuration;
import play.Logger;
import play.i18n.Messages;
import play.modules.mongodb.jackson.MongoDB;

import com.mongodb.BasicDBObject;

public class TokenDao {
	public static JacksonDBCollection<Token, String> tokenCollection = MongoDB
			.getCollection("Token", Token.class, String.class);

	public static Token findByTokenAndType(String token, String type)
			throws IOException {
		return tokenCollection.findOne(new BasicDBObject().append("token",
				token).append("type", type));
	}

	private static Token getNewToken(User user, String type, String email) {
		Logger.info(user + "-------" + type + "-----------" + email);
		Token token = new Token();
		token.token = UUID.randomUUID().toString();
		token.userId = user.getId();
		token.type = type;
		token.email = user.email;
		Date date = new Date();
		token.dateCreation = date.getTime();
		tokenCollection.save(token);
		return token;
	}

	public static void sendMailResetPassword(User user)
			throws MalformedURLException {
		sendMail(user, "password", null);
	}

	public static void sendMailChangeMail(User user, @Nullable String email)
			throws MalformedURLException {
		sendMail(user, "email", email);
	}

	private static void sendMail(User user, String type, String email)
			throws MalformedURLException {
		Logger.info(email + "------------------------------");
		Token token = getNewToken(user, type.toString(), email);
		String externalServer = Configuration.root().getString(
				"server.hostname");

		String subject = null;
		String message = null;
		String toMail = null;

		URL url = new URL("http://" + externalServer + "/reset/" + token.token);
		if (type.equals("password")) {
			subject = Messages.get("mail.reset.ask.subject");
			message = Messages.get("mail.reset.ask.message", url.toString());
			toMail = user.email;
		} else if (type.equals("email")) {
			subject = Messages.get("mail.change.ask.subject");
			message = Messages.get("mail.change.ask.message", url.toString());
			toMail = token.email; // == email parameter
		}
		List<String> mail = new ArrayList<String>();
		mail.add(toMail);
		Logger.debug("sendMailResetLink: url = " + url);
		MailContent content = new MailContent(subject, message, mail);
		Mail.sendMail(content);
	}

	public static void save(Token token) {
		tokenCollection.save(token);
	}

	public static void delete(Token token) {
		tokenCollection.remove(token);
	}

}
