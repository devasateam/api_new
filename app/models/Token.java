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
	public String id;

	@Required
	public String token;

	@Required
	public String userId;

	@Required
	public String type;

	@Required
	public Long dateCreation;

	@Required
	public String email;

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
