package com.accounts.service.impl;

import java.util.List;

import play.Logger;
import models.Brand;

import com.accounts.service.BrandService;
import com.ecommerce.model.dao.BrandDao;

public class BrandServiceImpl implements BrandService {

	@Override
	public Brand saveBrand(Brand attribute) {
		return BrandDao.create(attribute);
	}

	@Override
	public Brand updateBrand(Brand attribute) {
		return BrandDao.update(attribute);
	}

	@Override
	public List<Brand> getBrands() {
		return BrandDao.all();
	}

}
