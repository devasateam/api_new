package controllers;

import models.Brand;
import play.mvc.Result;
import play.mvc.Security;

import com.accounts.service.BrandService;
import com.accounts.service.impl.BrandServiceImpl;
import com.onlifli.extractor.BrandExtractor;

@Security.Authenticated(Secured.class)
public class BrandController extends Application {
	private static BrandService brandService = new BrandServiceImpl();

	public static Result saveBrand() {
		
		Brand brand = BrandExtractor.extract(null);
		brand = brandService.saveBrand(brand);
		return jsonResponse(brand, 200);
	}

	public static Result getBrands() {
		return jsonResponse(brandService.getBrands(), 200);
	}
}
