package article.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sun.corba.se.spi.orbutil.fsm.State;

import article.model.Article;
import article.model.Writer;
import jdbc.jdbcUtil;

public class ArticleDao {
	
	public Article selectedById(Connection conn, int no) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM article WHERE article_no=?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();
			Article article = null;
			if (rs.next()) {
				article = convertArticle(rs);
			}
			return article;
		} finally {
			jdbcUtil.close(rs, pstmt);
		}
	}
	
	public void increaseReadCount(Connection conn, int no) throws SQLException {
		String sql = "UPDATE article "
				+ "SET read_cnt = read_cnt + 1 "
				+ "WHERE article_no=?";
		try (PreparedStatement pstmt = 
				conn.prepareStatement(sql)) {
			pstmt.setInt(1, no);
			pstmt.executeUpdate();
		}
	}
	
	public List<Article> select(Connection conn, int pageNum, int size) throws SQLException {
		String sql = "SELECT rn, article_no, writer_id, writer_name, title, regdate, moddate, read_cnt "
				+ "FROM ("
				+ "SELECT article_no, "
						+ "writer_id, "
						+ "writer_name, "
						+ "title, "
						+ "regdate, "
						+ "moddate, "
						+ "read_cnt, "
						+ "ROW_NUMBER() over (ORDER BY article_no DESC) rn "
				+ "FROM article) "
				+ "WHERE rn BETWEEN ? AND ?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, (pageNum-1) * size + 1);
			pstmt.setInt(2, pageNum * size);
			
			rs = pstmt.executeQuery();
			List<Article> result = new ArrayList<Article>();
			while (rs.next()) {
				result.add(convertArticle(rs));
			}
			return result;
		} finally {
			jdbcUtil.close(rs, pstmt);
		}
	}
	
	private Article convertArticle(ResultSet rs) throws SQLException {
		return new Article(rs.getInt("article_no"),
					new Writer(
							rs.getString("writer_id"),
							rs.getString("writer_name")
							),
					rs.getString("title"),
					rs.getTimestamp("regdate"),
					rs.getTimestamp("moddate"),
					rs.getInt("read_cnt")
				);
	}
	
	public int selectCount(Connection conn) throws SQLException {
		String sql = "SELECT COUNT(*) FROM article";
		
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			if(rs.next()) {
				return rs.getInt(1);
			}
			return 0;
		} finally {
			jdbcUtil.close(rs, stmt);
		}
		
	}
	
	public Article insert (Connection conn, Article article) throws SQLException {
		String sql = "INSERT INTO article "
				+ "(writer_id, writer_name, title, regdate, moddate, read_cnt) "
				+ "VALUES (?, ?, ?, SYSDATE, SYSDATE, 0)";
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try { 
			pstmt = conn.prepareStatement(sql, new String[] {"article_no", "regdate", "moddate"});
			int col = 1;
			pstmt.setString(col++, article.getWriter().getId());
			pstmt.setString(col++, article.getWriter().getName());
			pstmt.setString(col++, article.getTitle());
			int cnt = pstmt.executeUpdate();
			
			if (cnt == 1) {
				rs = pstmt.getGeneratedKeys();
				int key = 0;
				Date regDate = null;
				Date modDate = null;
				if (rs.next()) {
					//generate된 column 3개
					key = rs.getInt(1);
					regDate = rs.getTimestamp(2);
					modDate = rs.getTimestamp(3);
				}
				
				return new Article(key, article.getWriter(), article.getTitle(), 
						regDate, modDate, 0);
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			jdbcUtil.close(pstmt);
		}
	}
}
