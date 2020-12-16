package member.service;

import java.sql.Connection;
import java.sql.SQLException;

import auth.service.User;
import jdbc.ConnectionProvider;
import jdbc.jdbcUtil;
import member.dao.MemberDao;
import member.model.Member;

public class RemoveMemberService {
	private MemberDao memberDao = new MemberDao();

	public void removeMember(User user, String pwd) {
		// 0. connection 얻기
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);

			// 1. dao의 selectById로 member를 얻기
			Member member = memberDao.selectById(conn, user.getId());

			// 1.1 member없으면 MemberNotFoundException 발생
			if (member == null) {
				throw new MemberNotFoundException();
			}
			// 2. password와 member.password가 같은지 확인
			// 2.1 다르면 InvalidPasswordException 발생
			if (!member.matchPassword(pwd)) {
				throw new InvalidPasswordException();
			}

			// 3. delete() 실행
			memberDao.delete(conn, user.getId());
			conn.commit();
		} catch (SQLException e) {
			jdbcUtil.rollback(conn);
			throw new RuntimeException();
		} finally {
			jdbcUtil.close(conn);
		}

	}
}
