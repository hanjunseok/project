package service.logic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.Schedule;
import service.ScheduleService;
import store.logic.ScheduleStoreLogic;

@Service
public class ScheduleServiceLogic implements ScheduleService {

	@Autowired
	private ScheduleStoreLogic store;

	@Override
	public List<Schedule> findAll(String artistName) {
	    int scheduleId = store.findScheduleId(artistName);
		return store.readAll(scheduleId);
	}

}
