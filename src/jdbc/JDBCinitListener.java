package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Application Lifecycle Listener implementation class JDBCinitListener
 *
 */
@WebListener
public class JDBCinitListener implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public JDBCinitListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent sce)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent sce)  { 
         
         ServletContext application = sce.getServletContext();
         
         String url = application.getInitParameter("jdbcUrl");
         String user = application.getInitParameter("jdbcUser");
         String pw = application.getInitParameter("jdbcPassword");
      
         
         //1.클래스 로딩
         try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         //2. drivermanager에서 connection 얻기
         //3. close();

         ConnectionProvider.setUrl(url);
         ConnectionProvider.setUser(user);
         ConnectionProvider.setPassword(pw);
         
         try {
			Connection con = DriverManager.getConnection(url, user, pw);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         //context root 경로
         String contextPath = application.getContextPath();
         application.setAttribute("root", contextPath);
    }
	
}
