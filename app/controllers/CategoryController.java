package controllers;

import models.Category;
import play.mvc.BodyParser;
import play.mvc.Result;
import play.mvc.Security;

import com.accounts.service.CategoryService;
import com.accounts.service.impl.CategoryServiceImpl;
import com.fasterxml.jackson.databind.JsonNode;

@Security.Authenticated(Secured.class)
public class CategoryController extends Application {
	private static CategoryService categoryService = new CategoryServiceImpl();

	@BodyParser.Of(BodyParser.Json.class)
	public static Result saveCategoryJson() {
		Category category = extract(request().body().asJson());
		category = categoryService.save(category);

		return jsonResponse("Category Created", 200);
	}

	@BodyParser.Of(BodyParser.Json.class)
	public static Result updateCategoryJson() {
		Category category = extract(request().body().asJson());
		category = categoryService.update(category);
		return jsonResponse("Successfully category.", 200);
	}

	public static Result getCategory() {
		return jsonResponse(categoryService.list(), 200);
	}

	private static Category extract(JsonNode json) {
		Category category = new Category();
		category.setName(json.findPath("category_name") != null ? toStoreFriendly(json
				.findPath("category_name").textValue()) : null);
		category.setParentCategoryId(json.findPath("parent_category_name") != null ? toStoreFriendly(json
				.findPath("parent_category_name").textValue()) : null);
		category.setDescription(json.findPath("desc") != null ? json.findPath(
				"desc").textValue() : null);
		return category;
	}

	private static String toStoreFriendly(String name) {
		if (name.length() > 0)
			return name.substring(0, 1).toUpperCase() + name.substring(1);
		else
			return null;
	}
}
