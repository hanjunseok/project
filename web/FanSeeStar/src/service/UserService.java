package service;

import domain.User;

public interface UserService {
	//
	User login(User user);
	boolean register(User user);
	User find(String loginId);
}
