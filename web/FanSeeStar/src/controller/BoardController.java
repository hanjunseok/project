package controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.Board;
import domain.User;
import service.BoardService;

@Controller
public class BoardController {
	@Autowired
	private BoardService service;
	String artist;

	@RequestMapping("boardList")
	public ModelAndView showBoardList(HttpSession session, String artistName) {
		if (session == null || session.getAttribute("loginedUser") == null) {
			return new ModelAndView("redirect:login");
		}
		if (artistName == null) {
			artistName = artist;
		}

		ModelAndView modelAndView = new ModelAndView("listBoard.jsp");
		modelAndView.addObject("boards", service.findAll(artistName));
		modelAndView.addObject("artist", artistName);
		return modelAndView;
	}

	@RequestMapping(value = "boardCreate", method = RequestMethod.POST)
	public String insertBoard(HttpSession session, Board board) {
		if (session == null || session.getAttribute("loginedUser") == null) {
			return "redirect:login";
		}
		artist = board.getArtistName();
		service.register(board);
		return "redirect:boardList";
	}

	@RequestMapping("detailBoard")
	public ModelAndView showDetailBoard(HttpSession session, int boardId) {
		if (session == null || session.getAttribute("loginedUser") == null) {
			return new ModelAndView("redirect:login");
		}

		ModelAndView modelAndView = new ModelAndView("detailBoard.jsp");
		modelAndView.addObject("boards", service.findByName(boardId));
		return modelAndView;
	}

	@RequestMapping("deleteBoard")
	public String deleteBoard(HttpSession session, Board board) {
		if (session == null || session.getAttribute("loginedUser") == null) {
			return "redirect:login";
		}
		User user = (User) session.getAttribute("loginedUser");
		artist = board.getArtistName();
		if (user.getUserId().equals(board.getWriter())) {
			service.remove(board.getBoardId());
		}
		return "redirect:boardList";
	}

	@RequestMapping("updateBoardList")
	public ModelAndView showUpdateBoard(HttpSession session, Board board) {
		if (session == null || session.getAttribute("loginedUser") == null) {
			return new ModelAndView("redirect:login");
		}

		User user = (User) session.getAttribute("loginedUser");
		if (user.getUserId().equals(board.getWriter())) {
			artist = board.getArtistName();
			ModelAndView modelAndView = new ModelAndView("updateBoard.jsp");
			modelAndView.addObject("boards", service.findByName(board.getBoardId()));
			return modelAndView;
		}
		return new ModelAndView("redirect:boardList");
	}
	
	@RequestMapping(value="updateBoard", method = RequestMethod.POST)
	public String updateBoard(HttpSession session, Board board) {
		if (session == null || session.getAttribute("loginedUser") == null) {
			return "redirect:login";
		}
		User user = (User) session.getAttribute("loginedUser");
		artist = board.getArtistName();
		if (user.getUserId().equals(board.getWriter())) {
			service.update(board);
		}
		return "redirect:boardList";
	}
}
