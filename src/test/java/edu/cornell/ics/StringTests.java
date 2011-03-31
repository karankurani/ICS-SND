package edu.cornell.ics;

import junit.framework.TestCase;
import org.junit.Test;

public class StringTests extends TestCase {
    @Test
    public void ReplaceAll() {
        String s = "#*Karan";
        s = s.replaceAll("#\\*", "");
        System.out.println(s);
    }
}
