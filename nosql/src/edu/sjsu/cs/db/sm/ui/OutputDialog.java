
package edu.sjsu.cs.db.sm.ui;
import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

public class OutputDialog extends JDialog
{
	JPanel panel1 = new JPanel();
    JButton m_save = new JButton("Save");
    JButton m_cancel = new JButton("Cancel");
	JTextArea m_textArea = new JTextArea();
    private SMPanel panel;
    private boolean m_isChanged = false;
    private String m_originalString;
    private static final boolean DEBUG = true;

	 public OutputDialog(Frame frame, String title, boolean modal) {
    super(frame, title, modal);
    try {

      jbInit();
      pack();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

    public boolean isChanged()
    {
        return m_isChanged;
    }


	private void jbInit() throws Exception {
        this.setDefaultCloseOperation(
                JDialog.DO_NOTHING_ON_CLOSE );

    panel1.setLayout(new BorderLayout());
    this.getContentPane().setLayout( new BorderLayout());
//    panel1.setBounds(new Rectangle(10, 10, 400, 282));
    getContentPane().add(panel1, BorderLayout.CENTER);
    panel1.add(m_textArea, BorderLayout.CENTER);
    panel1.add(m_save, BorderLayout.SOUTH);
    panel1.add( m_cancel, BorderLayout.EAST );
        m_cancel.addActionListener( new ActionListener()
        {
            public void actionPerformed ( ActionEvent e )
            {
                cancelAction( e );
            }
        });
    m_textArea.setEditable(true);
        m_save.addActionListener( new java.awt.event.ActionListener()
        {
            public void actionPerformed ( ActionEvent e )
            {
                m_save_action( e );
            }
        } );

        m_textArea.getDocument().addDocumentListener( new DocumentListener()
        {
            public void insertUpdate ( DocumentEvent event )
            {
                if  (m_textArea.getText().equalsIgnoreCase( m_originalString ) == false )
                        m_isChanged = true;

            }

            public void removeUpdate ( DocumentEvent event )
            {
                if ( m_textArea.getText().equalsIgnoreCase( m_originalString ) == false )
                    m_isChanged = true;
                if ( DEBUG ) System.out.println( "remove" );
            }

            public void changedUpdate ( DocumentEvent event )
            {
                if ( DEBUG ) System.out.println( "changed" );
            }
        });
         this.addWindowListener( new WindowListener()
         {
             public void windowOpened ( WindowEvent event )
             {
             }

             public void windowClosing ( WindowEvent event )
             {
                 System.out.println( "Came here" );
                 if ( isChanged() )
                 {
                     JOptionPane.showMessageDialog( null, "Context changed, save first or cancel" );
                 }
                 else
                 {
                     dispose();
                 }

//                 System.out.println( "Windows is closing" );
             }

             public void windowClosed ( WindowEvent event )
             {
                 if ( DEBUG ) System.out.println( "Windows is closed" );
             }

             public void windowIconified ( WindowEvent event )
             {
             }

             public void windowDeiconified ( WindowEvent event )
             {
             }

             public void windowActivated ( WindowEvent event )
             {
             }

             public void windowDeactivated ( WindowEvent event )
             {
             }
         });

//        m_textArea.addInputMethodListener( new InputMethodListener()
//        {
//            public void inputMethodTextChanged ( InputMethodEvent event )
//            {
//                System.out.println( "Something changed by input" );
//            }
//
//            public void caretPositionChanged ( InputMethodEvent event )
//            {
//            }
//        });
  }

    private void cancelAction (ActionEvent e )
    {
        m_isChanged = false;
        this.dispose();
    }

    private void m_save_action( ActionEvent e  )
    {
        if ( getOutput().length() != 0 )
        {
            System.out.println( "Output="+getOutput()+" original="+m_originalString );
            this.dispose();
        }
    }

	public void setOutput(String _text )
	{
        m_originalString = _text;
        m_textArea.setText(_text );
	}

    public String getOutput()
    {
        return m_textArea.getText();
    }
}
