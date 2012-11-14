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

/**
 * adopted from fasttext by Glen Murphy @ http://glenmurphy.com/
 */

import java.util.HashMap;
import java.util.Map;

import processing.core.PApplet;
import processing.core.PImage;

/**
 * <p>
 * The BitfontRenderer is used to draw controlP5's text labels, by default it uses the bitfont
 * standard58 by miniml. The bitfontRenderer is based on a per pixel technique and is not using
 * processing's PFont renderer. To use PFonts within controlP5, take a look at ControlFont
 * </p>
 * <p>
 * ftext - fast text for processing. to create a font graphic use the following string (first
 * character being a space) !"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\]^_`
 * abcdefghijklmnopqrstuvwxyz{|}~
 * </p>
 * <ul>
 * <li><a href="http://www.dafont.com/advocut.font" target="_blank">advocut.font</a></li>
 * <li><a href="http://www.dafont.com/grixel-kyrou-9.font" target="_blank">grixel-kyrou-9.font</a></li>
 * <li><a href="http://www.dafont.com/david-sans.font" target="_blank">david-sans.font</a></li>
 * <li><a href="http://www.dafont.com/sven-stuber.d516" target="_blank">sven-stuber.d516</a></li>
 * <li><a href="http://www.dafont.com/supernatural.font" target="_blank">supernatural.font</a></li>
 * <li><a href="http://www.dafont.com/supertext.font" target="_blank">supertext.font</a></li>
 * <li><a href="http://www.dafont.com/regupix.font" target="_blank">regupix.font</a></li>
 * <li><a href="http://www.dafont.com/optiate.font" target="_blank">optiate.font</a></li>
 * <li><a href="http://www.dafont.com/superhelio.font" target="_blank">superhelio.font</a></li>
 * <li><a href="http://www.dafont.com/superbly.font" target="_blank">superbly.font</a></li>
 * <li><a href="http://www.fontsquirrel.com/fonts/Audimat-Mono" target="_blank">Audimat-Mono</a></li>
 * <li><a href="http://www.fontsquirrel.com/fonts/Envy-Code-R" target="_blank">Envy-Code-R</a></li>
 * </ul>
 * 
 * @see controlP5.ControlFont
 */
public class BitFontRenderer {

	protected static int numFonts = 4;

	public static final int standard58 = ControlP5.standard58;

	public static final int standard56 = ControlP5.standard56;

	public static final int synt24 = ControlP5.synt24;

	public static final int grixel = ControlP5.grixel;

	protected float height;

	protected static Map<Integer, BitFont> fonts;

	private final ControlP5 cp5;

	protected BitFontRenderer(ControlP5 theControlP5) {
		cp5 = theControlP5;
		loadFonts();
	}

	private void loadFonts() {
		if (fonts == null) {
			fonts = new HashMap<Integer, BitFont>();
			fonts.put(standard58, new BitFont(standard58).setSource("standard58.gif"));
			fonts.put(standard56, new BitFont(standard56).setSource("standard56.gif"));
			fonts.put(synt24, new BitFont(synt24).setSource("synt24.gif"));
			fonts.put(grixel, new BitFont(grixel).setSource("GrixelKyrou9.gif"));
		}
	}

	/**
	 * TODO implement addBitFont
	 * 
	 * @exclude
	 * @param theImage
	 * @return
	 */
	public static int addBitFont(PImage theImage) {
		ControlP5.logger.info("adding custom bitfonts is disabled with this version of controlP5.");
		return -1;
	}

	protected static BitFont getFont(int theIndex) {
		return fonts.get(theIndex);
	}

	protected static int getPosition(Label theLabel, ControlFont.BitFontLabel theBitFont, int theX) {
		BitFont f = fonts.get(theBitFont.getFontIndex());
		String s = theLabel.getTextFormatted();
		int l = s.length();
		int x = 0;
		for (int i = 0; i < l; i++) {
			final int myIndex = ((int) s.charAt(i) - 32);
			if (myIndex >= 0 && myIndex <= 95) {
				x += f.charWidth[myIndex] + theLabel.getLetterSpacing();
				if (x >= theX) {
					return i;
				}
			}
		}
		return l;
	}

	public static int getWidth(Label theLabel, final ControlFont.BitFontLabel theBitFont, String theText) {
		return getDimension(theLabel, theBitFont, -1, -1, theText, 0, theText.length())[0];
	}

	public static int getWidth(Label theLabel, final ControlFont.BitFontLabel theBitFont) {
		return getWidth(theLabel, theBitFont, theLabel.getText().length());
	}

	protected static int getWidth(Label theLabel, final ControlFont.BitFontLabel theBitFont, int theLength) {
		return getDimension(theLabel, theBitFont, -1, -1, theLabel.getTextFormatted(), 0, theLength)[0];
	}

	protected static int[] getDimension(Label theLabel, ControlFont.BitFontLabel theBitFont, String theText) {
		return getDimension(theLabel, theBitFont, -1, -1, theText, 0, theText.length());
	}

	protected static int[] getDimension(Label theLabel, final ControlFont.BitFontLabel theBitFont, final int theWidth, final int theHeight, final String theText, final int theStart, int theEnd) {
		int[] dim = { 0, theLabel.getLineHeight() };
		int tx = 0;
		BitFont f = fonts.get(theBitFont.getFontIndex());
		// String s = theLabel.getTextFormatted();
		theEnd = (int)PApplet.min(theText.length(),theEnd);
		for (int i = theStart; i < theEnd; i++) {
			final int myIndex = ((int) theText.charAt(i) - 32);
			if (myIndex >= 0 && myIndex <= 95) {
				dim[0] += f.charWidth[myIndex] + theLabel.getLetterSpacing();
			} else {
				int c = theText.charAt(i);
				if (c != 9 && c != 10 && c != 13) {
					// 9 = tab, 10 = new line, 13 = carriage return
					ControlP5.logger().warning(
							"You are using a character that is not supported by controlP5's BitFont-Renderer, you could use ControlFont instead (see the ControlP5controlFont example). ("
									+ ((int) theText.charAt(i)) + "," + theText.charAt(i) + ")");
				} else {
					if (dim[0] > tx) {
						tx = dim[0];
						dim[0] = 0;
					}
					if (c == 10 || c == 13) {
						dim[1] += theLabel.getLineHeight();
					}
				}
			}
		}
		dim[0] = (dim[0] > tx) ? dim[0] : tx;
		return dim;
	}

	public static int getHeight(int theFontIndex) {
		return fonts.get(theFontIndex).texture.height;
	}

	public static int getHeight(ControlFont.BitFontLabel theLabel) {
		return fonts.get(theLabel.getFontIndex()).texture.height;
	}

	private static void putchar(final int theC, final int theX, final int theY, final int theColor, final PImage theImage, final PImage theMask, final BitFont theBitFont) {
		final int myWH = theImage.width * theImage.height;
		final int len = theBitFont.charWidth[theC] * theBitFont.charHeight;
		final int w = theY * theImage.width;
		for (int i = 0; i < len; i++) {
			final int xpos = theX + i % theBitFont.charWidth[theC];
			final int pos = xpos + w + (i / theBitFont.charWidth[theC]) * theImage.width;
			if (theBitFont.chars[theC][i] == 0xff000000 && xpos < theImage.width && xpos >= 0 && pos >= 0 && pos < myWH) {
				theImage.pixels[pos] = theColor;
				theMask.pixels[pos] = 0xffffffff;
			}
		}
	}

	private static int writeCharacters(final ControlFont.BitFontLabel theBitFont, Label theLabel) {

		BitFont f = fonts.get(theBitFont.getFontIndex());

		int indent = 0;

		if (theLabel.isFixedSize()) {
			int n = getWidth(theLabel, theBitFont);
			indent = n > theLabel.getWidth() ? theLabel.getWidth() - n : 0;
		}

		int myOriginalY = theLabel.getFont().get().getOffset(1);

		int myY = theLabel.getFont().get().getOffset(1);

		final String s = theLabel.getTextFormatted();

		int myWrap = (theLabel.isMultiline()) ? theBitFont.getImage().width : -1;

		int l = s.length();

		final int[] letters_indent = new int[l];

		final int[] letters_letter = new int[l];

		final int[] letters_lineheight = new int[l];

		int err = 0;

		for (int i = 0; i < l; i++) {

			int c = (int) s.charAt(i);

			if (c != 10) {
				if ((myWrap > 0 && indent > myWrap)) {
					indent = 0;
					myY += theLabel.getLineHeight();
					final int j = i;
					err++;
					while (i > 0 && err < s.length()) {
						i--;
						// in case a word longer than the actual width.
						if (i == 1) {
							i = j;
							break;
						}
						// go back until you find a space or a dash.
						if (s.charAt(i) == ' ' || s.charAt(i) == '-') {
							i++;
							c = (int) s.charAt(i);
							break;
						}
					}
				}

				if (c >= 127 || c <= 32) {
					c = 32;
				}

				letters_indent[i] = indent;
				letters_letter[i] = c - 32;
				letters_lineheight[i] = myY;

				indent += f.charWidth[c - 32] + theLabel.getLetterSpacing();
			} else {
				myY += theLabel.getLineHeight();
				indent = 0;
				letters_indent[i] = 0;
				letters_letter[i] = -1;
				letters_lineheight[i] = 0;
			}
		}
		for (int i = 0; i < l; i++) {
			if (letters_letter[i] != -1) {
				putchar(letters_letter[i], letters_indent[i], letters_lineheight[i], theLabel.getColor(), theBitFont.getImage(), theBitFont.getImageMask(), f);
			}
		}
		return myY - myOriginalY;
	}

	public static int write(final ControlFont.BitFontLabel theBitFont, Label theLabel) {
		final int myWH = theBitFont.getImage().width * theBitFont.getImage().height;
		for (int i = 0; i < myWH; i++) {
			theBitFont.getImage().pixels[i] = 0x00ffffff;
			theBitFont.getImageMask().pixels[i] = 0xff000000;
		}
		final int myHeight = writeCharacters(theBitFont, theLabel);
		theBitFont.getImage().mask(theBitFont.getImageMask());
		return myHeight;
	}

	class BitFont {

		protected int characters;

		protected int[] charWidth = new int[255];

		protected int charHeight;

		protected int[][] chars;

		protected int lineHeight;

		protected int wh;

		protected PImage texture;

		protected int id;

		private String _mySource;

		BitFont(int theId) {
			id = theId;
		}

		int getHeight() {
			return texture.height;
		}

		BitFont setSource(String theSource) {
			_mySource = theSource;
			texture = cp5.papplet.loadImage(getClass().getResource(_mySource).toString());
			charHeight = texture.height;
			lineHeight = charHeight;
			int currWidth = 0;
			int maxWidth = 0;

			for (int i = 0; i < texture.width; i++) {
				currWidth++;
				if (texture.pixels[i] == 0xffff0000) {
					charWidth[characters] = currWidth;
					characters++;
					if (currWidth > maxWidth) {
						maxWidth = currWidth;
					}
					currWidth = 0;
				}
			}

			// create the character sprites.
			chars = new int[characters][maxWidth * charHeight];
			int indent = 0;
			for (int i = 0; i < characters; i++) {
				for (int u = 0; u < charWidth[i] * charHeight; u++) {
					chars[i][u] = texture.pixels[indent + (u / charWidth[i]) * texture.width + (u % charWidth[i])];
				}
				indent += charWidth[i];
			}
			return this;
		}

	}

	/**
	 * @deprecated
	 * @exclude
	 */
	@Deprecated
	public static int getWidth(ControlFont.BitFontLabel theLabel) {
		return -1;
	}
}
