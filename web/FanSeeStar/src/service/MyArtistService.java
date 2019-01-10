package service;

import java.util.List;

import domain.Artist;

public interface MyArtistService {
	boolean register(String userId, String artistName);
	boolean remove(String userId, String artistName);
	List<Artist> findAll(String userId);
}
