
package edu.sjsu.cs.db.sm.ui;

import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ScrollingPanel extends JPanel implements ActionListener
{
	protected JButton north, south;
	protected JViewport viewport;
	protected int incr = 64;

	public ScrollingPanel (Component component)
	{
		setLayout (new BorderLayout ());
		north = new BasicArrowButton (BasicArrowButton.NORTH);
		south = new BasicArrowButton (BasicArrowButton.SOUTH);
		viewport = new JViewport ();
		add ("Center", viewport);
		viewport.setView (component);
		north.addActionListener (this);
		south.addActionListener (this);
	}

	public void setBounds (int x, int y, int w, int h)
	{
		super.setBounds (x, y, w, h);
		Dimension view = new Dimension (w, h);
		Dimension pane = viewport.getView ().getPreferredSize ();
		viewport.setViewPosition (new Point (0, 0));
		remove (north);
		if (pane.height >= view.height)
		{
			add ("South", south);
		}
		else
		{
			remove (south);
		}
		doLayout ();
	}

	public void actionPerformed (ActionEvent event)
	{
		Dimension view = getSize ();
		Dimension pane = viewport.getView ().getPreferredSize ();
		Point top = viewport.getViewPosition ();
		if (event.getSource () == north)
		{
			if (pane.height > view.height)
				add ("South", south);
			if (top.y < incr)
			{
				viewport.setViewPosition (new Point (0, 0));
				remove (north);
			}
			else
			{
				viewport.setViewPosition (new Point (0, top.y - incr));
			}
			doLayout ();
		}
		if (event.getSource () == south)
		{
			if (pane.height > view.height)
				add ("North", north);
			int max = pane.height - view.height;
			if (top.y > (max - incr))
			{
				remove (south);
				doLayout ();
				view = viewport.getExtentSize ();
				max = pane.height - view.height;
				viewport.setViewPosition (new Point (0, max));
			}
			else
			{
				viewport.setViewPosition (new Point (0, top.y + incr));
			}
			doLayout ();
		}
	}
}