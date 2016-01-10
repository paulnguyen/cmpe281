package edu.sjsu.cs.db.sm.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class RolloverButton extends JButton implements MouseListener
{

	public RolloverButton (String name)
	{
		this (name, null);
	}

	public RolloverButton (String name, Icon icon)
	{
		super (name);
		if (icon == null)
		{
			java.net.URL url = getClass ().getResource ("button.gif");
			if (url != null)
				icon = new ImageIcon (url);
		}
		this.setIcon (icon);
		setOpaque (true);
		setBackground (Color.gray);
		setForeground (Color.white);
		setMargin (new Insets (2, 2, 2, 2));
		setBorderPainted (false);
		setFocusPainted (false);
		setVerticalAlignment (TOP);
		setHorizontalAlignment (CENTER);
		setVerticalTextPosition (BOTTOM);
		setHorizontalTextPosition (CENTER);
		addMouseListener (this);
	}

	public boolean isDefaultButton ()
	{
		return false;
	}

	public void mouseEntered (MouseEvent event)
	{
		setBorderPainted (true);
		setForeground (Color.black);
		setBackground (Color.lightGray);
		repaint ();
	}

	public void mouseExited (MouseEvent event)
	{
		setBorderPainted (false);
		setForeground (Color.white);
		setBackground (Color.gray);
		repaint ();
	}

	public void mouseEnter (MouseEvent event)
	{
	}

	public void mousePressed (MouseEvent event)
	{
	}

	public void mouseClicked (MouseEvent event)
	{
	}

	public void mouseReleased (MouseEvent event)
	{
	}

}