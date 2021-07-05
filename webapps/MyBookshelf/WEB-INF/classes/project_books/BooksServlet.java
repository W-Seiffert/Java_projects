package project_books;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.sql.DataSource;

import javax.naming.Context;
import javax.naming.InitialContext;

public class BooksServlet extends HttpServlet {

    private BookDbUtil bookDbUtil;
    Connection myConn = null;

    /*
    // using the @Resource annotation for Resource Injection
    @Resource(name="jdbc/mybookshelf")
    private DataSource dataSource;
    */

    @Override
    public void init() throws ServletException {
        super.init();

        try {
            Context initCtx;
            initCtx = new InitialContext();
            Context envContext = (Context) initCtx.lookup("java:/comp/env");
            DataSource dataSource = (DataSource) envContext.lookup("jdbc/mybookshelf");
            myConn = dataSource.getConnection();
            bookDbUtil = new BookDbUtil(dataSource);
        }
        catch (Exception exc) {
            throw new ServletException(exc);
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException {

        try {
            // read the parameter "command"
            String theCommand = request.getParameter("command");
            // if the command is missing, set default to listing the books
            if(theCommand == null) {
                theCommand = "LIST";
            }
            // route to the appropriate method
            switch(theCommand) {

                case "LIST":
                    listBooks(request, response);
                    break;

                case "ADD":
                    addBooks(request, response);
                    break;

                case "LOAD":
                    loadBook(request, response);
                    break;

                case "UPDATE":
                    updateBook(request, response);
                    break;

                case "DELETE":
                    deleteBook(request, response);
                    break;

                default:
                    listBooks(request, response);
            }
        }
        catch(Exception exc) {
            throw new ServletException(exc);
        }

        /*
        try
        {
        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/MyBookshelf?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "1356=Goldne-Bull");
        stmt = conn.createStatement();

        int id = Integer.parseInt(request.getParameter("ID"));
        String famName = request.getParameter("fam.name");
        String fstName = request.getParameter("fst.name");
        String title = request.getParameter("title");
        String placePubl = request.getParameter("place.publ");
        int yearPubl = Integer.parseInt(request.getParameter("year.publ"));

        stmt.executeUpdate("INSERT INTO Books(ID, `Author’s name`, `Author’s first name`, Title, `Place of publication`, `Date of publication`) VALUES (" + id + ", '" + famName + "', '" + fstName + "', '" + title + "', '" + placePubl + "', " + yearPubl + ")");

        stmt.close();
        conn.close();
        }
        catch(Exception ex)
        {
            response.sendRedirect("/MyBookshelf/Error.jsp");
            return;
        }

        response.sendRedirect("/MyBookshelf/GetBookshelf");
        return; 
        */
    }

    private void listBooks(HttpServletRequest request, HttpServletResponse response)
    throws Exception {

        // get books from the BooksDbUtil
        List<Book> books = bookDbUtil.getBooks();

        // add books to the request (name + object)
        request.setAttribute("BOOK_LIST", books);

        // send the request to the JSP page
        RequestDispatcher dispatcher = request.getRequestDispatcher("/ShowBooks.jsp");
        dispatcher.forward(request, response);
    }

    private void addBooks(HttpServletRequest request, HttpServletResponse response)
    throws Exception {

        // read book info from the submitted form
        int id = Integer.parseInt(request.getParameter("ID"));
        String famName = request.getParameter("fam.name");
        String fstName = request.getParameter("fst.name");
        String title = request.getParameter("title");
        String placePubl = request.getParameter("place.publ");
        int yearPubl = Integer.parseInt(request.getParameter("year.publ"));

        // create a new book object
        Book theBook = new Book(id, famName, fstName, title, placePubl, yearPubl);

        // add the book to the database
        bookDbUtil.addBook(theBook);

        // send the augmented list back to the presentation page
        listBooks(request, response);
    }

    private void deleteBook(HttpServletRequest request, HttpServletResponse response)
    throws Exception {

        // get the book ID
        String bookID = request.getParameter("ID");

        // delete the book from the database
        bookDbUtil.deleteBook(bookID);

        // send the reduced list back to the presentation page
        listBooks(request, response);
    }

    private void updateBook(HttpServletRequest request, HttpServletResponse response)
    throws Exception {

        // get the book info - from the form of UpdateBookForm.jsp!
        int id = Integer.parseInt(request.getParameter("id"));
        String famName = request.getParameter("famName");
        String fstName = request.getParameter("fstName");
        String title = request.getParameter("title");
        String placePubl = request.getParameter("placePubl");
        int yearPubl = Integer.parseInt(request.getParameter("yearPubl"));

        // create a new book object
        Book theBook = new Book(id, famName, fstName, title, placePubl, yearPubl);

        // perform the update on the database
        bookDbUtil.updateBook(theBook);

        // send the updated list back to the presentation page
        listBooks(request, response);
    }

    private void loadBook(HttpServletRequest request, HttpServletResponse response)
    throws Exception {

        // read book id from form data
        String bookID = request.getParameter("ID");

        // get book from database (db util)
        Book theBook = bookDbUtil.getBook(bookID);

        // place book in the request attribute
        request.setAttribute("THE_BOOK", theBook);

        // send to jsp page: UpdateBookForm.jsp
        RequestDispatcher dispatcher = request.getRequestDispatcher("/UpdateBookForm.jsp");
        dispatcher.forward(request, response);
    }

}