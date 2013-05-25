/*
 * ANTLRNoCaseFileStream.java
 *
 * Created on January 25, 2008, 2:12 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package ch.bitwave.lang.delphi.parser;

import java.io.IOException;

import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CharStream;

/**
 * 
 * @author Jim Idle
 */
public class ANTLRNoCaseFileStream extends ANTLRFileStream {
	public ANTLRNoCaseFileStream(final String fileName) throws IOException {
		super(fileName, null);
	}

	public ANTLRNoCaseFileStream(final String fileName, final String encoding) throws IOException {
		super(fileName, encoding);
	}

	@Override
	public int LA(int i) {
		if (i == 0) {
			return 0; // undefined
		}
		if (i < 0) {
			i++; // e.g., translate LA(-1) to use offset 0
		}

		if ((this.p + i - 1) >= this.n) {

			return CharStream.EOF;
		}
		return Character.toLowerCase(this.data[this.p + i - 1]);
	}

}