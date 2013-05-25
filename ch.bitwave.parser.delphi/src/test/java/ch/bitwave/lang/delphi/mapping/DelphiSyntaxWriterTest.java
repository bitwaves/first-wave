package ch.bitwave.lang.delphi.mapping;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.junit.Test;

import ch.bitwave.lang.delphi.ast.Unit;
import ch.bitwave.lang.delphi.driver.DelphiSourceParserDriver;
import ch.bitwave.lang.delphi.testbase.Resources;

public class DelphiSyntaxWriterTest {
	@Test
	public void shouldWriteSampleUnit() throws IOException {
		Unit unit = new DelphiSourceParserDriver().readFile(new File(Resources.RESOURCE_FOLDER
				+ "SampleUnit1.pas"), Resources.ENCODING);

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		Writer w = new OutputStreamWriter(bos);
		DelphiSyntaxWriter writer = new DelphiSyntaxWriter(w);
		writer.writeNode(unit);

		w.close();
		System.out.print(bos.toString());
	}
}
