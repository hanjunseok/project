package service;

import java.util.List;

import domain.Board;

public interface BoardService {
	boolean register(Board board);
	boolean remove(int boardId);
	boolean update(Board board);
	List<Board> findByName(int boardId);
	List<Board> findAll(String artistName);
}
