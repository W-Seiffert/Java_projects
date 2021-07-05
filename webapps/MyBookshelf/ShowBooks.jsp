<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="project_books.*, java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>

<html>

  <head>
    <title>MyBookshelf - Result</title>
    <style type="text/css">
      table {
        margin: 0px auto;
        border: 2px solid black;
      }
      td {
        padding: 5px;
        text-align: left;
      }
      tr:nth-child(even) {
        background-color: aquamarine;
      }
      .myButton {
        padding: 10px 20px;
        border-radius: 12px;
        font-size: 1em;
      }
      .myButton:hover {
        box-shadow: 0 12px 12px 0 rgba(0,0,0,0.24), 0 17px 50px 0 rgba(0,0,0,0.19);   
      }
      .myButton_2 {
        background-color: lightyellow;
        padding: 10px 20px;
        border-radius: 12px;
        font-size: 1em;
      }
    </style>
  </head>

  <body style="text-align: center">

    <h1>MyBookshelf - a database for books</h1>
    <br/>
    <h2>The booklist you were asking for</h2>
    <br/>
    <table border="1" cellpadding="2" cellspacing="0" >

      <tr>
        <th>ID</th>
        <th>Author: name</th>
        <th>Author: 1st name</th>
        <th>Title</th>
        <th>Place of Publ.</th>
        <th>Date of Publ.</th>
        <th>Action</th>
      </tr>

      <!-- JSTL core tag, "forEach" -->
      <c:forEach var="tempBook" items="${BOOK_LIST}">

        <!-- set up a link for each book: JSTL core tag "url", with params ... -->
        <c:url var="tempLink" value="BooksServlet">
          <c:param name="command" value="LOAD" />
          <c:param name="ID" value="${tempBook.id}" />
        </c:url>

        <!--  set up a link to delete a book -->
        <c:url var="deleteLink" value="BooksServlet">
          <c:param name="command" value="DELETE" />
          <c:param name="ID" value="${tempBook.id}" />
        </c:url>

        <!-- the "tempBook" object is provided by the "get Books()" method of the class BookDbUtil -->
        <tr>
          <td>${tempBook.id}</td>
          <td>${tempBook.nameFam}</td>
          <td>${tempBook.nameFirst}</td>
          <td>${tempBook.title}</td>
          <td>${tempBook.placePubl}</td>
          <td>${tempBook.yearPubl}</td>
          <td>
            <a href="${tempLink}">Update</a>
             | 
            <a href="${deleteLink}" onclick="if (!(confirm('Are you sure you want to delete this book?'))) return false">Delete</a>
          </td>
        </tr>

      </c:forEach>

    </table>

    <br/>

    <input type="button" value="Add a new entry" onclick="window.location.href='AddBookForm.jsp'; return false;"
           class="myButton" />
    <br/><br/>
    <input type="button" value="Back to the Startpage" onclick="window.location.href='Index.jsp'; return false;"
           class="myButton_2" />

  </body>

</html>