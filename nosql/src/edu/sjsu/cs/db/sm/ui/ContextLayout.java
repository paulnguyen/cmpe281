package edu.sjsu.cs.db.sm.ui;

import java.awt.*;
import java.io.Serializable;
import java.util.Vector;

public class ContextLayout extends AbstractLayout implements LayoutManager2, Serializable
{
	protected Vector tabs = new Vector ();
	protected Vector panels = new Vector ();
	protected Component center;
	protected int index = 1;

	/**
	 * Constructs a ContextLayout with no gaps between components.
	 **/
	public ContextLayout ()
	{
		super ();
	}

	/**
	 * Constructs a ContextLayout with the specified gaps.
	 * @param hgap The horizontal gap
	 * @param vgap The vertical gap
	 **/
	public ContextLayout (int hgap, int vgap)
	{
		super (hgap, vgap);
	}

	public void setIndex (Container parent, int index)
	{
		this.index = index;
		layoutContainer (parent);
	}

	/**
	 * Adds the specified component to the layout, using the
	 * specified constraint object.
	 * @param comp The component to be added
	 * @param name The name of the component position
	 **/
	public void addLayoutComponent (Component tab, Object panel)
	{
		if (panel == null) return;
		tabs.addElement (tab);
		panels.addElement (panel);
	}

	/**
	 * Removes the specified component from the layout.
	 * @param comp The component to be removed
	 **/
	public void removeLayoutComponent (Component comp)
	{
		for (int i = 0; i < tabs.size (); i++)
		{
			if (tabs.elementAt (i) == comp)
			{
				tabs.removeElementAt (i);
				panels.removeElementAt (i);
				return;
			}
		}
	}

	/**
	 * Returns the minimum dimensions needed to layout the
	 * components contained in the specified target container.
	 * @param target The Container on which to do the layout
	 **/
	public Dimension minimumLayoutSize (Container target)
	{
		Insets insets = target.getInsets ();
		Dimension tab = getMinimumTabSize ();
		int h = tab.height * (tabs.size () + 1) + (tabs.size () * hgap);
		return new Dimension (tab.width + insets.left + insets.right + (hgap * 2), h + insets.top + insets.bottom);
	}

	/**
	 * Returns the preferred dimensions for this layout given the
	 * components in the specified target container.
	 * @param target The component which needs to be laid out
	 **/
	public Dimension preferredLayoutSize (Container target)
	{
		Insets insets = target.getInsets ();
		Dimension tab = getPreferredTabSize ();
		int h = tab.height * (tabs.size () + 1) + (tabs.size () * hgap);
		return new Dimension (tab.width + insets.left + insets.right + (hgap * 2), h + insets.top + insets.bottom);
	}

	/**
	 * Lays out the specified container. This method will actually
	 * reshape the components in the specified target container in
	 * order to satisfy the constraints of the layout object.
	 * @param target The component being laid out
	 **/
	public void layoutContainer (Container target)
	{
		Dimension size = getPreferredTabSize ();
		layoutTabs (target, index, size, target.getSize ());
		layoutCenter (target, index, size, target.getSize ());
	}

	private Dimension getPreferredTabSize ()
	{
		int w = 0;
		int h = 0;
		Dimension size;
		Component comp;
		for (int i = 0; i < tabs.size (); i++)
		{
			comp = (Component) tabs.elementAt (i);
			size = comp.getPreferredSize ();
			if (size.width > w) w = size.width;
			if (size.height > h) h = size.height;
		}
		return new Dimension (w, h);
	}

	private Dimension getMinimumTabSize ()
	{
		int w = 0;
		int h = 0;
		Component comp;
		Dimension size;
		for (int i = 0; i < tabs.size (); i++)
		{
			comp = (Component) tabs.elementAt (i);
			size = comp.getMinimumSize ();
			if (size.width > w) w = size.width;
			if (size.height > h) h = size.height;
		}
		return new Dimension (w, h);
	}

	private void layoutCenter (Container cont, int index, Dimension size, Dimension parent)
	{
		Insets insets = cont.getInsets ();
		int top = size.height * index + insets.top + 1 + vgap * index + vgap;
		int h = parent.height - (size.height + vgap) * tabs.size () - insets.top - insets.bottom - 2 - vgap * 2;

		if (center != null) cont.remove (center);
		center = (Component) panels.elementAt (index - 1);

		center.setBounds (insets.left + 1 + hgap, top, parent.width - insets.left - insets.right - 2 - (hgap * 2), h);
		cont.add (center);
		center.paintAll (center.getGraphics ());
	}

	private void layoutTabs (Container cont, int index, Dimension size, Dimension parent)
	{
		Insets insets = cont.getInsets ();
		Component comp;
		// Top tabs
		int top = insets.top + 1 + vgap;
		for (int i = 0; i < index; i++)
		{
			comp = (Component) tabs.elementAt (i);
			comp.setBounds (insets.left + 1 + hgap, top, parent.width - insets.left - insets.right - 2 - (hgap * 2), size.height);
			top += size.height + vgap;
		}
		// Bottom tabs
		top = parent.height - insets.bottom - 1 - (size.height + vgap) * (tabs.size () - index);
		for (int i = index; i < tabs.size (); i++)
		{
			comp = (Component) tabs.elementAt (i);
			comp.setBounds (insets.left + 1 + hgap, top, parent.width - insets.left - insets.right - 2 - (hgap * 2), size.height);
			top += size.height + vgap;
		}
	}

}