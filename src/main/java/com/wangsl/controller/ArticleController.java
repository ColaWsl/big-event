package com.wangsl.controller;

import com.wangsl.pojo.Article;
import com.wangsl.pojo.PageBean;
import com.wangsl.pojo.Result;
import com.wangsl.service.ArticleService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Validated
@RestController
@RequestMapping("/article")
public class ArticleController {

	private final ArticleService articleService;

	@Autowired
	public ArticleController(ArticleService articleService) {
		this.articleService = articleService;
	}

	@PostMapping
	public Result add(@RequestBody @Validated(Article.Add.class) Article article){
		articleService.add(article);
		return Result.success();
	}

	@GetMapping
	public Result<PageBean<Article>> list(Integer pageNum,
	                                      Integer pageSize,
	                                      @RequestParam(required = false) Integer categoryId,
	                                      @RequestParam(required = false) String state){
		PageBean<Article> pb = articleService.list(pageNum, pageSize, categoryId, state);
		return Result.success(pb);
	}

	@GetMapping("/detail")
	public Result<Article> detail(@NotNull Integer id) {
		Article article = articleService.findById(id);
		return article != null ? Result.success(article) : Result.error("没有当前id文章");
	}


	@PutMapping
	public Result update(@RequestBody @Validated(Article.Update.class) Article article) {
		articleService.update(article);
		return Result.success();
	}

	@DeleteMapping
	public Result delete(@NotNull Integer id) {
		articleService.delete(id);
		return Result.success();
	}
}
