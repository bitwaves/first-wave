package ch.bitwave.lang.delphi.parser;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.DefaultErrorStrategy;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;

public class LoggingBailErrorStrategy extends DefaultErrorStrategy {

	private List<RecognitionException> errors = new ArrayList<RecognitionException>();

	@Override
	public void reportError(final Parser recognizer, final RecognitionException e)
			throws RecognitionException {
		super.reportError(recognizer, e);
		this.errors.add(e);
	}

	public boolean hasErrors() {
		return !this.errors.isEmpty();
	}

}
