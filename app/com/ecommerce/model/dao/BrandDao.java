package com.ecommerce.model.dao;

import java.util.List;

import com.mongodb.BasicDBObject;

import models.Brand;
import net.vz.mongodb.jackson.JacksonDBCollection;
import net.vz.mongodb.jackson.WriteResult;
import play.modules.mongodb.jackson.MongoDB;

/**
 * @Author Pramod Email:sendpramod@gmail.com
 */
public class BrandDao {
	private static JacksonDBCollection<Brand, String> coll = MongoDB
			.getCollection("Brands", Brand.class, String.class);

	public static List<Brand> all() {
		return coll.find().toArray();
	}

	public static Brand create(Brand attribute) {
		if(exist(attribute.getName())){
			WriteResult<Brand, String> result = coll.save(attribute);
			return result.getSavedObject();
		}else {
			return null;
		}
	}

	public static Brand update(Brand attribute) {
		WriteResult<Brand, String> result = coll.updateById(attribute.getId(),
				attribute);
		return result.getSavedObject();
	}

	public static void delete(String id) {
		Brand attribute = coll.findOneById(id);
		if (attribute != null)
			coll.remove(attribute);
	}

	private static boolean exist(String name) {
		return coll.findOne(new BasicDBObject().append("name", name)) != null ? true
				: false;
	}
}
