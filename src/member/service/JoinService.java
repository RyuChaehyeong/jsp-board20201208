package member.service;

import java.sql.Connection;
import java.sql.SQLException;

import jdbc.ConnectionProvider;
import jdbc.jdbcUtil;
import member.dao.MemberDao;
import member.model.Member;

public class JoinService {
	private MemberDao memberDao = new MemberDao();
	
	
	public void join(JoinRequest joinReq) {
		//table에 insert하는 것이 주업무, table관련 업무는 DAO가 함
		//DAO 객체에 insert하는 일! insert할 때 member 객체에 담아서 함
		
		
		//id 중복 테스트, 있으면 exception 없으면 insert
		//select, insert하는 것을 하나의 transaction으로, 하나의 connection 사용
		Connection con = null;
		try {
			con = ConnectionProvider.getConnection();	
			con.setAutoCommit(false);
			Member m = memberDao.selectById(con, joinReq.getId());
			
			if (m != null) {
				jdbcUtil.rollback(con);
				throw new DuplicateIdException();
			}
			
			Member member = new Member();
			member.setId(joinReq.getId());
			member.setName(joinReq.getName());
			member.setPassword(joinReq.getPassword());
			
			memberDao.insert(con, member);
			con.commit();
			
		} catch (SQLException e) {
			jdbcUtil.rollback(con);
			throw new RuntimeException(e);

		} finally {
			jdbcUtil.close(con);
		}
	}
	
}
