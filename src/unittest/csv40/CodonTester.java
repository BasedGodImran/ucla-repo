package unittest.csv40;

import static java.lang.System.out;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.vcc.csv40.Program;

public class CodonTester {
	private static final int maximumScore = 40;
	private static int totalScore;

	private static Path correctOuputPath = Paths.get("testSequenceOutput.txt");

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;
	private final PrintStream originalErr = System.err;

	@Before
	public void setUpStreams() {
		System.setOut(new PrintStream(outContent));
		System.setErr(new PrintStream(errContent));
	}

	@After
	public void restoreStreams() {
		System.setOut(originalOut);
		System.setErr(originalErr);
	}

	@BeforeClass
	public static void beforeTesting() throws IOException {
		totalScore = 0;
	}

	@AfterClass
	public static void afterTesting() {
		out.printf("Your program's functionality scores %d out of %d.\n\n", totalScore, maximumScore);
	}

	public static boolean equalsIgnoreNewlineStyle(final String s1, final String s2) {
		return s1 != null && s2 != null && normalizeLineEnds(s1).equals(normalizeLineEnds(s2));
	}

	private static String normalizeLineEnds(final String s) {
		return s.replace("\r\n", "\n").replace('\r', '\n');
	}

	@Test
	public void testMain() throws Exception {
		Program.main(null);
		final String studentOutput = outContent.toString().trim();
		final String correctOutput = Files.readString(correctOuputPath).trim();
		assertTrue(equalsIgnoreNewlineStyle(studentOutput, correctOutput));
		totalScore += 40;
	}

}
