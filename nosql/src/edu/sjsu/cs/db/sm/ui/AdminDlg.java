package edu.sjsu.cs.db.sm.ui;

import edu.sjsu.cs.db.sm.SM;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;


public class AdminDlg extends JDialog
{
    JPanel panel1 = new JPanel();
    private JLabel jLabel1 = new JLabel();
    private JTextField m_impl = new JTextField();
    private JButton jButton1 = new JButton();

    public AdminDlg ( Frame frame, String title, boolean modal )
    {
        super( frame, title, modal );
        try
        {
            jbInit();
            pack();

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public AdminDlg ()
    {
        this( null, "", false );
    }

    private void jbInit () throws Exception
    {
        panel1.setLayout( new ColumnLayout( 25, ColumnLayout.LAYOUT_CENTER ) );
        this.getContentPane().setLayout( new ColumnLayout( 25, ColumnLayout.LAYOUT_CENTER ) );//new ColumnLayout(ColumnLayout.LAYOUT_CENTER));
        jLabel1.setText( "Please, specify implementation class" );
        m_impl.setText( "edu.sjsu.cs.db.sm.SMImplVersion3" );
        jButton1.setText( "Connect" );
        jButton1.addActionListener( new java.awt.event.ActionListener()
        {
            public void actionPerformed ( ActionEvent e )
            {
                jButton1_actionPerformed( e );
            }
        } );
        getContentPane().add( panel1, null );
        panel1.add( jLabel1, null );
        panel1.add( m_impl, null );
        panel1.add( jButton1, null );
    }

    private void jButton1_actionPerformed ( ActionEvent e )
    {
        try
        {
            this.setCursor( Cursor.getPredefinedCursor( Cursor.WAIT_CURSOR ) );
            if ( m_impl == null || m_impl.getText().length() == 0 )
            {
                JOptionPane.showMessageDialog( this, "Invalid driver");
                return;
            }
            Class impl = Class.forName( m_impl.getText() );
            Object obj = impl.newInstance();
            if ( obj instanceof SM )
            {
                Env.getInstance().registerDriver( (SM)obj );
                this.dispose();
            }
            else
            {
                JOptionPane.showMessageDialog( this, "Invalid driver specified" );
            }
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog( this, "Error creating driver="+ ex.getMessage() );
        }
        finally
        {
            this.setCursor( Cursor.getPredefinedCursor( Cursor.DEFAULT_CURSOR) );
        }
    }


}