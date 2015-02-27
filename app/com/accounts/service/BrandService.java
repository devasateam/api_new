package com.accounts.service;

import java.util.List;

import models.Brand;

public interface BrandService {
	Brand saveBrand(Brand attribute);
	List<Brand> getBrands();
}
