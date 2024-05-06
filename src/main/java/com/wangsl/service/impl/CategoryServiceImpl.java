package com.wangsl.service.impl;

import com.wangsl.mapper.CategoryMapper;
import com.wangsl.pojo.Category;
import com.wangsl.service.CategoryService;
import com.wangsl.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService {

	private final CategoryMapper categoryMapper;

	@Autowired
	public CategoryServiceImpl(CategoryMapper categoryMapper) {
		this.categoryMapper = categoryMapper;
	}

	@Override
	public void add(Category category) {
		Map<String, Object> map = ThreadLocalUtil.get();
		Integer id = (Integer) map.get("id");
		category.setCreateUser(id);
		categoryMapper.add(category);
	}

	@Override
	public List<Category> list() {
		Map<String, Object> map = ThreadLocalUtil.get();
		Integer id = (Integer) map.get("id");
		return categoryMapper.list(id);
	}

	@Override
	public Category detail(Integer id) {
		Map<String, Object> map = ThreadLocalUtil.get();
		Integer userId = (Integer) map.get("id");
		Category category = categoryMapper.findById(id, userId);
		return category;
	}

	@Override
	public void update(Category category) {
		categoryMapper.update(category);
	}

	@Override
	public void delete(Integer id) {
		categoryMapper.delete(id);
	}


}
