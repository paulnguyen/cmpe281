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
        GumballMachineRepository db = new GumballMachineRepository();

        String message = "";



        protected void Page_Load(object sender, EventArgs e)
        {
            GumballMachine gm = db.getBySerialNumber("1234998871109");
            message = "" +
                       "Mighty Gumball, Inc.\n" +
                       "Azure-enabled Standing Gumball\n" +
                       "Model# " + gm.modelNumber + "\n" +
                       "Serial# " + gm.serialNumber + "\n" +
                       "Inventory: " + gm.count + " gumballs\n";
            TextBoxMessage.Text = message;

        }

        protected void InsertQuaterButton_Click(object sender, EventArgs e)
        {
            TextBoxMessage.Text = message + "\n\nInsert Quarter Button Clicked!";
        }

        protected void TurnCrankButton_Click(object sender, EventArgs e)
        {
            TextBoxMessage.Text = message + "\n\nTurn Crank Button Clicked!";
            GumballMachine gm = db.getBySerialNumber("1234998871109");
            gm.count--;
            db.SaveChanges();

        }
    }
}