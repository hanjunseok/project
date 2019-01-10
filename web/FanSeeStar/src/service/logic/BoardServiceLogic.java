package service.logic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.Board;
import service.BoardService;
import store.logic.BoardStoreLogic;

@Service
public class BoardServiceLogic implements BoardService{

	@Autowired
	private BoardStoreLogic store;

	@Override
	public boolean register(Board board) {
		return store.wrtie(board);
	}

	@Override
	public List<Board> findAll(String artistName) {
		return store.readAll(artistName);
	}

	@Override
	public List<Board> findByName(int boardId) {
		return store.readByName(boardId);
	}

	@Override
	public boolean remove(int boardId) {
		return store.delete(boardId);
	}

	@Override
	public boolean update(Board board) {
		return store.update(board);
	}
}
