package models;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Transient;

import net.vz.mongodb.jackson.ObjectId;

import org.codehaus.jackson.annotate.JsonIgnore;

import play.data.validation.Constraints.Required;

/**
 * @author samir
 */
public class Token {
	@Transient
	private static final int EXPIRATION_DAYS = 1;

	@Id
	@ObjectId
	private String id;

	@Required
	private String token;

	@Required
	private String userId;

	@Required
	private String type;

	@Required
	private Long dateCreation;

	@Required
	private String email;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(Long dateCreation) {
		this.dateCreation = dateCreation;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@JsonIgnore
	public boolean isExpired() {
		Date date = new Date(dateCreation);
		return dateCreation != null && date.before(expirationTime());
	}

	@JsonIgnore
	private Date expirationTime() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DATE, -EXPIRATION_DAYS);
		return cal.getTime();
	}
}
