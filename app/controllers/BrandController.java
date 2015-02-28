package controllers;

import java.util.Map;

import models.Brand;
import models.BrandContactDetails;
import play.mvc.Http.Request;
import play.mvc.Result;
import play.mvc.Security;

import com.accounts.service.BrandService;
import com.accounts.service.impl.BrandServiceImpl;

@Security.Authenticated(Secured.class)
public class BrandController extends Application {
	private static BrandService brandService = new BrandServiceImpl();

	public static Result saveBrand() {
		Brand brand = extract(request());
		brand = brandService.saveBrand(brand);
		return jsonResponse(brand, 200);
	}

	public static Result getBrands() {
		return jsonResponse(brandService.getBrands(), 200);
	}
	private static Brand extract(Request request) {
		Brand brand = new Brand();
		Map<String, String[]> parameters = request.body().asFormUrlEncoded();
		brand.setName(parameters.get("name")[0]);
		brand.setDescription(parameters.get("desc")[0]);
		BrandContactDetails brandContactDetails = new BrandContactDetails();
		brandContactDetails.setAddressline1(parameters.get("addressline1")[0]);
		brandContactDetails.setAddressline2(parameters.get("addressline2")[0]);
		brand.setBrandContactDetails(brandContactDetails);
		return brand;
	}
}
