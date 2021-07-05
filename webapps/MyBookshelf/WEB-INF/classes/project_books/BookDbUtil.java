package project_books;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

import javax.naming.Context;
import javax.naming.InitialContext;

public class BookDbUtil {

    private DataSource dataSource;

    // Constructor
    public BookDbUtil(DataSource theDataSource) {
        dataSource = theDataSource;
    }

    // method to list all Book objects
    public List<Book> getBooks() throws Exception {

        List<Book> books = new ArrayList<>();

        Connection myConn = null;
        Statement myStmt = null;
        ResultSet myRs = null;

        try {
            // get a connection
            // myConn = dataSource.getConnection();
            Context initCtx;
            initCtx = new InitialContext();
            Context envContext = (Context) initCtx.lookup("java:/comp/env");
            DataSource dataSource = (DataSource) envContext.lookup("jdbc/mybookshelf");
            myConn = dataSource.getConnection();

            // create sql statement
            String sql = "SELECT * FROM Books ORDER BY ID";
            myStmt = myConn.createStatement();

            // execute query
            myRs = myStmt.executeQuery(sql);

            // process result set
            while (myRs.next()) {
                // retrieve data from result set row
                int id = myRs.getInt("ID");
                String famName = myRs.getString("Author\u0027s name");
                String fstName = myRs.getString("Author\u0027s first name");
                String title = myRs.getString("Title");
                String placePubl = myRs.getString("Place of publication");
                int yearPubl = myRs.getInt("Year of publication");
                // create new book object, using the retrieved data
                Book tempBook = new Book (id, famName, fstName, title, placePubl, yearPubl);
                // add it to the list of books
                books.add(tempBook);
            }
            return books;
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

    // method ADD BOOK
    public void addBook(Book theBook) throws Exception {

        Connection myConn = null;
        PreparedStatement myStmt = null;

        try {
            // get database connection
            // myConn = dataSource.getConnection();

            Context initCtx;
            initCtx = new InitialContext();
            Context envContext = (Context) initCtx.lookup("java:/comp/env");
            DataSource dataSource = (DataSource) envContext.lookup("jdbc/mybookshelf");
            myConn = dataSource.getConnection();
            // create sql for INSERT
            String sql = "INSERT INTO Books (ID, \u0060Author\u0027s name\u0060, " +
            "\u0060Author\u0027s first name\u0060, Title, \u0060Place of publication\u0060, \u0060Year of publication\u0060) " +
            "VALUES (?, ?, ?, ?, ?, ?)";
            myStmt = myConn.prepareStatement(sql);
            // set the param values for the book
            myStmt.setInt(1, theBook.getId());
            myStmt.setString(2, theBook.getNameFam());
            myStmt.setString(3, theBook.getNameFirst());
            myStmt.setString(4, theBook.getTitle());
            myStmt.setString(5, theBook.getPlacePubl());
            myStmt.setInt(6, theBook.getYearPubl());
            // execute the sql statement
            myStmt.execute();
        }
        finally {
            // clean up JDBC objects
            close(myConn, myStmt, null);
        }
    }

    public void updateBook(Book theBook) throws Exception {

        Connection myConn = null;
        PreparedStatement myStmt = null;

        try {
            // get db connection
            Context initCtx;
            initCtx = new InitialContext();
            Context envContext = (Context) initCtx.lookup("java:/comp/env");
            DataSource dataSource = (DataSource) envContext.lookup("jdbc/mybookshelf");
            myConn = dataSource.getConnection();

            // create SQL update statement
            String sql = "UPDATE books "
                        + "SET id=?, `Author's name`=?, `Author's first name`=?, Title=?, `Place of publication`=?, `Year of publication`=? "
                        + "WHERE id=?";

            // prepare statement
            myStmt = myConn.prepareStatement(sql);

            // set params
            myStmt.setInt(1, theBook.getId());
            myStmt.setString(2, theBook.getNameFam());
            myStmt.setString(3, theBook.getNameFirst());
            myStmt.setString(4, theBook.getTitle());
            myStmt.setString(5, theBook.getPlacePubl());
            myStmt.setInt(6, theBook.getYearPubl());
            myStmt.setInt(7, theBook.getId());

            // execute SQL statement
            myStmt.execute();
        }
        finally {
            // clean up JDBC objects
            close(myConn, myStmt, null);
        }
    }

    // method DELETE BOOK
    public void deleteBook(String bookID) throws Exception {

        Connection myConn = null;
        PreparedStatement myStmt = null;

        try {
            // convert book id to int
            int book2delete_ID = Integer.parseInt(bookID);

            // get connection to database
            Context initCtx;
            initCtx = new InitialContext();
            Context envContext = (Context) initCtx.lookup("java:/comp/env");
            DataSource dataSource = (DataSource) envContext.lookup("jdbc/mybookshelf");
            myConn = dataSource.getConnection();

            // create sql to delete book
            String sql = "DELETE FROM books WHERE ID=?";

            // prepare statement
            myStmt = myConn.prepareStatement(sql);

            // set params
            myStmt.setInt(1, book2delete_ID);

            // execute sql statement
            myStmt.execute();
        }
        finally {
            // clean up JDBC code
            close(myConn, myStmt, null);
        }
    }

    // method GET BOOK
    public Book getBook(String theBookId) throws Exception {
        Book theBook = null;

        Connection myConn = null;
        PreparedStatement myStmt = null;
        ResultSet myRs = null;
        int bookId;

        try {
            bookId = Integer.parseInt(theBookId);

            // get connection to database
            // myConn = dataSource.getConnection();
            Context initCtx;
            initCtx = new InitialContext();
            Context envContext = (Context) initCtx.lookup("java:/comp/env");
            DataSource dataSource = (DataSource) envContext.lookup("jdbc/mybookshelf");
            myConn = dataSource.getConnection();

            // create sql to get selected student
            String sql = "SELECT * FROM Books WHERE ID=?";

            // create prepared statement
            myStmt = myConn.prepareStatement(sql);

            // set params
            myStmt.setInt(1, bookId);

            // execute statement
            myRs = myStmt.executeQuery();

            // retrieve data from result set row
            if (myRs.next()) {
                int id = myRs.getInt("ID");
                String famName = myRs.getString("Author\u0027s name");
                String fstName = myRs.getString("Author\u0027s first name");
                String title = myRs.getString("Title");
                String placePubl = myRs.getString("Place of publication");
                int yearPubl = myRs.getInt("Year of publication");

                // create new book object, using the retrieved data
                theBook = new Book(id, famName, fstName, title, placePubl, yearPubl);
            }
            else {
                throw new Exception("Could not find book id: " + bookId);
            }

            return theBook;
        }
        finally {
            close(myConn, myStmt, myRs);
        }
    }

}