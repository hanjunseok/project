package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import domain.Schedule;
import service.ScheduleService;


@Controller
public class ScheduleController {
	@Autowired
	private ScheduleService service;

	@RequestMapping("list")
	public String showLoginForm() {
		return "index.jsp";
	}
	
	@RequestMapping("schedule")
	public ModelAndView showSchedule2(String artistName, String company) {
		List<Schedule> list = service.findAll(artistName);
		ModelAndView modelAndView = new ModelAndView("schedule.jsp");
		modelAndView.addObject("schedules", list);
		modelAndView.addObject("company", company);
		return modelAndView;
	}
} 
