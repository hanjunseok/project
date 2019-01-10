package service.logic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.Artist;
import service.MyArtistService;
import store.logic.MyArtistStoreLogic;

@Service
public class MyArtistServiceLogic implements MyArtistService{

	@Autowired
	private MyArtistStoreLogic store;

	@Override
	public boolean register(String userId, String artistName) {
		if (store.existUser(userId, artistName)) {
			return false;
		}
		return store.create(userId, artistName);
	}

	@Override
	public List<Artist> findAll(String userId) {
		return store.readAll(userId);
	}

	@Override
	public boolean remove(String userId, String artistName) {
		return store.delete(userId, artistName);
	}
	
}
