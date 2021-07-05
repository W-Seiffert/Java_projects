<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>

  <head>
    <title>Add Book Form</title>
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
    </style>
  </head>

  <body style="text-align: center">

    <div id="form_title">
      <h2>Add a new book to the bookshelf!</h2>
      <br/>
      <img src="images/book_orange.png" alt="Orange Book" width="300" height="212" />
      <br/><br/>
    </div>

    <div id="form_content">

      <h4>Please enter the required data here:</h4>
      <br/>

      <form method="GET" action="/MyBookshelf/BooksServlet">

        <input type="hidden" name="command" value="ADD" />

        <table>

          <tr>
            <td>ID: </td>
            <td><input type="number" name="ID" required /></td>
          </tr>

          <tr>
            <td>Family Name: </td>
            <td><input type="text" name="fam.name" required /></td>
          </tr>

          <tr>
            <td>First Name: </td>
            <td><input type="text" name="fst.name" required /></td>
          </tr>

          <tr>
            <td>Title: </td>
            <td><input type="text" name="title" required /></td>
          </tr>

          <tr>
            <td>Place/Publ.: </td>
            <td><input type="text" name="place.publ" required /></td>
          </tr>

          <tr>
            <td>Year/Publ.: </td>
            <td><input type="number" name="year.publ" required maxlength="4" /></td>
          </tr>

        </table>
        <br/>
        <input type="submit" value="Save" class="myButton" />
      </form>

      <br/>
      <p>
        <a href="BooksServlet">&#x21e8; &nbsp; Back to the Bookshelf! &nbsp; &#x21e6;</a>
      </p>

    </div>

  </body>

</html>