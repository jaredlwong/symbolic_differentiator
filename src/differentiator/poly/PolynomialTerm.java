package differentiator.poly;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * The PolynomialTerm class represents a term of a polynomial. More explicitly
 * it represents a term of the form
 * A * B ^ C
 * where A is a real number. B is a variable identifier of the form [a-zA-Z]+.
 * And C is a non-negative integer.
 */
public class PolynomialTerm {

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((coefficient == null) ? 0 : coefficient.hashCode());
        result = prime * result
                + ((variables == null) ? 0 : variables.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PolynomialTerm other = (PolynomialTerm) obj;
        if (coefficient == null) {
            if (other.coefficient != null)
                return false;
        } else if (!coefficient.equals(other.coefficient))
            return false;
        if (variables == null) {
            if (other.variables != null)
                return false;
        } else if (!variables.equals(other.variables))
            return false;
        return true;
    }

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

    /**
     * Multiply two PolynomialTerms and return their product
     * @param one The first PolynomialTerm
     * @param two The second PolynomialTerm
     * @return The product of the two PolynomialTerms.
     */
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

    /**
     * @return The variables of this PolynomialTerm.
     */
    public Map<String, Integer> getVariables() {
        return new HashMap<String, Integer>(variables);
    }

    /**
     * @return The coefficient of this PolynomialTerm.
     */
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
            res.append(coefficient.stripTrailingZeros().toPlainString());
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
