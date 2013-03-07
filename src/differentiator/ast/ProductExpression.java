package differentiator.ast;

import differentiator.type.Type;

public class ProductExpression extends ExpressionElement {

    public ProductExpression() {
        super(Type.STAR);
    }

    public <R> R accept(ExpressionEvaluationVisitor<R> eev) {
        return eev.visit(this);
    }

    @Override
    public String toString() {
        return "Product";
    }

    @Override
    public String interpret() {
        StringBuilder result = new StringBuilder();
        result.append("(");
        result.append(this.getLeft().interpret());
        result.append("*");
        result.append(this.getRight().interpret());
        result.append(")");
        return result.toString();
    }
}
