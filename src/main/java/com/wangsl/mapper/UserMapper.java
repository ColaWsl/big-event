package com.wangsl.mapper;

import com.wangsl.pojo.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

	// 根据用户名查询
	@Select("select * from user where username=#{username}")
	User selectByUsername(@Param("username") String username);

	// 添加
	@Insert("insert into user(username, password, create_time, update_time)" +
			"values (#{username}, #{password}, now(), now())")
	void insert(@Param("username") String username, @Param("password") String password);

	// 修改
	@Update("update user set nickname = #{nickname}, email = #{email}, update_time = #{updateTime} where id = #{id}")
	void update(User user);

	// 修改头像
	@Update("update user set user_pic = #{avatarUrl}, update_time = now() where id = #{id}")
	void updateAvatar(@Param("id") Integer id, @Param("avatarUrl") String avatarUrl);

	// 修改密码
	@Update("update user set password = #{password}, update_time = now() where id = #{id}")
	void updatePassword(@Param("id") Integer id, @Param("password") String password);
}
