package differentiator.poly;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class PolynomialTerm {

    private BigDecimal coefficient;
    private Map<String, Integer> variables;

    /**
     * This constructor takes a coefficient and a map of variables along with
     * their powers.
     * @param _coefficient
     * @param _variables
     */
    public <N extends Number> PolynomialTerm(
            N _coefficient, Map<String, Integer> _variables) {
        coefficient = BigDecimal.valueOf(_coefficient.doubleValue());
        // create shallow copy, okay because Strings 
        variables = new HashMap<String, Integer>(_variables);
    }

    /**
     * This constructor takes a coefficient and any number of variables.
     * @param _coefficient
     * @param _variables
     */
    public <N extends Number> PolynomialTerm(
            N _coefficient, String ... _variables) {
        coefficient = BigDecimal.valueOf(_coefficient.doubleValue());
        variables = new HashMap<String, Integer>();
        for (String _variable : _variables) {
            if (variables.containsKey(_variable)) {
                variables.put(_variable, variables.get(_variable) + 1);
            } else {
                variables.put(_variable, 1);
            }
        }
    }

    /**
     * This constructor assumes that this term has a coefficient of 1.
     * @param _coefficient
     * @param _variables
     */
    public <N extends Number> PolynomialTerm(String ... _variables) {
        coefficient = BigDecimal.valueOf(1);
        variables = new HashMap<String, Integer>();
        for (String _variable : _variables) {
            if (variables.containsKey(_variable)) {
                variables.put(_variable, variables.get(_variable) + 1);
            } else {
                variables.put(_variable, 1);
            }
        }
    }

    public static PolynomialTerm multiply(
            PolynomialTerm one, PolynomialTerm two) {
        // Shallow copy okay because String, Integer both immutable
        Map<String, Integer> newVariables =
                new HashMap<String, Integer>(one.variables);
        for ( Entry<String, Integer> varItem : two.variables.entrySet()) {
            if (newVariables.containsKey(varItem.getKey())) {
                newVariables.put(varItem.getKey(),
                        newVariables.get(varItem.getKey())
                        + varItem.getValue());
            } else {
                newVariables.put(varItem.getKey(), varItem.getValue());
            }
        }
        BigDecimal newCoeff = one.coefficient.multiply(two.coefficient);
        return new PolynomialTerm(newCoeff, newVariables);
    }

    public Map<String, Integer> getVariables() {
        return new HashMap<String, Integer>(variables);
    }

    public BigDecimal getCoefficient() {
        return coefficient;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        if (coefficient.compareTo(BigDecimal.valueOf(0)) == 0) {
            return "";
        }
        if (coefficient.compareTo(BigDecimal.valueOf(1)) != 0 ||
                variables.size() == 0) {
            res.append(coefficient.stripTrailingZeros());
        }
        for (Entry<String, Integer> varItem : variables.entrySet()) {
            if (res.length() > 0) {
                res.append("*");
            }
            if (varItem.getValue() != 0) {
                res.append(varItem.getKey());
                if (varItem.getValue() > 1) {
                    res.append("^");
                    res.append(varItem.getValue());
                } else if (varItem.getValue() < 0) {
                    res.append("^");
                    res.append("(");
                    res.append(varItem.getValue());
                    res.append(")");
                }
            }
        }
        return res.toString();
    }
}