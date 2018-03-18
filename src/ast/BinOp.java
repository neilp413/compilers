package ast;

import environment.Environment;

public class BinOp extends Expression
{
    public static final String MULTIPLICATION = "*";
    public static final String DIVISION = "/";
    public static final String ADDITION = "+";
    public static final String SUBTRACTION = "-";
    public static final String MOD = "%";
    
    private String op;
    private Expression exp1;
    private Expression exp2;
    
    public BinOp(String op, Expression exp1, Expression exp2)
    {
        this.op = op;
        this.exp1 = exp1;
        this.exp2 = exp2;
    }
    
    public String getOperator()
    {
        return op;
    }
    
    public Expression getExpressionOne()
    {
        return exp1;
    }
    
    public Expression getExpressionTwo()
    {
        return exp2;
    }

    public int eval(Environment env)
    {
        switch(op)
        {
        case MULTIPLICATION:
            return exp1.eval(env) * exp2.eval(env);
        case DIVISION:
            return exp1.eval(env) / exp2.eval(env);
        case ADDITION:
            return exp1.eval(env) + exp2.eval(env);
        case SUBTRACTION:
            return exp1.eval(env) - exp2.eval(env);
        case MOD:
            return exp1.eval(env) % exp2.eval(env);
        default:
            throw new IllegalArgumentException("Invalid Operator: " + op);
        }
    }
}