package differentiator;

public interface EvaluatorVisitor<E> {
    public void evaluate(Evaluatable<E> e);
}