package differentiator;

import java.math.BigDecimal;
import java.util.ArrayList;
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

    public PolynomialTerm(BigDecimal _coefficient, List<String> _variables) {
        coefficient = _coefficient;
        // create shallow copy, okay because Strings 
        variables = new LinkedList<String>(_variables);
    }

    public static PolynomialTerm multiply(
            PolynomialTerm one, PolynomialTerm two) {
        List<String> newVars = new ArrayList<String>(one.variables);
        newVars.addAll(two.variables);
        BigDecimal newCoeff = one.coefficient.add(two.coefficient);
        return new PolynomialTerm(newCoeff,newVars);
    }

    public String[] getVariables() {
        return variables.toArray(new String[variables.size()]);
    }

    public BigDecimal getCoefficient() {
        return coefficient;
    }
}
