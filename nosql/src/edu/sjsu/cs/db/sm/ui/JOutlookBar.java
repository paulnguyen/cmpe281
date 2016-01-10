package edu.sjsu.cs.db.sm.ui;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class JOutlookBar extends JPanel implements ActionListener
{
	protected Vector buttons = new Vector ();
	protected Vector names = new Vector ();
	protected Vector views = new Vector ();

	public JOutlookBar ()
	{
		setLayout (new ContextLayout ());
		setBorder (new BevelBorder (BevelBorder.LOWERED));
		setPreferredSize (new Dimension (80, 80));
	}

	/**
	 * Class AppFrame implements interface ActionListener. Reference to that class is then inserted to
	 * outlook buttons and here is the code that is called when button is pressed.
	 * param		context - its forder (some call it context) name
	 * param		btnText - text for button
	 * param		image - button image URL
	 * param		action - ActionListener called by button when pressed
	 */
	public void addIcon (String context, String btnText, String image, ActionListener action)
	{
		int index;
		ImageIcon ikona = null;
		JPanel view;
		if ((index = names.indexOf (context)) > -1)
		{
			view = (JPanel) views.elementAt (index);
		}
		else
		{
			view = new JPanel ();
			view.setBackground (Color.gray);
			view.setLayout (new ListLayout ());
			names.addElement (context);
			views.addElement (view);
			addTab (context, new ScrollingPanel (view));
		}
		if (image != null)
		{
			java.net.URL url = getClass ().getResource (image);
			if (url != null)
				ikona = new ImageIcon (url);
		}
		RolloverButton button = new RolloverButton (btnText, (Icon) ikona);
		button.addActionListener (action);
		view.add (button);
		doLayout ();
	}

	public void setIndex (int index)
	{
		((ContextLayout) getLayout ()).setIndex (this, index);
	}

	public void addTab (String name, Component comp)
	{
		JButton button = new TabButton (name);
		add (button, comp);
		buttons.addElement (button);
		button.addActionListener (this);
	}

	public void removeTab (JButton button)
	{
		button.removeActionListener (this);
		buttons.removeElement (button);
		remove (button);
	}

	public void actionPerformed (ActionEvent event)
	{
		Object source = event.getSource ();
		for (int i = 0; i < buttons.size (); i++)
		{
			if (source == buttons.elementAt (i))
			{
				setIndex (i + 1);
				return;
			}
		}
	}
}