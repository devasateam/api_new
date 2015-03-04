package com.accounts.service.impl;

import java.util.List;

import models.Category;

import com.accounts.service.CategoryService;
import com.ecommerce.model.dao.CategoryDao;

/**
 * @author samir
 */
public class CategoryServiceImpl implements CategoryService {

	@Override
	public Category save(Category category) {
		return CategoryDao.create(category);
	}

	@Override
	public Category update(Category category) {
		return CategoryDao.update(category);
	}

	@Override
	public List<Category> list() {
		return CategoryDao.all();
	}

	@Override
	public boolean delete(String id) {
		return CategoryDao.delete(id);
	}

}
