package controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import models.Brand;
import models.BrandContactDetails;
import models.ContactPersonDetails;
import play.Logger;
import play.i18n.Messages;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Http.Request;
import play.mvc.Result;
import play.mvc.Security;

import com.accounts.service.BrandService;
import com.accounts.service.impl.BrandServiceImpl;

@Security.Authenticated(Secured.class)
public class BrandController extends Application {
	private static BrandService brandService = new BrandServiceImpl();

	public static Result saveBrand() {
		Brand brand = extract(request().body().asFormUrlEncoded());
		brand = brandService.saveBrand(brand);
		return jsonResponse("Unable to save brand.", 400);
	}
	
	public static Result saveMultipartBrand() {
		if(request().body().asMultipartFormData().asFormUrlEncoded() != null){
				Logger.info("not nul-----------");
		}else{
			Logger.info("------------nul-----------");
		}
		Brand brand = extract(request().body().asMultipartFormData().asFormUrlEncoded());
		brand = brandService.saveBrand(brand);
		if (brand != null) {
			uploadImage(request(), brand.getId());
			return jsonResponse(brand, 200);
		}
		return jsonResponse("Unable to save brand.", 400);
	}

	public static Result saveImage(String fileName) {
		if (uploadImage(request(), fileName)) {
			return jsonResponse("Image upload successfully.", 200);
		}
		return jsonResponse("Unable to upload", 400);
	}

	public static Result getBrands() {
		return jsonResponse(brandService.getBrands(), 200);
	}

	public static boolean uploadImage(Request request, String fileName) {
		MultipartFormData body = request.body().asMultipartFormData();
		if (body != null) {
			FilePart picture = body.getFile("brandimg");
			if (picture != null) {
				File file = picture.getFile();
				file.renameTo(new File(Messages.get("brand.images") + "\\"
						+ fileName + ".jpg"));
				return true;
			}
		}
		return false;
	}

	private static Brand extract(Map<String, String[]> parameters) {
		Logger.info("brand_name"+parameters.get("brand_name")[0]);
		Brand brand = new Brand();
		brand.setName(parameters.get("brand_name")[0]);
		brand.setDescription(parameters.get("desc")[0]);
		BrandContactDetails brandContactDetails = new BrandContactDetails();
		brandContactDetails.setAddressline1(parameters
				.get("brand_addressline1")[0]);
		brandContactDetails.setAddressline2(parameters
				.get("brand_addressline2")[0]);
		ContactPersonDetails contactPersonDetails = new ContactPersonDetails();
		contactPersonDetails.setName(parameters.get("brand_contact_name")[0]);
		contactPersonDetails.setMobile1("brand_contact_number");
		List<ContactPersonDetails> cpd = new ArrayList<ContactPersonDetails>();
		cpd.add(contactPersonDetails);
		brandContactDetails.setContactPersons(cpd);
		brand.setBrandContactDetails(brandContactDetails);
		return brand;
	}
}
