package com.ecommerce.model.dao;

import java.util.List;

import com.mongodb.BasicDBObject;

import models.Brand;
import models.Category;
import net.vz.mongodb.jackson.JacksonDBCollection;
import net.vz.mongodb.jackson.WriteResult;
import play.modules.mongodb.jackson.MongoDB;

/**
 * @Author Pramod Email:sendpramod@gmail.com
 */
public class CategoryDao {
	private static JacksonDBCollection<Category, String> coll = MongoDB
			.getCollection("Categories", Category.class, String.class);

	public static List<Category> all() {
		return coll.find().toArray();
	}

	public static Category create(Category category) {
		if (exist(category.getName())) {
			WriteResult<Category, String> result = coll.save(category);
			return result.getSavedObject();
		} else {
			return null;
		}
	}

	public static Category update(Category category) {
		if (exist(category.getName())) {
			WriteResult<Category, String> result = coll.updateById(
					category.getId(), category);
			return result.getSavedObject();
		} else {
			return null;
		}
	}

	public static boolean delete(String id) {
		Category category = coll.findOneById(id);
		if (category != null) {
			coll.remove(category);
			return true;
		}
		return false;
	}

	private static boolean exist(String name) {
		return coll.findOne(new BasicDBObject().append("name", name)) != null ? true
				: false;
	}
}
