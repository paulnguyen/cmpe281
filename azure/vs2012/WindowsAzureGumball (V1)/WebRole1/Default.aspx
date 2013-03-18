<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="Default.aspx.cs" Inherits="WebRole1.Default" %>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title></title>
</head>
<body>
    <form id="form1" runat="server">
    <div style="text-align: center">
    
        <br />
        <asp:TextBox ID="TextBoxMessage" runat="server" Height="173px" Width="424px" 
            BorderColor="#999999" BorderWidth="2px" ReadOnly="True" TextMode="MultiLine" Rows="20">
        </asp:TextBox>
        <br />
        <br />
        <br />
        <asp:Image ID="Image1" runat="server" Height="372px" 
            ImageUrl="~/Images/giant-gumball-machine.jpg" Width="434px" />
        <br />
        <br />
        <br />
        <asp:Button ID="InsertQuaterButton" runat="server" Text="Insert Quarter" 
            OnClick="InsertQuaterButton_Click" />
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <asp:Button ID="TurnCrankButton" runat="server" Text="Turn Crank" 
            OnClick="TurnCrankButton_Click" />
        <br />
        <br />
    
    </div>
    </form>
</body>
</html>
