package differentiator.ast;

import differentiator.Type;

public class ProductExpression extends ExpressionElement {

    public ProductExpression() {
        super(Type.STAR);
    }

    public void accept(ExpressionEvaluationVisitor eev) {
        eev.visit(this);
    }

    @Override
    public String toString() {
        return "Product";
    }
}
