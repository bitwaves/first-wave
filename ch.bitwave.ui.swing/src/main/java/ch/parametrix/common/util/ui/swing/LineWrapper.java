package ch.parametrix.common.util.ui.swing;

import java.awt.FontMetrics;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * Helper class for wrapping text across multiple lines.
 */
public class LineWrapper {

	/**
	 * Wraps the given text according to the given font metrics and width in
	 * device pixels.
	 * 
	 * @param fm
	 *            the font metrix describing the font characteristics.
	 * @param text
	 *            the text to wrap.
	 * @param width
	 *            the width in device pixels to wrap to.
	 * @param ox
	 *            the X offset of the begin of the first line.
	 * @return the wrapped lines.
	 */
	public static List<String> breakIntoLines(final FontMetrics fm, final String text,
			final int width, int ox) {
		String breakableCharacters = " \t:\\/-";
		if (width < 1) {
			throw new IllegalArgumentException("Width must be positive"); //$NON-NLS-1$
		}
		int fromIndex = 0;
		int pos = 0;
		int bestpos;
		String largestString;
		List<String> lines = new ArrayList<String>();
		while (fromIndex != -1) {
			while (fromIndex < text.length()
					&& breakableCharacters.indexOf(text.charAt(fromIndex)) >= 0) {
				++fromIndex;
				if (fromIndex >= text.length()) {
					break;
				}
			}
			pos = fromIndex;
			bestpos = -1;
			largestString = null;
			String sample = text;
			while (pos >= fromIndex) {
				boolean bHardNewline = false;
				int newlinePos = text.indexOf('\n', pos);
				int breakableCharPos = indexOfAny(text, breakableCharacters, pos);
				if (newlinePos != -1
						&& ((breakableCharPos == -1) || (breakableCharPos != -1 && newlinePos < breakableCharPos))) {
					pos = newlinePos;
					bHardNewline = true;
				} else {
					pos = breakableCharPos;
					bHardNewline = false;
				}
				if (pos == -1) {
					sample = text.substring(fromIndex);
				} else {
					sample = text.substring(fromIndex, pos + 1);
				}
				if (fm.stringWidth(sample) < width - ox) {
					largestString = sample;
					bestpos = pos;
					if (bHardNewline) {
						bestpos++;
					}
					if (pos == -1 || bHardNewline) {
						break;
					}
				} else {
					break;
				}
				++pos;
			}
			if (largestString == null) {
				int totalWidth = 0;
				int oneCharWidth = 0;
				pos = fromIndex;
				while (pos < text.length()) {
					oneCharWidth = fm.charWidth(text.charAt(pos));
					if ((totalWidth + oneCharWidth) >= width - ox) {
						break;
					}
					totalWidth += oneCharWidth;
					++pos;
				}
				lines.add(text.substring(fromIndex, pos));
				ox = 0;
				fromIndex = pos;
			} else {
				lines.add(largestString);
				fromIndex = bestpos;
			}
		}

		return lines;
	}

	/**
	 * <p>
	 * Search a String to find the first index of any character in the given set
	 * of characters.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> String will return <code>-1</code>. A
	 * <code>null</code> search string will return <code>-1</code>.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.indexOfAny(null, *)            = -1
	 * StringUtils.indexOfAny("", *)              = -1
	 * StringUtils.indexOfAny(*, null)            = -1
	 * StringUtils.indexOfAny(*, "")              = -1
	 * StringUtils.indexOfAny("zzabyycdxx", "za") = 0
	 * StringUtils.indexOfAny("zzabyycdxx", "by") = 3
	 * StringUtils.indexOfAny("aba","z")          = -1
	 * </pre>
	 * 
	 * @param str
	 *            the String to check, may be null
	 * @param searchChars
	 *            the chars to search for, may be null
	 * @param pos
	 *            the offset to start searching
	 * 
	 * @return the index of any of the chars, -1 if no match or null input
	 * @since 2.0
	 */
	protected static int indexOfAny(final String str, final String searchChars, final int pos) {
		if (StringUtils.isEmpty(str) || StringUtils.isEmpty(searchChars)) {
			return -1;
		}
		return indexOfAny(str, searchChars.toCharArray(), pos);
	}

	/**
	 * <p>
	 * Search a String to find the first index of any character in the given set
	 * of characters.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> String will return <code>-1</code>. A
	 * <code>null</code> or zero length search array will return <code>-1</code>
	 * .
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.indexOfAny(null, *)                = -1
	 * StringUtils.indexOfAny("", *)                  = -1
	 * StringUtils.indexOfAny(*, null)                = -1
	 * StringUtils.indexOfAny(*, [])                  = -1
	 * StringUtils.indexOfAny("zzabyycdxx",['z','a']) = 0
	 * StringUtils.indexOfAny("zzabyycdxx",['b','y']) = 3
	 * StringUtils.indexOfAny("aba", ['z'])           = -1
	 * </pre>
	 * 
	 * @param str
	 *            the String to check, may be null
	 * @param searchChars
	 *            the chars to search for, may be null
	 * @param pos
	 *            the offset to start searching
	 * 
	 * @return the index of any of the chars, -1 if no match or null input
	 * @since 2.0
	 */
	protected static int indexOfAny(final String str, final char[] searchChars, final int pos) {
		if (StringUtils.isEmpty(str) || searchChars.length == 0) {
			return -1;
		}
		int csLen = str.length();
		int csLast = csLen - 1;
		int searchLen = searchChars.length;
		int searchLast = searchLen - 1;
		for (int i = pos; i < csLen; i++) {
			char ch = str.charAt(i);
			for (int j = 0; j < searchLen; j++) {
				if (searchChars[j] == ch) {
					if (i < csLast && j < searchLast && isHighSurrogate(ch)) {
						// ch is a supplementary character
						if (searchChars[j + 1] == str.charAt(i + 1)) {
							return i;
						}
					} else {
						return i;
					}
				}
			}
		}
		return -1;
	}

	/**
	 * Indicates whether {@code ch} is a high- (or leading-) surrogate code unit
	 * that is used for representing supplementary characters in UTF-16
	 * encoding.
	 * 
	 * @param ch
	 *            the character to test.
	 * @return {@code true} if {@code ch} is a high-surrogate code unit;
	 *         {@code false} otherwise.
	 */
	private static boolean isHighSurrogate(final char ch) {
		return ('\uD800' <= ch && '\uDBFF' >= ch);
	}

}
