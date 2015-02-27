package controllers;

import models.Brand;
import play.mvc.Result;
import play.mvc.Security;

import com.accounts.service.BrandService;
import com.accounts.service.impl.BrandServiceImpl;

@Security.Authenticated(Secured.class)
public class BrandController extends Application {
	private static BrandService brandService = new BrandServiceImpl();

	public static Result saveBrand(String name, String desc) {
		Brand brand = new Brand();
		brand.setName(name);
		brand.setDescription(desc);
		
		
		
		brand = brandService.saveBrand(brand);
		return jsonResponse(brand, 200);
	}
	private static Brand extractBrand(){
		
	}
	

	public static Result getAttributes() {
		return jsonResponse(brandService.getBrands(), 200);
	}
}
