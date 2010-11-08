package ICS.SND.Tests;

import junit.framework.TestCase;

public class StringTests extends TestCase {
    public void testReplaceAll() {
        String s = "#*Karan";
        s = s.replaceAll("#\\*", "");
        System.out.println(s);
    }
}
