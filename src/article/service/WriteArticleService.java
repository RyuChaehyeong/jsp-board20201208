package article.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import article.dao.ArticleContentDao;
import article.dao.ArticleDao;
import article.model.Article;
import article.model.ArticleContent;
import jdbc.ConnectionProvider;
import jdbc.jdbcUtil;

public class WriteArticleService {
	private ArticleDao articleDao = new ArticleDao();
	private ArticleContentDao contentDao = new ArticleContentDao();
	
	public Integer write (WriteRequest req) {
		Connection conn = null;
		
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false); //작업2개
			
			//작업1
			Article article = toArticle(req);
			
			Article savedArticle = articleDao.insert(conn, article);
			if (savedArticle == null) {
				throw new RuntimeException("fail to insert article");
			}
			
			//작업2
			ArticleContent content = new ArticleContent(
					savedArticle.getNumber(), 
					req.getContent());
			ArticleContent savedContent = contentDao.insert(conn, content);
			if(savedContent == null) {
				throw new RuntimeException("fail to insert article_cotent");
			}
			
			conn.commit();
			return savedArticle.getNumber();
					
		} catch (SQLException e) {
			jdbcUtil.rollback(conn);
			throw new RuntimeException();
		} catch (RuntimeException e) {
			jdbcUtil.rollback(conn);
			throw e;
		} finally {
			jdbcUtil.close(conn);
		}
	}

	private Article toArticle(WriteRequest req) {
		return new Article(null, req.getWriter(), req.getTitle(), null, null, 0); 
	}
}
