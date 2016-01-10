package edu.sjsu.cs.db.sm.ui;

import javax.swing.*;
import java.awt.*;

public class TabButton extends JButton
{
	public TabButton (String name)
	{
		super (name);
		setOpaque (true);
		setFocusPainted (false);
		setMargin (new Insets (2, 2, 2, 2));
		setMinimumSize (new Dimension (20, 20));
		setPreferredSize (new Dimension (20, 20));
	}

	public boolean isFocusTraversable ()
	{
		return false;
	}

	public boolean isDefaultButton ()
	{
		return false;
	}
}