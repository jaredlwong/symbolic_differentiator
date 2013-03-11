package differentiator.type;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TypeTest {
    /**
     * Make sure operator precedence is working. Specifically that the
     * Terminal type is always less than another terminal or right parenthesis.
     */
    @Test
    public void basicTest() {
        for (Type t : Type.values()) {
            if (!(t == Type.TERMINAL || t == Type.RPAR)) {
                assertTrue( -1 == Type.TERMINAL.comparePrecedence(t));
            }
        }
    }
}
