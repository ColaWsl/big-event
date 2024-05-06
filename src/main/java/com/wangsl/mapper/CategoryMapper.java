package com.wangsl.mapper;

import com.wangsl.pojo.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CategoryMapper {

	@Insert("insert into category(category_name, category_alias,create_user, create_time, update_time) " +
			"values(#{categoryName},#{categoryAlias},#{createUser},now(),now())")
	void add(Category category);

	@Select("select * from category where create_user=#{id}")
	List<Category> list(@Param("id") Integer id);

	@Select("select * from category where id=#{id} and create_user=#{userId}")
	Category findById(@Param("id") Integer id, @Param("userId") Integer userId);

	@Update("update category set category_name=#{categoryName},category_alias=#{categoryAlias},update_time=now() where id=#{id}")
	void update(Category category);

	@Delete("delete from category where id=#{id}")
	void delete(@Param("id") Integer id);

}
