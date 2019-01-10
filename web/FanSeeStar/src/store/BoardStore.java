package store;

import java.util.List;

import domain.Board;

public interface BoardStore {

	boolean wrtie(Board board);
	boolean existBoard(String userId, int boardId);
	boolean delete(int boardId);
	boolean update(Board board);
	List<Board> readByName(int boardId);
	List<Board> readAll(String artistName); 
	
}
