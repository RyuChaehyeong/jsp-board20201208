package member.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import auth.service.User;
import member.service.InvalidPasswordException;
import member.service.MemberNotFoundException;
import member.service.RemoveMemberService;
import mvc.command.CommandHandler;

public class RemoveMemberHandler implements CommandHandler{
	private static final String FORM_VIEW = "removeMemberForm";
	private RemoveMemberService removeMemberSvc = new RemoveMemberService();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//이전handler 메소드 참조  get이면 processForm, post이면 processSubmit, 아니면 에러
		if (req.getMethod().equalsIgnoreCase("GET")) {
			return processForm(req, res);
		} else if (req.getMethod().equalsIgnoreCase("POST")) {
			return processSubmit(req, res);
		}
		
		return null;
	}
	
	private String processForm(HttpServletRequest req, HttpServletResponse res) {
		return FORM_VIEW;
	}
	
	private String processSubmit(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//session에서 user 객체를 getAttr (authUser)
		User user = (User) req.getSession().getAttribute("authUser");
		
		//errors Map을 만들어서 request attr에 넣고 ...
		Map<String, Boolean> errors = new HashMap<String, Boolean>();
		req.setAttribute("errors", errors);

		//pw 파라미터 얻고 pw가 null이거나 isEmpty이면 errors에 관련 코드 put, view의 이름을 리턴
		String pwd = req.getParameter("password");
		if(pwd == null || pwd.isEmpty()) {
			errors.put("pwd", Boolean.TRUE);
		}
		if(!errors.isEmpty()) {
			return FORM_VIEW;
		}
		
		//service에 일시키기
		try {
			removeMemberSvc.removeMember(user, pwd);
			//세선을 invalidate
			HttpSession session = req.getSession(false);
			if(session != null) {
				session.invalidate();
			}
			return "removeMemberSuccess";

		//exception 처리
		//문제가 생기면 errors map으로 코드 추가 폼으로 forward하도록 view의 이름을 리턴
		//문제1. 사용자가 없는 경우  문제2.password가 틀린경우 (캐치문 2개)
		} catch (InvalidPasswordException e) {
			errors.put("badCurPwd", Boolean.TRUE);
			return FORM_VIEW;
		} catch (MemberNotFoundException e) {
			res.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}
		
	}

}
