package com.onlifli.extractor;

import static play.data.Form.form;
import models.Brand;
import play.data.Form;
import play.mvc.Http.Request;

public class BrandExtractor {
	public static Brand extract(Request request) {
		Form<Brand> registerForm = form(Brand.class).bindFromRequest();
		if (registerForm.hasErrors()) {
			return null;
		}
		Brand brand = registerForm.get();
		return brand;
		//TODO VALIDATION
		// Map<String, String[]> parameters = request.body().asFormUrlEncoded();
		// String name = parameters.get("name")[0];
		// String email = parameters.get("email")[0];
		// String password = parameters.get("password")[0];
	}

}
