package ch.bitwave.lang.delphi.parser;

import java.io.IOException;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Before;
import org.junit.Test;

import ch.bitwave.lang.delphi.parser.DelphiParser.DelphiUnitContext;
import ch.bitwave.lang.delphi.testbase.Resources;

public class DelphiParserTest {

	private LoggingBailErrorStrategy errorCollectingHandler;

	@Before
	public void setUp() {
		this.errorCollectingHandler = new LoggingBailErrorStrategy();
	}

	@Test
	public void shouldParseSampleUnit4() throws IOException {
		ANTLRInputStream is = getStream("SampleUnit4.pas");
		parseExpression(is);
	}

	@Test
	public void shouldParseSampleUnit6() throws IOException {
		ANTLRInputStream is = getStream("SampleUnit6.pas");
		parseExpression(is);
	}

	@Test
	public void shouldParseSampleUnit2() throws IOException {
		ANTLRInputStream is = getStream("SampleUnit2.pas");
		parseExpression(is);
	}

	@Test
	public void shouldParseSampeUnit3() throws IOException {
		ANTLRInputStream is = getStream("SampleUnit3.pas");
		parseExpression(is);
	}

	@SuppressWarnings("unused")
	private void printParseTree(final ANTLRInputStream is, final ParserRuleContext context) {
		printNode(is, "", context);
	}

	private void printNode(final ANTLRInputStream is, final String indent, final ParseTree context) {
		String text = context.getClass().getSimpleName() + "> " + context.toString();
		context.getSourceInterval();
		System.out.println(indent + text);
		for (int i = 0; i < context.getChildCount(); i++) {
			ParseTree child = context.getChild(i);
			printNode(is, indent + "  ", child);
		}
	}

	private DelphiUnitContext parseExpression(final ANTLRInputStream is) throws IOException {
		DelphiLexer lex = new DelphiLexer(is);
		CommonTokenStream tokens = new CommonTokenStream(lex);
		DelphiParser parser = new DelphiParser(tokens);
		parser.setBuildParseTree(true);
		parser.addParseListener(new DelphiSampleListener());
		parser.setErrorHandler(this.errorCollectingHandler);
		DelphiUnitContext context = parser.delphiUnit();
		if (this.errorCollectingHandler.hasErrors()) {
			// printParseTree(is, context);
			throw new RuntimeException("There are parse errors, see log.");
		}
		return context;
	}

	private ANTLRInputStream getStream(final String fileName) throws IOException {
		return new ANTLRNoCaseFileStream(Resources.RESOURCE_FOLDER + fileName);
	}

}
