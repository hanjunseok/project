package store.logic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import domain.Artist;
import store.MyAristStore;
import store.factory.ConnectionFactory;
import store.logic.util.JdbcUtils;

@Repository
public class MyArtistStoreLogic implements MyAristStore{
private ConnectionFactory connectionFactory;
	
	public MyArtistStoreLogic() {
		connectionFactory = ConnectionFactory.getInstance();
	}

	@Override
	public boolean create(String userId, String artistName) {
		//
		Connection connection = null;
		PreparedStatement psmt = null;
		int createdCount = 0;
		try {
			connection = connectionFactory.createConnection();
			
			psmt = connection.prepareStatement("insert into members_artist(userid, artist_name) values (?,?)");
			
			psmt.setString(1, userId);
			psmt.setString(2, artistName);

			createdCount = psmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			JdbcUtils.close(psmt, connection);
		}

		return createdCount > 0;
	}

	@Override
	public List<Artist> readAll(String userId) {
		StringBuilder sql = new StringBuilder();
		sql.append("select artist.agency, artist.artist_name, artist.image ");
		sql.append("from artist_tb artist ");
		sql.append("JOIN members_artist ua ");
		sql.append("ON artist.artist_name=ua.artist_name ");
		sql.append("where ua.userId = ?");

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Artist> artists = new ArrayList<Artist>();

		try {
			conn = connectionFactory.createConnection();
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, userId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Artist artist = new Artist();
				artist.setAgency(rs.getString(1));
				artist.setArtistName(rs.getString(2));
				artist.setImage(rs.getString(3));
				artists.add(artist);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			JdbcUtils.close(rs, pstmt, conn);
		}

		return artists;
	}

	@Override
	public boolean existUser(String userId, String artistName) {
		//
		Connection connection = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		try {
			connection = connectionFactory.createConnection();
			
			psmt = connection.prepareStatement("select artist_name from members_artist where userId = ? and artist_name = ?");
			psmt.setString(1, userId);
			psmt.setString(2, artistName);

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
	public boolean delete(String userId, String artistName) {
		Connection connection = null;
		PreparedStatement psmt = null;
		int deleteCount = 0;
		try {
			connection = connectionFactory.createConnection();
			
			psmt = connection.prepareStatement("delete from members_artist where userid=? and artist_name=?");
			psmt.setString(1, userId);
			psmt.setString(2, artistName);

			deleteCount = psmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			JdbcUtils.close(psmt, connection);
		}

		return deleteCount > 0;
	}
}
