package ch.parametrix.common.util.ui.swing;

import static org.hamcrest.Matchers.is;

import java.awt.Color;

import org.apache.commons.lang.StringUtils;

import ch.bitwave.platform.codestyle.DBC;

/**
 * Helper class for dealing with colors specified as hexadecimal strings.
 * 
 */
public class HexColor {
	/**
	 * Creates a color from the given 6-character string. The format expected is
	 * "RRGGBB", where each color component is a 16-bit integer encoded as a
	 * hexadecimal byte. Example: "AAEECC".
	 * 
	 * @param value
	 * @return
	 */
	public static Color createColor(final String value) {
		DBC.PRE.assertFalse("The color value must not be empty or null.",
				StringUtils.isEmpty(value));
		DBC.PRE.assertThat(6, is(value.length()));
		int r = Integer.valueOf(value.substring(0, 2), 16);
		int g = Integer.valueOf(value.substring(2, 4), 16);
		int b = Integer.valueOf(value.substring(4, 6), 16);
		return new Color(r, g, b);
	}
}
