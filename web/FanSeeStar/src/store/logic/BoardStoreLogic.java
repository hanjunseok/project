package store.logic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import domain.Board;
import store.BoardStore;
import store.factory.ConnectionFactory;
import store.logic.util.JdbcUtils;

@Repository
public class BoardStoreLogic implements BoardStore{
	private ConnectionFactory connectionFactory;
	
	public BoardStoreLogic() {
		connectionFactory = ConnectionFactory.getInstance();
	}

	@Override
	public boolean wrtie(Board board) {
		//
		Connection connection = null;
		PreparedStatement psmt = null;
		int createdCount = 0;
		try {
			connection = connectionFactory.createConnection();
			
			psmt = connection.prepareStatement("insert into board_tb values (0,?,?,?,?,null)");
			
			psmt.setString(1, board.getArtistName());
			psmt.setString(2, board.getWriter());
			psmt.setString(3, board.getBoardTitle());
			psmt.setString(4, board.getBoardContent());

			createdCount = psmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			JdbcUtils.close(psmt, connection);
		}

		return createdCount > 0;
	}
	
	@Override
	public List<Board> readAll(String artistName) {
		String sql = "select board_id, artist_name, writer, board_title, board_content, board_image from board_tb where artist_name=? order by board_id desc";

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Board> boards = new ArrayList<Board>();

		try {
			conn = connectionFactory.createConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, artistName);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Board board = new Board();
				board.setBoardId(rs.getInt(1));
				board.setArtistName(rs.getString(2));
				board.setWriter(rs.getString(3));
				board.setBoardTitle(rs.getString(4));
				board.setBoardContent(rs.getString(5));
				board.setBoardImage(rs.getString(6));
				boards.add(board);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			JdbcUtils.close(rs, pstmt, conn);
		}

		return boards;
	}

	@Override
	public List<Board> readByName(int boardId) {
		String sql = "select board_id, artist_name, writer, board_title, board_content, board_image from board_tb where board_id=?";

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Board> boards = new ArrayList<Board>();

		try {
			conn = connectionFactory.createConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Board board = new Board();
				board.setBoardId(rs.getInt(1));
				board.setArtistName(rs.getString(2));
				board.setWriter(rs.getString(3));
				board.setBoardTitle(rs.getString(4));
				board.setBoardContent(rs.getString(5));
				board.setBoardImage(rs.getString(6));
				boards.add(board);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			JdbcUtils.close(rs, pstmt, conn);
		}

		return boards;
	}

	@Override
	public boolean existBoard(String loginId, int boardId) {
		//
		Connection connection = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		try {
			connection = connectionFactory.createConnection();
			
			psmt = connection.prepareStatement("select board_id from board_tb where writer = ? and board_id = ?");
			psmt.setString(1, loginId);
			psmt.setInt(2, boardId);

			rs = psmt.executeQuery();
			if (rs.next()) {
				return true;
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			JdbcUtils.close(rs, psmt, connection);
		}

		return false;
	}

	@Override
	public boolean delete(int boardId) {
		Connection connection = null;
		PreparedStatement psmt = null;
		int deleteCount = 0;
		try {
			connection = connectionFactory.createConnection();
			
			psmt = connection.prepareStatement("delete from board_tb where board_id=?");
			psmt.setInt(1, boardId);

			deleteCount = psmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			JdbcUtils.close(psmt, connection);
		}

		return deleteCount > 0;
	}

	@Override
	public boolean update(Board board) {
		Connection connection = null;
		PreparedStatement psmt = null;
		int createdCount = 0;
		try {
			connection = connectionFactory.createConnection();
			
			psmt = connection.prepareStatement("update board_tb set board_title=?, board_content=? where board_id=?");
			
			psmt.setString(1, board.getBoardTitle());
			psmt.setString(2, board.getBoardContent());
			psmt.setInt(3, board.getBoardId());
		

			createdCount = psmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			JdbcUtils.close(psmt, connection);
		}

		return createdCount > 0;
	}
}
