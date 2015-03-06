
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Welcome to the Gumball Machine (Groovy Version)</title>
</head>

<body>
<h1 align="center">Welcome to the Gumball Machine</h1>

<!-- FORM SECTION -->
<form name="form1" method="post" action="">
    <p>

    <div align="center">
        <textarea name="message" cols="50" rows="10" readonly id="message">
            ${flash.message}
        </textarea>
    </div>
</p>
    <p align="center"><img src="${createLinkTo(dir: 'images', file: 'giant-gumball-machine.jpg')}"  width="385" height="316"></p>

    <p align="center">
        <input type="submit" name="event" id="btnInsertQuarter" value="Insert Quarter">
        &nbsp;&nbsp;&nbsp;&nbsp;
        <input type="submit" name="event" id="btnTurnCrank" value="Turn Crank">
    </p>
</form>
<!-- END FORM SECTION -->

</body>
</html>