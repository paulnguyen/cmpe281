<html>
  <body bgcolor="white">
    <div style="font-size: 150%; color: #850F0F">
      <span>Enter your name: </span><br />
      <form method="post" action="hello">
        <input type=text size="15" name="user" >
        <input type=submit name="submit" value="Ok">
      </form>
    </div>
    <div>
      <%
          {
            java.lang.String greeting = (java.lang.String)request.getAttribute("greeting");   
            java.lang.String message= (java.lang.String)request.getAttribute("message");   
      %>
      <span><%=greeting%></span>
      <br/>
      <span><%=message%></span>
      <%
          }
      %>
    </div>
  </body>
</html>
