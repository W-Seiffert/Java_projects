<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>

  <head>
    <title>Update Book Form</title>
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
      .myInput {
        width: 200px;
        font-family: 'Times New Roman';
        font-size: 14px;
      }
    </style>
  </head>

  <body style="text-align: center">

    <div id="form_title">
      <h2>Update a book ...</h2>
      <br/>
      <img src="images/books_with_labels.png" alt="Books with labels" width="300" height="237" />
      <br/><br/>
    </div>

    <div id="form_content">
      <h4>Please enter your changes in the form given below:</h4>

      <form method="GET" action="/MyBookshelf/BooksServlet">

        <input type="hidden" name="command" value="UPDATE" />

        <input type="hidden" name="id" value="${THE_BOOK.id}" />

        <!-- "THE_BOOK": attribute name set by the Servlet! -->
        <table>
          <tbody>

            <tr>
              <td><label>ID:</label></td>
              <td><input class="myInput" type="number" name="id" value="${THE_BOOK.id}" required /></td>
            </tr>

            <tr>
              <td><label>Family Name:</label></td>
              <td><input class="myInput" type="text" name="famName" value="${THE_BOOK.nameFam}" required /></td>
            </tr>

            <tr>
              <td><label>First Name:</label></td>
              <td><input class="myInput" type="text" name="fstName" value="${THE_BOOK.nameFirst}" required /></td>
            </tr>

            <tr>
              <td><label>Title:</label></td>
              <td><textarea class="myInput" name="title" required>"${THE_BOOK.title}"</textarea></td>
              <!-- <td><input type="text" name="title" value="${THE_BOOK.title}" /></td> -->
            </tr>

            <tr>
              <td><label>Place/Publ.:</label></td>
              <td><input class="myInput" type="text" name="placePubl" value="${THE_BOOK.placePubl}" required /></td>
            </tr>

            <tr>
              <td><label>Year/Publ.:</label></td>
              <td><input class="myInput" type="number" name="yearPubl" value="${THE_BOOK.yearPubl}" required maxlength="4" /></td>
            </tr>

          </tbody>
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