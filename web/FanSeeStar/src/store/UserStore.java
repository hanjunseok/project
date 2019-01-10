package store;

import domain.User;

public interface UserStore {
	//
	boolean create(User user);
	User read(String id);
}
