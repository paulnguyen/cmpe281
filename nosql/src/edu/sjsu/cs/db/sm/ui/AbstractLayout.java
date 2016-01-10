
package edu.sjsu.cs.db.sm.ui;

import java.awt.*;
import java.io.Serializable;

public abstract class AbstractLayout implements LayoutManager2, Serializable
{
	protected int hgap;
	protected int vgap;

	public AbstractLayout ()
	{
		this (0, 0);
	}

	public AbstractLayout (int hgap, int vgap)
	{
		setHgap (hgap);
		setVgap (vgap);
	}

	/**
	 * Get the horizontal gap between components.
	 **/
	public int getHgap ()
	{
		return hgap;
	}

	/**
	 * Get the vertical gap between components.
	 **/
	public int getVgap ()
	{
		return vgap;
	}

	/**
	 * Set the horizontal gap between components.
	 * @param gap The horizontal gap to be set
	 **/
	public void setHgap (int gap)
	{
		hgap = gap;
	}

	/**
	 * Set the vertical gap between components.
	 * @param gap The vertical gap to be set
	 **/
	public void setVgap (int gap)
	{
		vgap = gap;
	}

	/**
	 * Returns the maximum dimensions for this layout given
	 * the component in the specified target container.
	 * @param target The component which needs to be laid out
	 **/
	public Dimension maximumLayoutSize (Container target)
	{
		return new Dimension (Integer.MAX_VALUE, Integer.MAX_VALUE);
	}

	/**
	 * Returns the alignment along the x axis. This specifies how
	 * the component would like to be aligned relative to other
	 * components. The value should be a number between 0 and 1
	 * where 0 represents alignment along the origin, 1 is aligned
	 * the furthest away from the origin, 0.5 is centered, etc.
	 **/
	public float getLayoutAlignmentX (Container parent)
	{
		return 0.5f;
	}

	/**
	 * Returns the alignment along the y axis. This specifies how
	 * the component would like to be aligned relative to other
	 * components. The value should be a number between 0 and 1
	 * where 0 represents alignment along the origin, 1 is aligned
	 * the furthest away from the origin, 0.5 is centered, etc.
	 **/

	public float getLayoutAlignmentY (Container parent)
	{
		return 0.5f;
	}

	/**
	 * Invalidates the layout, indicating that if the layout
	 * manager has cached information it should be discarded.
	 **/
	public void invalidateLayout (Container target)
	{
	}

	/**
	 * Adds the specified component with the specified name
	 * to the layout. By default, we call the more recent
	 * addLayoutComponent method with an object constraint
	 * argument. The name is passed through directly.
	 * @param name The name of the component
	 * @param comp The component to be added
	 **/
	public void addLayoutComponent (String name, Component comp)
	{
		addLayoutComponent (comp, name);
	}

	/**
	 * Add the specified component from the layout.
	 * By default, we let the Container handle this directly.
	 * @param comp The component to be added
	 * @param constraints The constraints to apply when laying out.
	 **/
	public void addLayoutComponent (Component comp, Object constraints)
	{
	}

	/**
	 * Removes the specified component from the layout.
	 * By default, we let the Container handle this directly.
	 * @param comp the component to be removed
	 **/
	public void removeLayoutComponent (Component comp)
	{
	}

	/**
	 * Return a string representation of the layout manager
	 **/
	public String toString ()
	{
		return getClass ().getName () + "[hgap=" + hgap + ",vgap=" + vgap + "]";
	}
}