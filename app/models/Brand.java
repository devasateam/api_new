package models;

import net.vz.mongodb.jackson.Id;
import net.vz.mongodb.jackson.ObjectId;
import play.data.validation.Constraints.Required;

/**
 * @Author Pramod Email:sendpramod@gmail.com
 */
public class Brand {
	@Id
	@ObjectId
	private String id;

	@Required
	private String name;

	private String description;
	
	private BrandContactDetails brandContactDetails;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
