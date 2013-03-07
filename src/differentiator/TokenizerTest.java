package differentiator;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TokenizerTest {

    @Test
    public void basicTest() {
        Tokenizer tok = Tokenizer.INSTANCE;
        tok.setInput("foo bar");
        String expected[] = {"foo", "bar"};
        int index = 0;
        while (tok.hasNext()) {
            assertTrue(expected[index++].equals(tok.next()));
        }
    }

    @Test
    public void allTypeTest() {
        Tokenizer tok = Tokenizer.INSTANCE;
        tok.setInput("foo bar 100 100.200 a + * - / ^");
        String expected[] = {"foo","bar","100","100.200","a","+","*","-","/","^"};
        int index = 0;
        while (tok.hasNext()) {
            assertTrue(expected[index++].equals(tok.next()));
        }
    }

    @Test
    public void allTypeOddSpacingTest() {
        Tokenizer tok = Tokenizer.INSTANCE;
        tok.setInput("     foo bar100 100.200a +*-      /^        ");
        String expected[] = {"foo","bar","100","100.200","a","+","*","-","/","^"};
        int index = 0;
        while (tok.hasNext()) {
            String tt = tok.next();
            assertTrue(expected[index++].equals(tt));
        }
    }

    @Test
    public void parenTest() {
        Tokenizer tok = Tokenizer.INSTANCE;
        tok.setInput(" (  ( a  (boo)1.23)*-^) ");
        String expected[] = {"(","(","a","(","boo",")","1.23",")","*","-","^",")"};
        int index = 0;
        while (tok.hasNext()) {
            String tt = tok.next();
            assertTrue(expected[index++].equals(tt));
        }
    }

    @Test
    public void spacingTest() {
        Tokenizer tok = Tokenizer.INSTANCE;
        tok.setInput("   (  \n (   -0+   -0.0)   *  foobar)   ");
        String expected[] = {"(","(","-","0","+","-","0.0",")","*","foobar",")"};
        int index = 0;
        while (tok.hasNext()) {
            String tt = tok.next();
            assertTrue(expected[index++].equals(tt));
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void badInputTest() {
        Tokenizer tok = Tokenizer.INSTANCE;
        tok.setInput("(%(   -0+   -0.0)   *  foobar)");
        while (tok.hasNext()) {
            tok.next();
        }
    }
}
