package differentiator.parse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import differentiator.type.Type;

public class TokenTest {
    @Test
    public void basicOperatorsTests() {
        Token lpar = Token.getInstance("(");
        assertEquals(Type.LPAR, lpar.getType());

        Token rpar = Token.getInstance(")");
        assertEquals(Type.RPAR, rpar.getType());

        Token star = Token.getInstance("*");
        assertEquals(Type.STAR, star.getType());

        Token plus = Token.getInstance("+");
        assertEquals(Type.PLUS, plus.getType());

        Token minus = Token.getInstance("-");
        assertEquals(Type.MINUS, minus.getType());

        Token slash = Token.getInstance("/");
        assertEquals(Type.SLASH, slash.getType());

        Token caret = Token.getInstance("^");
        assertEquals(Type.CARET, caret.getType());

        Token terminal = Token.getInstance("$");
        assertEquals(Type.TERMINAL, terminal.getType());

        Token integer = Token.getInstance("1234567890");
        assertEquals(Type.NUMBER, integer.getType());
        assertEquals("1234567890", integer.getValue());

        Token decimal = Token.getInstance(".12345");
        assertEquals(Type.NUMBER, decimal.getType());
        assertEquals(".12345", decimal.getValue());

        Token identifier = Token.getInstance("hello");
        assertEquals(Type.IDENTIFIER, identifier.getType());
        assertEquals("hello", identifier.getValue());
    }

    @Test
    public void basicIdentifierTests() {
        Token lower1 = Token.getInstance("a");
        assertEquals(Type.IDENTIFIER, lower1.getType());

        Token lower2 = Token.getInstance("z");
        assertEquals(Type.IDENTIFIER, lower2.getType());

        Token upper1 = Token.getInstance("A");
        assertEquals(Type.IDENTIFIER, upper1.getType());

        Token upper2 = Token.getInstance("Z");
        assertEquals(Type.IDENTIFIER, upper2.getType());

        Token lowermulti = Token.getInstance("aazz");
        assertEquals(Type.IDENTIFIER, lowermulti.getType());

        Token uppermulti = Token.getInstance("AAZZ");
        assertEquals(Type.IDENTIFIER, uppermulti.getType());

        char fullAlpha[] = new char[26*2];
        int i = 0;
        for (char c = 'a'; c <= 'z'; ++c, ++i) {
            fullAlpha[i*2] = c;
            fullAlpha[i*2+1] = Character.toUpperCase(c);
        }
        Token mixedcase = Token.getInstance(new String(fullAlpha));
        assertEquals(Type.IDENTIFIER, mixedcase.getType());
    }

    @Test
    public void basicIntegerTests() {
        Token simple = Token.getInstance("0123456789");
        assertEquals(Type.NUMBER, simple.getType());

        Token negsimple = Token.getInstance("-0123456789");
        assertEquals(Type.NUMBER, negsimple.getType());

        Token duplicate = Token.getInstance("-00112233445566778899");
        assertEquals(Type.NUMBER, duplicate.getType());
    }

    @Test
    public void basicRealTests() {
        Token simple1 = Token.getInstance("0123456789.");
        assertEquals(Type.NUMBER, simple1.getType());

        Token simple2 = Token.getInstance(".0123456789");
        assertEquals(Type.NUMBER, simple2.getType());

        Token simple3 = Token.getInstance("01234.56789");
        assertEquals(Type.NUMBER, simple3.getType());

        Token negsimple1 = Token.getInstance("-.0123456789");
        assertEquals(Type.NUMBER, negsimple1.getType());

        Token negsimple2 = Token.getInstance("-0123456789.");
        assertEquals(Type.NUMBER, negsimple2.getType());

        Token negsimple3 = Token.getInstance("-012345.6789");
        assertEquals(Type.NUMBER, negsimple3.getType());
    }

    @Test
    public void equalityTests() {
        Token int1 = Token.getInstance("1000000000000000000000000000000000");
        Token int2 = Token.getInstance("1000000000000000000000000000000000");
        assertEquals(int1,int2);
        assertEquals(int1.hashCode(), int2.hashCode());
        assertTrue(int1.equals(int2));

        Token dec1 = Token.getInstance("123.123123123123000000000000000123");
        Token dec2 = Token.getInstance("123.123123123123000000000000000123");
        assertEquals(dec1,dec2);
        assertEquals(dec1.hashCode(), dec2.hashCode());
        assertTrue(dec1.equals(dec2));
        
        Token var1 = Token.getInstance("foobar");
        Token var2 = Token.getInstance("foobar");
        assertEquals(var1, var2);
        assertEquals(var1.hashCode(),var2.hashCode());
        assertTrue(var1.equals(var2));
    }
}
