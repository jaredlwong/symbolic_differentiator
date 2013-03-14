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
     * @throws NullPointerException if the specified terms are null
     */
    public Polynomial(PolynomialTerm ... _terms) {
        if (_terms == null) {
            throw new NullPointerException("The terms were null.");
        }
        terms = new ArrayList<PolynomialTerm>(_terms.length);
        for (PolynomialTerm pt : _terms) {
            terms.add(pt);
        }
    }

    /**
     * Instantiates a new polynomial from a list of PolynomialTerms.
     * @param terms The initial terms of this polynomial.
     * @throws NullPointerException if the specified terms were null
     */
    public Polynomial(List<PolynomialTerm> _terms) {
        if (_terms == null) {
            throw new NullPointerException("The terms were null.");
        }
        // shallow copy okay b/c polynomial terms are immutable
        terms = new ArrayList<PolynomialTerm>(_terms);
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
            if (term.getValue().compareTo(BigDecimal.valueOf(0)) != 0) {
                newTermList.add(
                    new PolynomialTerm(term.getValue(), term.getKey()));
            }
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

    /**
     * Print out an appropriate string representation of this polynomial.
     */
    @Override
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

    /**
     * This method converts terms to a map. This should be used explicitly for
     * equals, hashCode, and any similar methods.
     * @return A Map of the terms in this polynomial
     */
    private Map<PolynomialTerm, Integer> getTermsMap() {
        Map<PolynomialTerm, Integer> termsMap =
                new HashMap<PolynomialTerm, Integer>();
        for (PolynomialTerm term : terms) {
            if (termsMap.containsKey(term)) {
                termsMap.put(term, termsMap.get(term) + 1);
            } else {
                termsMap.put(term, 1);
            }
        }
        return termsMap;
    }

    /**
     * Two polynomials have the same hash code if they have the same polynomial
     * terms. This does not mean that a polynomial is necessarily equal to its
     * simplified polynomial.
     */
    @Override
    public int hashCode() {
        Map<PolynomialTerm, Integer> termsMap = this.getTermsMap();
        final int prime = 31;
        int result = 1;
        result = prime * result + ((termsMap == null) ?
                0 : termsMap.hashCode());
        return result;
    }

    /**
     * Two polynomials are equal if they have the same polynomial terms. This
     * does not mean that a polynomial is necessarily equal to its simplified
     * polynomial.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Polynomial other = (Polynomial) obj;
        Map<PolynomialTerm, Integer> termsMap = this.getTermsMap();
        Map<PolynomialTerm, Integer> otherTermsMap = other.getTermsMap();
        if (termsMap == null) {
            if (otherTermsMap != null) {
                return false;
            }
        } else if (!termsMap.equals(otherTermsMap)) {
            return false;
        }
        return true;
    }
}
