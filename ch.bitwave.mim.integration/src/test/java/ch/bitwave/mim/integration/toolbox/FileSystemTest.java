package ch.bitwave.mim.integration.toolbox;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class FileSystemTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void shouldGetRelativeFolders() {
		String[] folders = FileSystem.getRelativeFolders(new File("c:/root"), new File(
				"c:/root/alpha/beta"));
		assertTrue(Arrays.deepEquals(new String[] { "alpha", "beta" }, folders));
	}

	@Test
	public void shouldGetEmptyListForIdenticalPaths() {
		String[] folders = FileSystem.getRelativeFolders(new File("c:/root"), new File("c:/ROOT"));
		assertTrue(folders.length == 0);
	}

	@Test
	public void shouldExceptOnGetRelativeFoldersWithIncompatiblePaths() {
		this.thrown.expect(RuntimeException.class);
		this.thrown.expectMessage("Path \"c:\\root\\alpha\\beta\" does not root in \"c:\\bar\".");
		FileSystem.getRelativeFolders(new File("c:/bar"), new File("c:/root/alpha/beta"));
	}
}
