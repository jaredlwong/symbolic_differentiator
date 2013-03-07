package differentiator.type;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TypeTest {

    /** Simple test to ensure operator precedence is working. */
    @Test
    public void basicTest() {
        for (Type t : Type.values()) {
            if (!(t == Type.TERMINAL || t == Type.RPAR)) {
                assertTrue( -1 == Type.TERMINAL.comparePrecedence(t));
            }
        }
    }
}
