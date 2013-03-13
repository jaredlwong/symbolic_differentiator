package differentiator.parse;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TokenizerTest {

    @Test
    public void basicTest() {
        String input = "foo bar";
        Tokenizer tok = new Tokenizer(input);
        String expected[] = {"foo", "bar"};
        int index = 0;
        while (tok.hasNext()) {
            assertTrue(expected[index++].equals(tok.next()));
        }
    }

    @Test
    public void allTypeTest() {
        Tokenizer tok = new Tokenizer("foo bar 100 100.200 a + * - / ^");
        String[] expected =
            {"foo","bar","100","100.200","a","+","*","-","/","^"};
        int index = 0;
        while (tok.hasNext()) {
            assertTrue(expected[index++].equals(tok.next()));
        }
    }

    @Test
    public void allTypeOddSpacingTest() {
        String input = "     foo bar100 100.200a +*-      /^        ";
        Tokenizer tok = new Tokenizer(input);
        String[] expected =
            {"foo","bar","100","100.200","a","+","*","-","/","^"};
        int index = 0;
        while (tok.hasNext()) {
            String tt = tok.next();
            assertTrue(expected[index++].equals(tt));
        }
    }

    @Test
    public void parenTest() {
        String input = " (  ( a  (boo)1.23)*-^) ";
        Tokenizer tok = new Tokenizer(input);
        String[] expected =
            {"(","(","a","(","boo",")","1.23",")","*","-","^",")"};
        int index = 0;
        while (tok.hasNext()) {
            String tt = tok.next();
            assertTrue(expected[index++].equals(tt));
        }
    }

    @Test
    public void spacingTest() {
        String input = "   (  \n (   -0+   -0.0)   *  foobar)   ";
        Tokenizer tok = new Tokenizer(input);
        String[] expected =
            {"(","(","-","0","+","-","0.0",")","*","foobar",")"};
        int index = 0;
        while (tok.hasNext()) {
            String tt = tok.next();
            assertTrue(expected[index++].equals(tt));
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void badInputTest() {
        String input = "(%(   -0+   -0.0)   *  foobar)";
        Tokenizer tok = new Tokenizer(input);
        while (tok.hasNext()) {
            tok.next();
        }
    }
}
