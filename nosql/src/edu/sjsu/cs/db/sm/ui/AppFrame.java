package edu.sjsu.cs.db.sm.ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.*;


public class AppFrame extends JFrame implements WindowListener, ActionListener
{
	JSplitPane sections = null;
    private boolean DEBUG = true;

	AppFrame( String as_Title )
	{
		super( as_Title );
		addWindowListener( this );
		JOutlookBar outlook = new JOutlookBar();
		outlook.addIcon( "Storage Manager", SM_OPERATIONS, "view.gif", this );
		sections = new JSplitPane( JSplitPane.HORIZONTAL_SPLIT, outlook, new JPanel() );
		sections.setDividerLocation( 120 );//150);
		sections.setOneTouchExpandable( true );
		getContentPane().add( sections );
		setSize( 640, 480 );
		setLocation( 100, 100 );
		setVisible( true );
	}

	public void windowActivated( WindowEvent e )
	{
	}

	public void windowClosing( WindowEvent e )
	{
        try
        {
            if ( DEBUG ) System.out.println( "Closing1 SM..." );
            Env.getInstance().getRegisteredDriver().close();
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog( this, "Error=" + ex.getMessage() );
        }
        System.exit( 0 );
	}

	public void windowClosed( WindowEvent e )
	{
//        try
//        {
////            System.out.println( "Closing SM..." );
//            Env.getInstance().getRegisteredDriver().close();
//        }
//        catch( Exception ex )
//        {
//            JOptionPane.showMessageDialog( this, "Error=" + ex.getMessage() );
//        }
//        System.exit( 0 );
	}

	public void windowDeactivated( WindowEvent e )
	{
	}

	public void windowDeiconified( WindowEvent e )
	{
	}

	public void windowIconified( WindowEvent e )
	{
	}

	public void windowOpened( WindowEvent e )
	{
	}

	public void actionPerformed( ActionEvent action )
	{
		if ( action.getActionCommand().equalsIgnoreCase( SM_OPERATIONS ) )
		{
			listOperations();
		}
	}


	private void listOperations ()
	{
		if ( sections.getBottomComponent() instanceof JSplitPane )
		{
			sections.remove( sections.getBottomComponent());
		}
        SMPanel treePanel = new SMPanel(this);
        contextPane = new JSplitPane( JSplitPane.VERTICAL_SPLIT, new JScrollPane( treePanel ), new JPanel() );
        Dimension dim = sections.getSize();
        int half = new Double( dim.getHeight() / 2 ).intValue()+100;
        contextPane.setDividerLocation( half );
        JScrollPane scrollPane = new JScrollPane( treePanel.getTextArea() );
        contextPane.setBottomComponent( scrollPane );
        sections.setBottomComponent( contextPane );
        sections.setDividerLocation( 120 );//150);
        contextPane.updateUI();
	}

	private static final String SM_OPERATIONS = "SM Operations";
	private JSplitPane contextPane;
}