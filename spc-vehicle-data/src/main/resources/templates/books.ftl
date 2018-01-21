<!DOCTYPE html>
<#import "/spring.ftl" as spring/>
<html lang="en">
    <head>
      <title>See all books</title>
      <link rel="stylesheet"
           type="text/css" href="<@spring.url '/css/style.css'/>"/>
    </head>
<body>

<h1>Books in the database</h1>
<table border="1">
<tr>
    <th>Author</th>
    <th>Book</th>
</tr>
<#list books as book>
<tr>
    <td>${book.author}</td>
    <td>${book.title}</td>
</tr>
</#list>
</table>

</body>

</html>