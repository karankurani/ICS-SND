package ICS.SND.Tests;

import static org.junit.Assert.*;

import org.junit.Test;

import ICS.SND.Utilities.IterableFile;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;

public class JUNGTest{
	@Test
	public void MyJUNGTest() {
		Graph<Integer, String> g = new UndirectedSparseGraph<Integer, String>();
		Integer a = 1;
		Integer b = 2;
		Integer c = 3;
		g.addEdge("a", a, b);
		g.addEdge("b", b, c);
		g.addEdge("c", a, c);
		assertTrue(2 == g.degree(a));
	}
	
	@Test
	public void readingFile() {
		Graph<String, Integer> g = new UndirectedSparseGraph<String, Integer>();
		int count = 0;
		for (String line : new IterableFile(UnitTests.DATA_PATH + "CoAuthorNetwork.txt")) {
			String[] foo = line.split("<break>");
			String author = foo[0];
			if(!g.containsVertex(author)) {
				g.addVertex(author);
			}
			String[] coAuthors = foo[1].split(",");
			for (String coAuthor : coAuthors) {
				if(!g.containsVertex(coAuthor)) {
					g.addVertex(coAuthor);
				}
				g.addEdge(count, author, coAuthor);
				count++;
				System.out.println(count);
			}
		}
	}
	
	@Test
	public void testReadASingleLine() {
		final String LINE = "64409<break>64400,61691,64171,64407,62058,26933,62059,";
		String[] foo = LINE.split("<break>");
		assertEquals("64409", foo[0]);
		assertEquals("64400,61691,64171,64407,62058,26933,62059,", foo[1]);
		String[] bar = foo[1].split(",");
		assertEquals("64400", bar[0]);
		assertEquals(7, bar.length);
	}
}
