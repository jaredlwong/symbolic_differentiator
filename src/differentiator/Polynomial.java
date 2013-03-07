package differentiator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

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
        Map<Set<String>, BigDecimal> newTerms = new HashMap<Set<String>, BigDecimal>();
        for (PolynomialTerm t : poly.getTerms()) {
            if (newTerms.containsKey(t.getVariableSet())) {
                BigDecimal old = newTerms.get(t.getVariableSet());
                newTerms.put(t.getVariableSet(), old.add(t.getCoefficient()));
            } else {
                newTerms.put(t.getVariableSet(), t.getCoefficient());
            }
        }
        List<PolynomialTerm> newTermList = new ArrayList<PolynomialTerm>(newTerms.size());
        for (Entry<Set<String>, BigDecimal> item : newTerms.entrySet()) {
            List<String> termVars = new ArrayList<String>();
            termVars.addAll(item.getKey());
            newTermList.add(new PolynomialTerm(item.getValue(), termVars));
        }
        return new Polynomial(newTermList);
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        for (PolynomialTerm t : terms) {
            res.append(t.getCoefficient().toString());
            for (String var : t.getVariables()) {
                res.append("*");
                res.append(var);
            }
            res.append("+");
        }
        res.deleteCharAt(res.length()-1);
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
