package com.accounts.service.impl;

import java.util.List;

import play.Logger;
import models.Brand;

import com.accounts.service.BrandService;
import com.ecommerce.model.dao.BrandDao;

public class BrandServiceImpl implements
		BrandService {

	@Override
	public Brand saveBrand(Brand attribute) {
		Logger.info(attribute.getName()+"----------"+attribute.getBrandContactDetails().getPincode()+"--------");
		return BrandDao.create(attribute);
	}

	@Override
	public List<Brand> getBrands() {
		return BrandDao.all();
	}

}
