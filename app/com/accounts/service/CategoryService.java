/**
 * 
 */
package com.accounts.service;

import java.util.List;

import models.Category;

/**
 * @author samir
 * 
 */
public interface CategoryService {
	Category save(Category category);

	Category update(Category category);

	List<Category> list();

	boolean delete(String id);

}
