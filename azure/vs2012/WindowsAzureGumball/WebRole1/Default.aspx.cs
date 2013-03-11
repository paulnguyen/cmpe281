using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace WebRole1
{
    public partial class Default : System.Web.UI.Page
    {

        String message = "" +
            "Mighty Gumball, Inc.\n" +
            "Azure-enabled Standing Gumball\n" +
            "Model# 12345AJBCK0981\n" +
            "Serial# 1234998871109\n" +
            "Inventory: 50 gumballs\n";

        protected void Page_Load(object sender, EventArgs e)
        {
            TextBoxMessage.Text = message;
        }

        protected void InsertQuaterButton_Click(object sender, EventArgs e)
        {
            TextBoxMessage.Text = message + "\n\nInsert Quarter Button Clicked!";
        }

        protected void TurnCrankButton_Click(object sender, EventArgs e)
        {
            TextBoxMessage.Text = message + "\n\nTurn Crank Button Clicked!";
        }
    }
}