package ch.bitwave.lang.delphi.driver;

import java.io.File;
import java.io.IOException;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

import ch.bitwave.lang.delphi.ast.Unit;
import ch.bitwave.lang.delphi.mapping.DelphiSyntaxMapper;
import ch.bitwave.lang.delphi.parser.ANTLRNoCaseFileStream;
import ch.bitwave.lang.delphi.parser.DelphiLexer;
import ch.bitwave.lang.delphi.parser.DelphiParser;
import ch.bitwave.lang.delphi.parser.LoggingBailErrorStrategy;

public class DelphiSourceParserDriver {

	/**
	 * Parses the given source file and returns the Delphi unit AST.
	 * 
	 * @param file
	 *            the file to read.
	 * @param encoding
	 *            the encoding of the file.
	 * @return the resulting AST.
	 * @throws IOException
	 *             if there was an issue accessing the file contents.
	 */
	public Unit readFile(final File file, final String encoding) throws IOException {
		ANTLRInputStream is = new ANTLRNoCaseFileStream(file.getAbsolutePath(), encoding);
		return readStream(is);
	}

	/**
	 * Parses the given source stream and returns the Delphi unit AST.
	 * 
	 * @param is
	 *            the stream to parse.
	 * @return the resulting AST.
	 */
	public Unit readStream(final ANTLRInputStream is) {
		DelphiLexer lex = new DelphiLexer(is);
		CommonTokenStream tokens = new CommonTokenStream(lex);
		DelphiParser parser = new DelphiParser(tokens);
		parser.setBuildParseTree(true);
		DelphiSyntaxMapper mapper = new DelphiSyntaxMapper();
		parser.addParseListener(mapper);
		// LoggingBailErrorStrategy errorStrategy = new
		// LoggingBailErrorStrategy();
		LoggingBailErrorStrategy errorStrategy = new LoggingBailErrorStrategy();
		parser.setErrorHandler(errorStrategy);
		parser.delphiUnit();
		if (errorStrategy.hasErrors()) {
			throw new RuntimeException("Parsing failed. See log for errors.");
		}
		return mapper.getUnit();
	}
}
