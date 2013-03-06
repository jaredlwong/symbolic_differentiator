package differentiator;

public interface Evaluatable<E> {
    public void accept(EvaluatorVisitor<E> ev);
}
