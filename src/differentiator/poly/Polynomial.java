package differentiator.poly;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Polynomial {
    private final List<PolynomialTerm> terms;

    public Polynomial(PolynomialTerm term) {
        terms = new ArrayList<PolynomialTerm>(1);
        terms.add(term);
    }

    public Polynomial(List<PolynomialTerm> terms) {
        // shallow copy okay b/c polynomial terms are immutable
        this.terms = new ArrayList<PolynomialTerm>(terms);
    }

    public List<PolynomialTerm> getTerms() {
        return terms;
    }

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

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        Iterator<PolynomialTerm> polyTermIter = terms.iterator();
        res.append(polyTermIter.next().toString());
        while (polyTermIter.hasNext()) {
            String nextTerm = polyTermIter.next().toString();
            if (!nextTerm.equals("")) {
                res.append("+");
                res.append(nextTerm);
            }
        }
        return res.toString();
    }

    public static Polynomial sum(Polynomial one, Polynomial two) {
        List<PolynomialTerm> sum =
                new ArrayList<PolynomialTerm>(
                        one.getTerms().size() + two.getTerms().size());

        sum.addAll(one.getTerms());
        sum.addAll(two.getTerms());
        return new Polynomial(sum);
    }

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
}
