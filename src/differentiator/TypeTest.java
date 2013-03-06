package differentiator;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TypeTest {
    @Test
    public void basicTest() {
        for (Type t : Type.values()) {
            if (!(t == Type.TERMINAL || t == Type.RPAR)) {
                assertTrue( -1 == Type.TERMINAL.comparePrecedence(t));
            }
        }
    }
}
