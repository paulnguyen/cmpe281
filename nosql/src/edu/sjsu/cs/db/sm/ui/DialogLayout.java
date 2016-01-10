package edu.sjsu.cs.db.sm.ui;


import java.awt.*;
import java.util.*;

import javax.swing.*;

/**
 *  Layout which manages text boxes, panels, and buttons.
 */
public class DialogLayout
	implements LayoutManager {

	protected static final int COMP_TWO_COL = 0;
	protected static final int COMP_BIG = 1;
	protected static final int COMP_BUTTON = 2;

	protected int m_divider = -1;
	protected int m_hGap = 10;
	protected int m_vGap = 5;
	protected Vector m_v = new Vector();

	/**
	 *  Constructor
	 */
	public DialogLayout() {}

	/**
	 *  Constructor
	 */
	public DialogLayout(int hGap, int vGap) {
		m_hGap = hGap;
		m_vGap = vGap;
	}

	/**
	 *  Constructor
	 */
	public DialogLayout(int divider) {
		m_divider = divider;
	}

	/**
	 *  LayoutManager implementaton
	 */
	public void addLayoutComponent(String name, Component comp) {}

	/**
	 *  LayoutManager implementaton
	 */
	public void removeLayoutComponent(Component comp) {}

	/**
	 *  LayoutManager implementaton
	 */
	public Dimension minimumLayoutSize(Container parent) {
		return preferredLayoutSize(parent);
	}

	/**
	 *  Calculates preferred size of given container
	 */
	public Dimension preferredLayoutSize(Container parent) {
		m_v.removeAllElements();
		int w = 0;
		int h = 0;
		int type = -1;
		int typeCounter = 0;

		for (int k=0 ; k<parent.getComponentCount(); k++) {
			Component comp = parent.getComponent(k);
			int newType = getLayoutType(comp);
			if (k == 0)
				type = newType;

			if (type != newType) {
				Dimension d = preferredLayoutSize(m_v, type);
				w = Math.max(w, d.width);
				h += d.height + m_vGap;
				m_v.removeAllElements();
				type = newType;
				typeCounter++;
			}

			m_v.addElement(comp);
		}

		Dimension d = preferredLayoutSize(m_v, type);
		w = Math.max(w, d.width);
		h += d.height;

		if (typeCounter > 1)
			h -= m_vGap;

		Insets insets = parent.getInsets();
		return new Dimension(w+insets.left+insets.right,
			h+insets.top+insets.bottom);

	}

	protected Dimension preferredLayoutSize(Vector v, int type) {
		if (v.size() == 0)
			return new Dimension(0, 0);

		int w = 0;
		int h = 0;
		switch (type) {

		case COMP_TWO_COL:
			int divider = getDivider(v);
			for (int k=1 ; k<v.size(); k+=2) {
				Component comp1 = (Component)v.elementAt(k-1);
				Component comp2 = (Component)v.elementAt(k);
				Dimension d1 = comp1.getPreferredSize();
				Dimension d2 = comp2.getPreferredSize();
				int hh = Math.max(d1.height, d2.height);

				w = Math.max(w, d2.width);
				h += hh + m_vGap;
			}
			h -= m_vGap;
			return new Dimension(divider+w, h);

		case COMP_BIG:
			for (int k=0 ; k<v.size(); k++) {
				Component comp = (Component)v.elementAt(k);
				Dimension d = comp.getPreferredSize();
				w = Math.max(w, d.width);
				h += d.height + m_vGap;
			}
			h -= m_vGap;
			return new Dimension(w, h);

		case COMP_BUTTON:
			Dimension d = getMaxDimension(v);
			w = d.width + m_hGap;
			h = d.height;
			return new Dimension(w*v.size()-m_hGap, h+m_vGap);

		default:
			throw new IllegalArgumentException("Unknown type "+type);
		}
	}

	/**
	 *  Do layout of a given container
	 */
	public void layoutContainer(Container parent) {
		m_v.removeAllElements();
		int type = -1;

		Insets insets = parent.getInsets();
		int w = parent.getSize().width - insets.left - insets.right;
		int x = insets.left;
		int y = insets.top;

		for (int k=0 ; k<parent.getComponentCount(); k++) {
			Component comp = parent.getComponent(k);
			int newType = getLayoutType(comp);
			if (k == 0)
				type = newType;

			if (type != newType) {
				y = layoutComponents(m_v, type, x, y, w);
				m_v.removeAllElements();
				type = newType;
			}

			m_v.addElement(comp);
		}

		y = layoutComponents(m_v, type, x, y, w);
		m_v.removeAllElements();
	}

	protected int layoutComponents(Vector v, int type,
			int x, int y, int w) {
		if (v.size() == 0)
			return 0;

		switch (type) {

		case COMP_TWO_COL:
			int divider = getDivider(v);
			for (int k=1 ; k<v.size(); k+=2) {
				Component comp1 = (Component)v.elementAt(k-1);
				Component comp2 = (Component)v.elementAt(k);
				Dimension d1 = comp1.getPreferredSize();
				Dimension d2 = comp2.getPreferredSize();
				int hh = Math.max(d1.height, d2.height);
				int dh1 = (hh-d1.height)/2;
				int dh2 = (hh-d2.height)/2;
				int xx = Math.max(divider-m_hGap, d1.width);

				comp1.setBounds(x, y+dh1, xx, d1.height);
				comp2.setBounds(x+xx+m_hGap, y+dh2, w-xx-m_hGap, d2.height);
				y += hh + m_vGap;
			}
			return y;

		case COMP_BIG:
			for (int k=0 ; k<v.size(); k++) {
				Component comp = (Component)v.elementAt(k);
				Dimension d = comp.getPreferredSize();
				comp.setBounds(x, y, w, d.height);
				y += d.height + m_vGap;
			}
			return y;

		case COMP_BUTTON:
			Dimension d = getMaxDimension(v);
			int ww = d.width*v.size() + m_hGap*(v.size()-1);
			int xx = x + Math.max(0, (w - ww)/2);
			y += m_vGap;
			for (int k=0 ; k<v.size(); k++) {
				Component comp = (Component)v.elementAt(k);
				comp.setBounds(xx, y, d.width, d.height);
				xx += d.width + m_hGap;
			}
			return y + d.height;

		default:
			throw new IllegalArgumentException("Unknown type "+type);
		}
	}

	/**
	 *  Get horizontal gap
	 */
	public int getHGap() {
		return m_hGap;
	}

	/**
	 *  Get vertical gap
	 */
	public int getVGap() {
		return m_vGap;
	}

	/**
	 *  Get divider's position
	 */
	public int getDivider() {
		return m_divider;
	}

	/**
	 *  Set divider's position
	 */
	public void setDivider(int divider) {
		if (divider > 0)
			m_divider = divider;
	}

	protected int getDivider(Vector v) {
		if (m_divider > 0)
			return m_divider;

		int divider = 0;
		for (int k=0 ; k<v.size(); k+=2) {
			Component comp = (Component)v.elementAt(k);
			Dimension d = comp.getPreferredSize();
			divider = Math.max(divider, d.width);
		}
		divider += m_hGap;
		return divider;
	}

	protected Dimension getMaxDimension(Vector v) {
		int w = 0;

		int h = 0;
		for (int k=0 ; k<v.size(); k++) {
			Component comp = (Component)v.elementAt(k);
			Dimension d = comp.getPreferredSize();
			w = Math.max(w, d.width);
			h = Math.max(h, d.height);
		}
		return new Dimension(w, h);
	}

	protected int getLayoutType(Component comp) {
		if (comp instanceof JButton)
			return COMP_BUTTON;
		else if (comp instanceof ComboPanel)
			return COMP_TWO_COL;
		else if (comp instanceof JPanel ||
			comp instanceof JScrollPane ||
			comp instanceof JTabbedPane)
			return COMP_BIG;
		else
			return COMP_TWO_COL;
	}

	/**
	 *  Class used to hold two or more components in a one cell
	 */
	static class ComboPanel extends JPanel {
		public ComboPanel() {}

		public ComboPanel(LayoutManager layout) { super(layout); }
	}

	/**
	 *  Creates JPanel which may be used to hold two or more components in a one cell
	 */
	public static JPanel getComboPanel() {
		return new ComboPanel();
	}

	/**
	 *  Creates JPanel which may be used to hold two or more components in a one cell
	 */
	public static JPanel getComboPanel(LayoutManager layout) {
		return new ComboPanel(layout);
	}

}

