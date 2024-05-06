package com.wangsl.service;

import com.wangsl.pojo.Category;

import java.util.List;

public interface CategoryService {
	void add(Category category);

	List<Category> list();

	Category detail(Integer id);

	void update(Category category);

	void delete(Integer id);
}
