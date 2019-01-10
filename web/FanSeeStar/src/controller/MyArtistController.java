package controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import domain.User;
import service.MyArtistService;

@Controller
public class MyArtistController {
@Autowired
private MyArtistService service;

@RequestMapping("addMyArtist")
public String addMyArtist(HttpSession session, String artistName) {
	if(session == null || session.getAttribute("loginedUser")== null) {
		return "redirect:login";
	}
	User user = (User)session.getAttribute("loginedUser");
	service.register(user.getUserId(), artistName);
	return "redirect:myArtistList";
}

@RequestMapping("myArtistList")
public ModelAndView showMyArtistList(HttpSession session) {
	if(session == null || session.getAttribute("loginedUser")== null) {
		return new ModelAndView("redirect:login");
	}
	User user = (User)session.getAttribute("loginedUser");
	ModelAndView modelAndView = new ModelAndView("myArtist.jsp");
	modelAndView.addObject("artists",service.findAll(user.getUserId()));
	return modelAndView;
}
@RequestMapping("deleteMyArtist")
public String removeMyArtist(HttpSession session, String artistName) {
	User user = (User)session.getAttribute("loginedUser");
	service.remove(user.getUserId(), artistName);
	return "redirect:myArtistList";
	
}
}
