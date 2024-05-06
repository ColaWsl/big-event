package com.wangsl.service;

import com.wangsl.pojo.User;
import org.springframework.stereotype.Service;

public interface UserService {

	User findByUsername(String username);

	void register(String username, String password);

	void update(User user);

	void updateAvatar(String avatarUrl);

	void updatePassword(String password);
}
