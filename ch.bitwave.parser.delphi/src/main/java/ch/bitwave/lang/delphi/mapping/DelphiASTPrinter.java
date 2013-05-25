package ch.bitwave.lang.delphi.mapping;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Stack;

import ch.bitwave.lang.delphi.ast.DelphiSyntaxAdapter;
import ch.bitwave.lang.delphi.ast.Node;

public class DelphiASTPrinter extends DelphiSyntaxAdapter {

	private Writer writer;
	private String currentIndent;
	private Stack<String> indentStack;

	private void append(final String text) {
		try {
			this.writer.write(text);
		} catch (IOException e) {
			throw new RuntimeException(String.format("Failed to append text to writer due to: %s",
					e.getMessage()), e);
		}
	}

	private void indent() {
		this.indentStack.push(this.currentIndent);
		this.currentIndent = this.currentIndent + "  ";
	}

	private void undent() {
		this.currentIndent = this.indentStack.pop();
	}

	private void writeIndent() {
		append(this.currentIndent);
	}

	public DelphiASTPrinter(final Writer writer) {
		super();
		this.writer = writer;
		this.currentIndent = "";
		this.indentStack = new Stack<String>();
	}

	public static String writeToString(final Node node) {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			Writer streamWriter = new OutputStreamWriter(bos);
			DelphiASTPrinter printer = new DelphiASTPrinter(streamWriter);
			printer.writeNode(node);
			streamWriter.close();
			return bos.toString();
		} catch (IOException e) {
			throw new RuntimeException(String.format("Failed to write node %s to a string.", node));
		}
	}

	public void writeNode(final Node node) {
		descend(node);
	}

	@Override
	protected void descend(final Node node) {
		writeIndent();
		append(node.getClass().getSimpleName());
		append("\r\n");
		indent();
		super.descend(node);
		undent();
	}

}
