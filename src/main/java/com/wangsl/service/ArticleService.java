package com.wangsl.service;

import com.wangsl.pojo.Article;
import com.wangsl.pojo.PageBean;

public interface ArticleService {
	void add(Article article);

	PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state);

	void delete(Integer id);

	Article findById(Integer id);

	void update(Article article);
}
