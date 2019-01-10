package store.logic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import domain.User;
import store.UserStore;
import store.factory.ConnectionFactory;
import store.logic.util.JdbcUtils;

@Repository
public class UserStoreLogic implements UserStore {
	
	private ConnectionFactory connectionFactory;
	
	public UserStoreLogic() {
		connectionFactory = ConnectionFactory.getInstance();
	}

	@Override
	public boolean create(User user) {
		Connection connection = null;
		PreparedStatement psmt = null;
		int createdCount = 0;
		try {
			connection = connectionFactory.createConnection();
			
			psmt = connection.prepareStatement("INSERT INTO MEMBERS_TB(USERID, PASSWORD, PHONE) VALUES (?,?,?)");
			
			psmt.setString(1, user.getUserId());
			psmt.setString(2, user.getPassword());
			psmt.setInt(3, user.getPhone());

			createdCount = psmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			JdbcUtils.close(psmt, connection);
		}

		return createdCount > 0;
	}

	@Override
	public User read(String id) {
		String sql = "SELECT USERID, PASSWORD, PHONE FROM MEMBERS_TB WHERE USERID = ?" ;

		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		User user =  null;

		try {
			conn = connectionFactory.createConnection();
			psmt = conn.prepareStatement(sql.toString());
			psmt.setString(1, id);
			rs = psmt.executeQuery();
			if (rs.next()) {
				user = new User();
				user.setUserId(rs.getString(1));
				user.setPassword(rs.getString(2));
				user.setPhone(rs.getInt(3));
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			JdbcUtils.close(rs, psmt, conn);
		}

		return user;
	}

}
