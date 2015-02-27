package com.accounts.service.impl;

import java.util.List;

import models.Brand;

import com.accounts.service.BrandService;
import com.ecommerce.model.dao.BrandDao;

public class BrandServiceImpl implements
		BrandService {

	@Override
	public Brand saveBrand(Brand attribute) {
		return BrandDao.create(attribute);
	}

	@Override
	public List<Brand> getBrands() {
		return BrandDao.all();
	}

}
