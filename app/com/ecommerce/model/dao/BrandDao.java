package com.ecommerce.model.dao;

import java.util.List;

import models.Brand;
import net.vz.mongodb.jackson.JacksonDBCollection;
import net.vz.mongodb.jackson.WriteResult;
import play.modules.mongodb.jackson.MongoDB;

/**
 * @Author Pramod Email:sendpramod@gmail.com
 */
public class BrandDao {
	private static JacksonDBCollection<Brand, String> coll = MongoDB
			.getCollection("Attributes", Brand.class, String.class);

	public static List<Brand> all() {
		return coll.find().toArray();
	}

	public static Brand create(Brand attribute) {
		WriteResult<Brand, String> result= coll.save(attribute);
		return result.getSavedObject();
	}

	public static void delete(String id) {
		Brand attribute = coll.findOneById(id);
		if (attribute != null)
			coll.remove(attribute);
	}
}
