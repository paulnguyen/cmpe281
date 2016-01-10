package edu.sjsu.cs.db.sm.ui;

import edu.sjsu.cs.db.sm.RecordFactory;
import edu.sjsu.cs.db.sm.SM;
import edu.sjsu.cs.db.sm.Util;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;

public class SMPanel extends JPanel
{

    public SMPanel ( Frame parent )
    {
        try
        {
            m_parent = parent;
            DefaultMutableTreeNode root = new DefaultMutableTreeNode( "ROOT" );

            m_tree.setModel( new DefaultTreeModel( root ) );
            m_tree.getSelectionModel().setSelectionMode
                    ( TreeSelectionModel.SINGLE_TREE_SELECTION );
            m_tree.addTreeSelectionListener( new TreeSelectionListener()
            {
                public void valueChanged ( TreeSelectionEvent e )
                {
                    treeSelectionEvent( e );
                }
            } );
            m_tree.addMouseListener( new MouseAdapter()
            {
                public void mouseClicked ( MouseEvent e )
                {
                    tree_mouseClicked( e );
                }
            } );
            this.setLayout( new BorderLayout() );
            this.add( new JLabel( SM_OPERATIONS ), BorderLayout.NORTH );
            this.add( m_tree );
            m_outputArea.setBackground( Color.white );
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog( this, e.getMessage() );
        }
    }

    private void tree_mouseClicked ( MouseEvent e )
    {
        JPopupMenu l_jPopupMenu = new JPopupMenu();
        if ( SwingUtilities.isRightMouseButton( e ) )
        {
            JMenuItem l_create = new JMenuItem( CREATE_LABEL );
            JMenuItem l_delete = new JMenuItem( DELETE_LABEL );
            JMenuItem l_update = new JMenuItem( UPDATE_LABEL );
            JMenuItem l_fetch = new JMenuItem( FETCH_LABEL );
            l_jPopupMenu.add( l_create );
            l_jPopupMenu.add( l_delete );
            l_jPopupMenu.add( l_update );
            l_jPopupMenu.add( l_fetch );
            l_jPopupMenu.show( ( JComponent ) e.getSource(), e.getX(), e.getY() );
            l_create.addActionListener( new java.awt.event.ActionListener()
            {
                public void actionPerformed ( ActionEvent e )
                {
                    l_create_actionPerformed( e );
                }
            } );
            l_delete.addActionListener( new java.awt.event.ActionListener()
            {
                public void actionPerformed ( ActionEvent e )
                {
                    l_delete_actionPerformed( e );
                }
            } );
            l_update.addActionListener( new java.awt.event.ActionListener()
            {
                public void actionPerformed ( ActionEvent e )
                {
                    l_load_actionPerformed( e );
                }
            } );
            l_fetch.addActionListener( new java.awt.event.ActionListener()
            {
                public void actionPerformed ( ActionEvent e )
                {
                    l_fetch_actionPerformed( e );
                }
            } );

        }
        super.processMouseEvent( e );
    }

    private void l_load_actionPerformed ( ActionEvent e )
    {

        if ( m_currentPath != null && m_currentPath.length() != 0 && m_currentPath != ROOT_LABEL )
        {
            if ( DEBUG ) System.out.println( "CurrentPath=" + m_currentPath );
            SM.OID oid = ( SM.OID ) oidList.get( m_currentPath );
            try
            {
                SM.Record record = Env.getInstance().getRegisteredDriver().fetch( oid );
//                    getTextArea().removeAll();
                String result = new String( record.getBytes( 0, ( int ) record.length() ) );
                OutputDialog outOutputDialog = new OutputDialog( m_parent, "Update", true );
                outOutputDialog.setOutput( result );
                outOutputDialog.setBounds( 0, 0, 300, 200 );
                outOutputDialog.show();
                if ( outOutputDialog.isChanged() )
                {
                    String updatedString = outOutputDialog.getOutput();
                    if ( DEBUG ) System.out.println( "Updated string=" + updatedString );
                    SM.Record updatedRecord = RecordFactory.getRecord( updatedString.length() );
                    updatedRecord.setBytes( updatedString.getBytes() );
                    SM.OID newOID = Env.getInstance().getRegisteredDriver().update( ( SM.OID ) oidList.get( m_currentPath ), updatedRecord );
                    oidList.remove( m_currentPath );
                    oidList.put( Util.toHexString( newOID.toBytes() ), newOID );
                    buildTree();
                    m_outputArea.doLayout();
                    m_outputArea.updateUI();
                }
            }
            catch (Exception ex)
            {
                JOptionPane.showMessageDialog( this, "Error=" + ex.getMessage() );
            }
        }
    }

    private void l_fetch_actionPerformed ( ActionEvent e )
    {
        if ( m_currentPath != null && m_currentPath.length() != 0 && m_currentPath != ROOT_LABEL )
        {
            if (DEBUG ) System.out.println( "CurrentPath=" + m_currentPath );
            SM.OID oid = ( SM.OID ) oidList.get( m_currentPath );
            try
            {
                SM.Record record = Env.getInstance().getRegisteredDriver().fetch( oid );
                getTextArea().removeAll();
                String result = new String( record.getBytes( 0, ( int ) record.length() ) );
                getTextArea().add( new JLabel( result ) );
                m_outputArea.doLayout();
                m_outputArea.updateUI();
            }
            catch (Exception ex)
            {
                JOptionPane.showMessageDialog( this, "Error=" + ex.getMessage() );
            }
        }
    }


    private void l_delete_actionPerformed ( ActionEvent e )
    {
        if ( DEBUG ) System.out.println( "CurrentPath=" + m_currentPath );
        try
        {
            if ( m_currentPath != null && m_currentPath.length() != 0 && m_currentPath != ROOT_LABEL )
            {
                Env.getInstance().getRegisteredDriver().delete( ( SM.OID ) oidList.get( m_currentPath ) );
                oidList.remove( m_currentPath );
                buildTree();
                m_outputArea.removeAll();
                m_tree.repaint();
                m_currentPath = null;
            }
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog( this, "Error=" + ex.getMessage() );
        }
    }

    private void l_create_actionPerformed ( ActionEvent e )
    {
//        if ( m_currentPath != null )
//        {
        try
        {
            OutputDialog outputDialog = new OutputDialog( m_parent, "Please enter data", true );
            outputDialog.setBounds( 0, 0, 300, 200 );
            outputDialog.show();
            if ( outputDialog.isChanged() )
            {
                SM.Record record = RecordFactory.getRecord( outputDialog.getOutput().length() );
                record.setBytes( outputDialog.getOutput().getBytes() );
                SM.OID oid = Env.getInstance().getRegisteredDriver().store( record );
                oidList.put( Util.toHexString( oid.toBytes() ), oid );
                buildTree();
            }
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog( this, "Error=" + ex.getMessage() );
            ex.printStackTrace();
        }
    }

    private void buildTree ()
            throws Exception
    {
        DefaultMutableTreeNode tree = new DefaultMutableTreeNode( ROOT_LABEL );
        Iterator it = oidList.keySet().iterator();
        while ( it.hasNext() )
        {
            String value = ( String ) it.next();
            tree.add( new DefaultMutableTreeNode( value ) );
        }
        m_tree.setModel( new DefaultTreeModel( tree ) );


    }

    private void treeSelectionEvent ( TreeSelectionEvent _e )
    {
        TreePath currentPath = _e.getNewLeadSelectionPath();
        if ( currentPath != null )
        {
            String fullPath = getCurrentPath( currentPath.toString() );
            m_currentPath = fullPath;
            try
            {
                if ( m_currentPath != null && m_currentPath.length() != 0 && m_currentPath.equalsIgnoreCase( ROOT_LABEL ) != true )
                {
                    l_fetch_actionPerformed( null );
//				    m_outputArea.doLayout();
//				    m_outputArea.updateUI();
                }
            }
            catch (Exception e)
            {
                JOptionPane.showMessageDialog( this, e.getMessage() );
            }
        }
    }

    private String getCurrentPath ( String _rawPath )
    {
        _rawPath = _rawPath.substring( 1, _rawPath.length() - 1 );
        StringTokenizer st = new StringTokenizer( _rawPath, "," );
        StringBuffer fullPath = new StringBuffer();
        while ( st.hasMoreTokens() )
        {
            String token = st.nextToken().trim();
            if ( token.equalsIgnoreCase( ROOT_LABEL ) == false )
            {
                fullPath.append( token ).append( "" );
            }
            else
            {
                fullPath.append( "" );
            }
        }
        return fullPath.toString();
    }


    public JPanel getTextArea ()
    {
        return m_outputArea;
    }

    private static final String SM_OPERATIONS = "SM Operations   ";
    private static final String CREATE_LABEL = "Create";
    private static final String DELETE_LABEL = "Delete";
    private static final String UPDATE_LABEL = "Update";
    private static final String FETCH_LABEL = "Fetch";
    private static final String ROOT_LABEL = "Root";
    private JTree m_tree = new JTree();
    private String m_currentPath;
    private JPanel m_outputArea = new JPanel( new ColumnLayout() );
    private Frame m_parent;
    private HashMap oidList = new HashMap();
    private boolean DEBUG = false;
}
