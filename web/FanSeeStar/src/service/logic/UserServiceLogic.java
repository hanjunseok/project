package service.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.User;
import service.UserService;
import store.UserStore;

@Service
public class UserServiceLogic implements UserService {

	@Autowired
	private UserStore store;

	public User login(User user) {
		User readedUser = null;
		if (validate(user)) {
			readedUser = store.read(user.getUserId()); 
		}
		return readedUser;
	}

	@Override
	public boolean register(User user) {
		if (!validate(user)) {
			return false;
		} else if (store.read(user.getUserId()) != null) {
			return false;
		}
		return store.create(user);
	}

	@Override
	public User find(String userId) {
		return store.read(userId);
	}

	private boolean validate(User user) {
		if (user == null) {
			throw new RuntimeException("사용자 정보가 없습니다.");
		} else if (user.getUserId() == null || user.getUserId().isEmpty()) {
			throw new RuntimeException("ID가 없습니다.");
		} else if (user.getPassword() == null || user.getPassword().isEmpty()) {
			throw new RuntimeException("비밀번호가 없습니다.");
		}
		return true;
	}

}
