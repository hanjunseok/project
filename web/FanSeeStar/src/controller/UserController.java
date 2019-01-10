package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import domain.User;
import service.UserService;

@Controller 
public class UserController {

	@Autowired
	private UserService service;
	
	@RequestMapping("login")//get 방식
	public String showLoginForm() {
		return "login.jsp";
	}
	@RequestMapping(value="login",method=RequestMethod.POST)//post 방식
	public String login(User user, HttpServletRequest request) {
		User loginedUser = service.login(user);
		
		if(loginedUser != null) {
			HttpSession session = request.getSession();
			session.setAttribute("loginedUser",loginedUser);
		}else {
			HttpSession session = request.getSession();
			session.invalidate();
		}
		
		return "redirect:list";
		
	}
	
	@RequestMapping("join")//get 방식 
	public String showJoinForm() {
		return "join.jsp";
	}
	
	@RequestMapping(value="join",method=RequestMethod.POST)//post 방식
	public String join(User user, HttpServletRequest request) {
		boolean registered = service.register(user);
		if(!registered) {
			return "redirect:join";
	}
		return "redirect:list";
	}
	
	@RequestMapping("logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:list";
	}
}
