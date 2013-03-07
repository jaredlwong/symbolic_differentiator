package differentiator;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

public class PolynomialTerm {

    private BigDecimal coefficient;
    private List<String> variables;

    public PolynomialTerm(int _coefficient, List<String> _variables) {
        coefficient = BigDecimal.valueOf(_coefficient);
        // create shallow copy, okay because Strings 
        variables = new LinkedList<String>(_variables);
    }

    public void multiply(String value) {
        if (value.matches("-?[0-9]+") ||
                value.matches("-?[0-9]*\\.[0-9]+|-?[0-9]+\\.[0-9]*")) {
            BigDecimal mult = new BigDecimal(value);
            coefficient = coefficient.multiply(mult);
        } else if (value.matches("[a-zA-Z]+")) {
            variables.add(value);
        } else {
            throw new IllegalArgumentException("Invalid multiplicand");
        }
    }
}
