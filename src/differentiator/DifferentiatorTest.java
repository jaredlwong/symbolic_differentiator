package differentiator;

import differentiator.ast.ExpressionElement;

//import org.junit.Test;

public class DifferentiatorTest {

    public static void main(String args[]) {
        Lexer lexer = Lexer.INSTANCE;
        Parser parser = Parser.INSTANCE;

        String input = "(x-1)";
        lexer.setInput(input);

        parser.setLexer(lexer);
        ExpressionElement exp = parser.getParseTree();
        System.out.println(exp.printTreeInfix());
        ExpressionElement diffExp =
                Differentiator.differentiate(exp, Token.getInstance("x"));
        System.out.println(diffExp.printEvaluationString());
        diffExp = ExpressionElement.simplify(diffExp);
        System.out.println(diffExp.printEvaluationString());
    }
}
