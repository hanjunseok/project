package store.logic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import domain.Schedule;
import store.ScheduleStore;
import store.factory.ConnectionFactory;
import store.logic.util.JdbcUtils;

@Repository
public class ScheduleStoreLogic implements ScheduleStore {
	
	private ConnectionFactory connectionFactory;
	
	public ScheduleStoreLogic() {
		connectionFactory = ConnectionFactory.getInstance();
	}

	@Override
	public  List<Schedule> readAll(int scheduleId){
		String sql = "select schedule_id, schedule_day, schedule_content from schedule_tb where schedule_id=?";

		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		List<Schedule> schedules = new ArrayList<Schedule>();

		try {
			conn = connectionFactory.createConnection();
			psmt = conn.prepareStatement(sql.toString());
			psmt.setInt(1, scheduleId);
			rs = psmt.executeQuery();
			while (rs.next()) {
				Schedule schedule = new Schedule();
				schedule.setScheduleId(rs.getInt(1));
				schedule.setDate(rs.getString(2));
				schedule.setContent(rs.getString(3));
				schedules.add(schedule);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			JdbcUtils.close(rs, psmt, conn);
		}

		return schedules;
	}

	@Override
	public int findScheduleId(String artistName) {
		System.out.println(artistName);
		String sql = "select schedule_id from artist_schedule where artist_name=?";

		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		int artistId = 0;
		try {
			conn = connectionFactory.createConnection();
			psmt = conn.prepareStatement(sql.toString());
			psmt.setString(1, artistName);
			rs = psmt.executeQuery();
			if(rs.next()) {
				artistId = rs.getInt(1);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			JdbcUtils.close(rs, psmt, conn);
		}
		return artistId;
	}



}
