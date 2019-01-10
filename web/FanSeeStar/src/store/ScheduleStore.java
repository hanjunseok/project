package store;

import java.util.List;
import domain.Schedule;

public interface ScheduleStore {
	List<Schedule> readAll(int scheduleId);
	int findScheduleId(String artistName);
}
  