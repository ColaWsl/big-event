package com.wangsl.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wangsl.mapper.ArticleMapper;
import com.wangsl.pojo.Article;
import com.wangsl.pojo.PageBean;
import com.wangsl.service.ArticleService;
import com.wangsl.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl implements ArticleService {

	private final ArticleMapper articleMapper;

	@Autowired
	public ArticleServiceImpl(ArticleMapper articleMapper) {
		this.articleMapper = articleMapper;
	}

	@Override
	public void add(Article article) {
		Map<String, Object> map = ThreadLocalUtil.get();
		Integer id = (Integer) map.get("id");
		article.setCreateUser(id);
		articleMapper.add(article);
	}

	@Override
	public PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state) {

		PageHelper.startPage(pageNum, pageSize);

		Map<String, Object> map = ThreadLocalUtil.get();
		Integer id = (Integer) map.get("id");

		Page<Article> page = (Page<Article>) articleMapper.list(id, categoryId, state);
		PageBean<Article> pb = new PageBean<>();
		pb.setTotal(page.getTotal());
		pb.setItems(page.getResult());
		return pb;
	}

	@Override
	public void delete(Integer id) {
		articleMapper.delete(id);
	}

	@Override
	public Article findById(Integer id) {
		return articleMapper.findById(id);
	}

	@Override
	public void update(Article article) {
		articleMapper.update(article);
	}
}
