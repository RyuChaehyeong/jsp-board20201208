package article.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import article.model.ArticleContent;
import jdbc.jdbcUtil;

public class ArticleContentDao {
	public ArticleContent selectById(Connection conn, int no) throws SQLException {
		String sql = "SELECT * FROM article_content "
				+ "WHERE article_no=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();
			ArticleContent content = null;
			
			if (rs.next()) {
				content = new ArticleContent(rs.getInt("article_no"),
						rs.getString("content"));
			}
			return content;
			
		} finally {
			jdbcUtil.close(rs, pstmt);
		}
				
	}
	
	public ArticleContent insert (Connection conn, ArticleContent content) throws SQLException {
		String sql = "INSERT INTO article_content "
				+ "(article_no, content) "
				+ "VALUES (?, ?)";
		
		PreparedStatement pstmt = null;
		
		try  {
			pstmt = conn.prepareStatement(sql); 
			pstmt.setInt(1, content.getNumber());
			pstmt.setString(2, content.getContent());
			
			int cnt = pstmt.executeUpdate();
			
			if (cnt == 1) {
				return content;
			} else {
				return null;
			}
		} finally {
			jdbcUtil.close(pstmt);
		}
	}
}
