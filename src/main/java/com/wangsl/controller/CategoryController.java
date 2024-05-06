package com.wangsl.controller;

import com.wangsl.pojo.Category;
import com.wangsl.pojo.Result;
import com.wangsl.service.CategoryService;
import jakarta.validation.constraints.NotNull;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/category")
public class CategoryController {

	private final CategoryService categoryService;

	@Autowired
	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@PostMapping
	public Result add(@RequestBody @Validated(Category.Add.class) Category category) {
		categoryService.add(category);
		return Result.success();
	}

	@GetMapping
	public Result<List<Category>> list() {
		List<Category> list = categoryService.list();
		return Result.success(list);
	}

	@GetMapping("/detail")
	public Result<Category> detail(@NotNull Integer id){
		Category category = categoryService.detail(id);
		return category != null ? Result.success(category) : Result.error("当前用户没有此分类");
	}

	@PutMapping
	public Result update(@RequestBody @Validated(Category.Update.class) Category category){
		categoryService.update(category);
		return Result.success();
	}

	@DeleteMapping
	public Result delete(@NotNull Integer id){
		categoryService.delete(id);
		return Result.success();
	}
}
