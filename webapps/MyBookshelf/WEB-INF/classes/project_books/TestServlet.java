package project_books;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.annotation.Resource;
import javax.servlet.ServletException;
// import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import javax.naming.Context;
import javax.naming.InitialContext;

// @WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    /*
	// Define datasource/connection pool for Resource Injection
	@Resource(name = "jdbc/mybookshelf")
	private DataSource dataSource; 
    */

    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// Step 1:  Set up the printwriter
		PrintWriter out = response.getWriter();
		response.setContentType("text/plain");
		
		// Step 2:  Get a connection to the database
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
            Context initCtx;
            initCtx = new InitialContext();
            Context envContext = (Context) initCtx.lookup("java:/comp/env");
            DataSource db = (DataSource) envContext.lookup("jdbc/mybookshelf");
            myConn = db.getConnection();
            out.println("YIPPIE YEAH - IT WORKS!!!");
            
			//myConn = dataSource.getConnection();
			myStmt = myConn.createStatement(); 

			// Step 3:  Create a SQL statement
			String sql = "SELECT * FROM Books";
			myStmt = myConn.createStatement();
			
			// Step 4:  Execute SQL query
			myRs = myStmt.executeQuery(sql);
			
			// Step 5:  Process the Result Set
			while (myRs.next()) {
				String book_title = myRs.getString("Title");
				String place = myRs.getString("Place of publication");
				String author = myRs.getString("Author\u0027s name");
				String year = myRs.getString("Year of publication");
				out.println(book_title + ", published in " + place + ", by " + author + " --- Date: " + year);
			}
		}
		catch (Exception exc) {
            StringWriter errors = new StringWriter();
            exc.printStackTrace(new PrintWriter(errors));
			out.println(errors.toString());
		}
        finally {
            close(myConn, myStmt, myRs);
        }
    }
    
    private void close(Connection myConn, Statement myStmt, ResultSet myRs) {

        try {
            if (myRs != null) {
                myRs.close();
            }
            if (myStmt != null) {
                myStmt.close();
            }
            if (myConn != null) {
                myConn.close();
            }
        }
        catch (Exception exc) {
            exc.printStackTrace();
        }
    }

}