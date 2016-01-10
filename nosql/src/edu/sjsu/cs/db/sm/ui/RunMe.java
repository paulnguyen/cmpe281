package edu.sjsu.cs.db.sm.ui;

import edu.sjsu.cs.db.sm.ui.AppFrame;

import javax.swing.*;

public class RunMe
{
	public static AppFrame mainframe;

	public static void main (String[] args)
	{
        AdminDlg dialog = new AdminDlg( null, "Login", true );
        for ( ;; )
        {
            try
            {
                UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
                dialog.setBounds( 0, 0, 300, 200 );
                dialog.show();
            }
            catch (Exception e)
            {
                e.printStackTrace ();
            }
            if ( Env.getInstance().isDriverRegistered() )
            {
                mainframe = new AppFrame( "SM Storage Manager" );
                break;
            }
        }
    }

}