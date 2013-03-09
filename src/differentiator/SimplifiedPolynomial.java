package differentiator;

import differentiator.ast.ExpressionElement;
import differentiator.poly.GeneratePolynomialVisitor;
import differentiator.poly.Polynomial;

/**
 * The Class SimplifiedPolynomial has a single method, getInstance that
 * returns an instance of a Polynomial that is simplified.
 * 
 * NOTE: ideally this would not be a class in and of itself, but it had to
 * be included for some reason.
 */
public final class SimplifiedPolynomial {

    /**
     * Gets an instance of Polynomial that is simplified.
     *
     * @param abstractSyntaxTree the abstract syntax tree in the form of an
     * ExpressionElement
     * @return A simplified polynomial representation of the abstract syntax
     * tree
     */
    public static Polynomial getInstance(ExpressionElement abstractSyntaxTree) {
        Polynomial representation =
                abstractSyntaxTree.accept(new GeneratePolynomialVisitor());
        return Polynomial.simplify(representation);
    }
}
