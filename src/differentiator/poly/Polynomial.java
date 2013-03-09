package differentiator.poly;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * The Class Polynomial defines a Polynomial.
 */
public class Polynomial {

    /** The terms of the polynomial as represented by a List of
     * PolynomialTerms.
     */
    private final List<PolynomialTerm> terms;

    /**
     * Instantiates a new polynomial from a single PolynomialTerm.
     * @param term The initial term of this polynomial.
     */
    public Polynomial(PolynomialTerm term) {
        terms = new ArrayList<PolynomialTerm>(1);
        terms.add(term);
    }

    /**
     * Instantiates a new polynomial from a list of PolynomialTerms.
     * @param terms The initial terms of this polynomial.
     */
    public Polynomial(List<PolynomialTerm> terms) {
        // shallow copy okay b/c polynomial terms are immutable
        this.terms = new ArrayList<PolynomialTerm>(terms);
    }

    /**
     * Gets the terms.
     * @return the terms
     */
    public List<PolynomialTerm> getTerms() {
        return new ArrayList<PolynomialTerm>(terms);
    }

    /**
     * Return a new poynomial represented the simplified version of this
     * Polynomial.
     * @param poly The polynomial to simplify.
     * @return The simplified Polynomial.
     */
    public static Polynomial simplify(Polynomial poly) {
        Map<Map<String, Integer>, BigDecimal> newTerms =
                new HashMap<Map<String, Integer>, BigDecimal>();
        for (PolynomialTerm term : poly.terms) {
            Map<String, Integer> termVars = term.getVariables();
            if (newTerms.containsKey(termVars)) {
                newTerms.put(termVars,
                        newTerms.get(termVars)
                        .add(term.getCoefficient()));
            } else {
                newTerms.put(termVars,term.getCoefficient());
            }
        }
        List<PolynomialTerm> newTermList =
                new ArrayList<PolynomialTerm>(newTerms.size());
        for (Entry<Map<String, Integer>, BigDecimal> term :
                newTerms.entrySet()) {
            newTermList.add(new PolynomialTerm(term.getValue(), term.getKey()));
        }
        return new Polynomial(newTermList);
    }



    /**
     * Add two polynomials and return the sum.
     * @param one the one
     * @param two the two
     * @return the polynomial
     */
    public static Polynomial sum(Polynomial one, Polynomial two) {
        List<PolynomialTerm> sum =
                new ArrayList<PolynomialTerm>(
                        one.getTerms().size() + two.getTerms().size());

        sum.addAll(one.getTerms());
        sum.addAll(two.getTerms());
        return new Polynomial(sum);
    }

    /**
     * Multiply two polynomials and return their product.
     * @param one the one
     * @param two the two
     * @return the polynomial
     */
    public static Polynomial multiply(Polynomial one, Polynomial two) {
        List<PolynomialTerm> product =
                new ArrayList<PolynomialTerm>(
                        one.getTerms().size() * two.getTerms().size());

        for (PolynomialTerm x : one.getTerms()) {
            for (PolynomialTerm y : two.getTerms()) {
                product.add(PolynomialTerm.multiply(x, y));
            }
        }

        return new Polynomial(product);
    }

    public String toString() {
        StringBuilder res = new StringBuilder();
        Iterator<PolynomialTerm> polyTermIter = terms.iterator();
        while (polyTermIter.hasNext()) {
            String nextTerm = polyTermIter.next().toString();
            if (!res.toString().equals("")) {
                res.append("+");
            }
            res.append(nextTerm);
        }
        if (res.toString().equals("")) {
            res.append("0");
        }
        // we check add plus iff no start, we also need to check for ending
        // this was harder to do above so we hack it a little here.
        if (res.charAt(res.length()-1) == '+') {
            res.deleteCharAt(res.length()-1);
        }
        return res.toString();
    }
}
