package store;

import java.util.List;

import domain.Artist;

public interface MyAristStore {

	boolean existUser(String userId, String artistName);
	boolean create(String userId, String artistName);
	List<Artist> readAll(String userId);
	boolean delete(String userId, String artistName);
}
