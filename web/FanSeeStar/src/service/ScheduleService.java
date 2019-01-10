package service;

import java.util.List;

import domain.Schedule;

public interface ScheduleService {

	List<Schedule> findAll(String artistName);
}
