
<html>
<head>
    <title></title>
</head>
<body>
<form name="form1" method="post" action="">
  <p>
  <div align="center">
      <textarea name="txt" cols="50" rows="10" readonly id="txt">
        
       <%
          {  
            java.lang.String message= (java.lang.String)request.getAttribute("message");   
       %>
         <%=message%>
       <%
          }
       %>
        
      </textarea>
    </div>
  </p>
  <p align="center"><img src="/images/giant-gumball-machine.jpg" width="385" height="316"></p>
  <p align="center">
    <input type="submit" name="btnInsert" id="btnInsert" value="Insert Quarter">
    &nbsp;&nbsp;&nbsp;&nbsp;
    <input type="submit" name="btnCrank" id="btnCrank" value="Turn Crank">
  </p>
</form>
</body>
</html>