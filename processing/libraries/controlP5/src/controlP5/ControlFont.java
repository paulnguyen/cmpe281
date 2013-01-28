
package controlP5;

/**
 * controlP5 is a processing gui library.
 *
 *  2006-2012 by Andreas Schlegel
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2.1
 * of the License, or (at your option) any later version.
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General
 * Public License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA 02111-1307 USA
 *
 * @author 		Andreas Schlegel (http://www.sojamo.de)
 * @modified	08/27/2012
 * @version		0.7.6
 *
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

/**
 * A ControlFont is a container for a PFont that can be used to customize the font of a label.
 * (Designing the Font handling gave me a big headache, especially when it comes to calculating the
 * dimensions of a font which are not available at all times but only at certain times. The current
 * status I suppose is a good compromise and works for standard font handling cases. For any special
 * cases it will be difficult to convince me to make any changes.)
 * 
 * @example extra/ControlP5controlFont
 */
public class ControlFont {

	private FontLabel _myFontLabel;

	public static boolean DEBUG = false;

	/**
	 * set the RENDER_2X variable to true to double render text, this makes the font look bolder
	 * especially in OpenGL mode. use: ControlFont.RENDER_2X = true;
	 */
	public static boolean RENDER_2X;


	public ControlFont(PFont theFont) {
		this(theFont, checkFontSize(theFont));

	}


	static private int checkFontSize(PFont theFont) {
		try {
			return theFont.getFont().getSize();
		} catch (NullPointerException e) {
			System.out.println("ControlP5: could not find font-size details for font " + theFont.getName() + ", use constructor ControlFont(PFont theFont, int theFontSize) to specify the font size.");
			return 10;
		}
	}


	public ControlFont(PFont theFont, int theFontSize) {
		this(theFont, theFontSize, theFontSize + 2);
	}


	public ControlFont(PFont theFont, int theFontSize, int theLineHeight) {
		_myFontLabel = new PFontLabel(theFont, theFontSize, theLineHeight);
	}


	protected ControlFont(int theBitFontIndex) {
		_myFontLabel = new BitFontLabel(theBitFontIndex);
	}


	void init(Label theLabel) {
		_myFontLabel.init(theLabel);
	}


	void adjust(PApplet theApplet, Label theLabel) {
		get().adjust(theApplet, theLabel);
	}


	public void draw(PApplet theApplet, Label theLabel) {
		get().draw(theApplet, theLabel);
	}


	public FontLabel get() {
		return _myFontLabel;
	}


	public int getWidth() {
		return get().getWidth();
	}


	public int getHeight() {
		return get().getHeight();
	}


	public static int getWidthFor(String theText, Label theLabel, PApplet theApplet) {
		if (theLabel.getFont().get() instanceof BitFontLabel) {
			BitFontLabel bf = ((BitFontLabel) theLabel.getFont().get());
			return BitFontRenderer.getWidth(theLabel, bf, theText);
		}
		else {
			PFontLabel pf = ((PFontLabel) theLabel.getFont().get());
			theApplet.textFont(pf.pfont, pf.size);
			return (int) theApplet.textWidth(theText);
		}
	}


	interface FontLabel {

		void adjust(PApplet theApplet, Label theLabel);


		void draw(PApplet theApplet, Label theLabel);


		int getWidth();


		int getHeight();


		void init(Label theLabel);


		int getCenter();


		int getTop();


		int getBottom();


		int getBaseline();


		int getTextHeight();


		int getOverflow();


		int getOffset(int theIndex);


		int getSize();

	}

	class BitFontLabel implements FontLabel {

		private int _myFontIndex = 0;

		private PImage _myImage;

		private PImage _myImageMask;

		private Label plabel;

		int changeInSizeThreshold = 0;

		private int top;

		private int bottom;

		private int center;

		private int baseline;

		private int height;

		private int width;

		private int _myTextHeight = 1;

		private int[] offset = new int[2];


		BitFontLabel(int theIndex) {
			_myFontIndex = theIndex;
		}


		@Override public void init(Label theLabel) {
			width = BitFontRenderer.getWidth(theLabel, this);
			height = BitFontRenderer.getHeight(this.getFontIndex());
			bottom = 3;
			top = -height + bottom;
			center = -6 + bottom;
			baseline = 0;
		}


		@Override public int getSize() {
			return 6;
		}


		@Override public void adjust(PApplet theApplet, Label theLabel) {
			if (_myImage == null) {
				adjustTexture(theApplet, theLabel);
			}
			if (changed(plabel, theLabel)) {
				if (theLabel.isMultiline()) {
					adjustTexture(theApplet, theLabel);
					return;
				}
				adjustTexture(theApplet, theLabel);
				BitFontRenderer.write(this, theLabel);
			}
		}


		private void calculateHeight(Label theLabel) {
			_myTextHeight = BitFontRenderer.write(this, theLabel);
			if (theLabel.isMultiline()) {
				int n = (int) ((_myTextHeight - height + theLabel.getLineHeight()) * theLabel.getOffsetYratio());
				offset[1] = n > 0 ? 0 : n;
			}
		}


		@Override public int getOverflow() {
			return (_myTextHeight - height);
		}


		@Override public int getOffset(int theIndex) {
			return offset[theIndex];
		}


		@Override public void draw(PApplet theApplet, Label theLabel) {
			theApplet.noStroke();
			theApplet.translate(0, -top);
			debug(theApplet, theLabel);
			theApplet.image(_myImage, 0, top);
			plabel = theLabel.copy();

		}


		@Override public int getTextHeight() {
			return _myTextHeight;
		}


		private void debug(PApplet theApplet, Label theLabel) {
			if (DEBUG) {

				theApplet.stroke(0, 255, 0); // BASELINE
				theApplet.line(0, getBaseline(), theApplet.textWidth(theLabel.getText()), getBaseline());

				theApplet.stroke(0, 0, 255); // TOP
				theApplet.line(0, getTop(), theApplet.textWidth(theLabel.getText()), getTop());

				theApplet.stroke(255, 255, 0); // BOTTOM
				theApplet.line(0, getBottom(), theApplet.textWidth(theLabel.getText()), getBottom());

				theApplet.stroke(255, 0, 0); // CENTER
				theApplet.line(0, getCenter(), theApplet.textWidth(theLabel.getText()), getCenter());
				theApplet.noStroke();
			}
		}


		private void adjustTexture(PApplet theApplet, Label theLabel) {
			if (theLabel.isMultiline() || theLabel.isFixedSize()) {
				width = theLabel.getWidth();
				height = theLabel.getHeight();
			}
			else {
				width = BitFontRenderer.getWidth(theLabel, this);
				height = BitFontRenderer.getHeight(this.getFontIndex());
				width += changeInSizeThreshold;
			}
			_myImage = theApplet.createImage(width, height, PApplet.ARGB);
			_myImageMask = theApplet.createImage(width, height, PApplet.RGB);
			calculateHeight(theLabel);
			theLabel.setWidth(width);
			theLabel.setHeight(height);
			theLabel.setChanged(false);
		}


		private boolean changed(Label a, Label b) {
			if (b.isMultiline()) {
				if (b.isChanged()) {
					return true;
				}
				return false;
			}
			if (a == null || b == null) {
				return false;
			}
			else if (b.isChanged()) {
				b.setChanged(false);
				return true;
			}

			return !a.getText().equals(b.getText());
		}


		@Override public int getHeight() {
			return height;
		}


		@Override public int getWidth() {
			return width;
		}


		@Override public int getCenter() {
			return center;
		}


		@Override public int getBaseline() {
			return baseline;
		}


		@Override public int getTop() {
			return top;
		}


		@Override public int getBottom() {
			return bottom;
		}


		public void setFontIndex(int theIndex) {
			_myFontIndex = theIndex;
			_myImage = null;
		}


		public int getFontIndex() {
			return _myFontIndex;
		}


		public PImage getImage() {
			return _myImage;
		}


		public PImage getImageMask() {
			return _myImageMask;
		}

	}

	class PFontLabel implements FontLabel {

		PFont pfont;

		List<String> txt;

		String s = "";

		private int top;

		private int bottom;

		private int center;

		private int height;

		private int width;

		private int baseline = 0;

		private int _myTextHeight = 1;

		private int[] offset = new int[2];

		private int size;


		PFontLabel(PFont theFont, int theFontSize, int theLineHeight) {
			pfont = theFont;
			size = theFontSize;
			txt = new ArrayList<String>();
		}


		@Override public void init(Label theLabel) {
			// when the font changes, init is called.
			// width and height should be adjusted to the updated font here,
			// but we need PApplet here to determine the width of the label.
			// unfortunately we dont have access to PApplet here, so a change
			// might result in a 1-frame-flickr but doesnt necessarily need
			// to happen.
		}


		public void setSize(int theSize) {
			size = theSize;
		}


		@Override public int getSize() {
			/* quickfix http://code.google.com/p/controlp5/issues/detail?id=46 first check the pfont size then default back to size */
			return size;
		}


		@Override public int getOffset(int theIndex) {
			return offset[theIndex];
		}


		@Override public int getTextHeight() {
			return _myTextHeight;
		}


		@Override public int getWidth() {
			return width;
		}


		@Override public int getHeight() {
			return height;
		}


		@Override public int getCenter() {
			return center;
		}


		@Override public int getTop() {
			return top;
		}


		@Override public int getBottom() {
			return bottom;
		}


		@Override public int getBaseline() {
			return baseline;
		}


		public PFont getFont() {
			return pfont;
		}


		@Override public void adjust(PApplet theApplet, Label theLabel) {
			if (theLabel.isChanged()) {
				theApplet.textFont(pfont, size);
				// the origin of a PFont Label is top left corner, therefore
				// the following the following measures have to be calculated
				// when a font is changed. we have to do that here since PApplet
				// is required to calculate a font's ascent and descent value.
				// values are calculated based on the baseline (which is 0),
				// therefore center and top are negative values.
				// to order to sync the line height with the height of the font,
				// the value of lineHeightOffset carries this offset value.
				// This becomes necessary when working with multiple lines.
				top = -(int) theApplet.textAscent();
				bottom = (int) theApplet.textDescent();
				center = -(int) ((-top - bottom) / 2);
				height = theLabel.isMultiline() ? theLabel.getHeight() : (int) (theApplet.textAscent() + theApplet.textDescent());
				width = theLabel.isMultiline() ? theLabel.getWidth() : (int) theApplet.textWidth(theLabel.getTextFormatted());
				if (theLabel.isMultiline()) {
					calculateHeight(theApplet, theLabel);
				}
				theLabel.setChanged(false);
			}
		}


		private void calculateHeight(PApplet theApplet, Label theLabel) {
			txt.clear();
			String myString = theLabel.getTextFormatted();
			List<String> paragraphs = Arrays.asList(myString.split("\n"));
			// does not recognize linebreaks at the end of theString.
			myString = "";
			for (String p : paragraphs) {
				List<String> words = Arrays.asList(p.split("\\s"));
				for (String w : words) {
					if (theApplet.textWidth(myString + w) < width) {
						myString += w + " ";
					}
					else {
						txt.add(myString.substring(0, PApplet.max(0, myString.length() - 1)));
						myString = w + " ";
					}
				}
				txt.add(myString.substring(0, myString.length() - 1));
				myString = "";
			}
			if (theLabel.getHeight() % theLabel.getLineHeight() != 0) {
				txt.add("");
			}
			_myTextHeight = (PApplet.round(txt.size() * theLabel.getLineHeight()));
			int maxLineNum = PApplet.round(theLabel.getHeight() / theLabel.getLineHeight());
			int offset = (int) (PApplet.max(0, txt.size() - maxLineNum) * (PApplet.abs(theLabel.getOffsetYratio())));
			int lim = PApplet.min(txt.size(), maxLineNum);
			s = "";
			for (int i = 0; i < lim; i++) {
				s += txt.get(i + offset) + "\n";
			}
		}


		@Override public int getOverflow() {
			return (_myTextHeight - height);
		}


		@Override public void draw(PApplet theApplet, Label theLabel) {
			PFont loadedFont = theApplet.g.textFont;
			float loadedSize = theApplet.g.textSize;
			if (loadedFont == null) {
				theApplet.textSize(loadedSize); //forces default font
				loadedFont = theApplet.g.textFont;
			}
			int loadedAlign = theApplet.g.textAlign;

			theApplet.textFont(pfont, size);
			theApplet.fill(theLabel.getColor());
			if (theLabel.isMultiline()) {
				theApplet.fill(theLabel.getColor());
				theApplet.textLeading(theLabel.getLineHeight());
				theApplet.text(s, 0, 0, theLabel.getWidth(), theLabel.getHeight());
			}
			else {
				theApplet.translate(0, -top + 1);
				debug(theApplet, theLabel);
				theApplet.fill(theLabel.getColor());
				theApplet.text(theLabel.getTextFormatted(), 0, 0);
				if (RENDER_2X) {
					theApplet.text(theLabel.getTextFormatted(), 0, 0);
				}
			}

			theApplet.textFont(loadedFont, loadedSize);
			theApplet.textAlign(loadedAlign);
		}


		private void debug(PApplet theApplet, Label theLabel) {
			if (DEBUG) {

				theApplet.stroke(0, 255, 0); // BASELINE
				theApplet.line(0, getBaseline(), theApplet.textWidth(theLabel.getText()), getBaseline());

				theApplet.stroke(0, 0, 255); // TOP
				theApplet.line(0, getTop(), theApplet.textWidth(theLabel.getText()), getTop());

				theApplet.stroke(255, 255, 0); // BOTTOM
				theApplet.line(0, getBottom(), theApplet.textWidth(theLabel.getText()), getBottom());

				theApplet.stroke(255, 0, 0); // CENTER
				theApplet.line(0, getCenter(), theApplet.textWidth(theLabel.getText()), getCenter());

				theApplet.stroke(255, 128, 0); // CENTER_CAPS
				theApplet.line(0, getTop() / 2, theApplet.textWidth(theLabel.getText()), getTop() / 2);

				theApplet.noStroke();
			}
		}

	}

}

// textorize, a Ruby-based font rasterizer command line utility for Mac OS X
// http://textorize.org/

