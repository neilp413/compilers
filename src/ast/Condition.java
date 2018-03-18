package ast;

import environment.Environment;

public class Condition 
{
    public static final String EQUAL = "=";
    public static final String NOT_EQUAL = "<>";
    public static final String LESS = "<";
    public static final String GREATER = ">";
    public static final String LESS_EQUAL = "<=";
    public static final String GREATER_EQUAL = ">=";
    
    private String relop;
    private Expression exp1;
    private Expression exp2;
    
    public Condition(String relop, Expression exp1, Expression exp2)
    {
        this.relop = relop;
        this.exp1 = exp1;
        this.exp2 = exp2;
    }
    
    public String getRelop()
    {
        return relop;
    }
    
    public Expression getExpressionOne()
    {
        return exp1;
    }
    
    public Expression getExpressionTwo()
    {
        return exp2;
    }

    public boolean eval(Environment env)
    {
        switch(relop)
        {
        case EQUAL:
            return exp1.eval(env) == exp2.eval(env);
        case NOT_EQUAL:
            return exp1.eval(env) != exp2.eval(env);
        case LESS:
            return exp1.eval(env) < exp2.eval(env);
        case GREATER:
            return exp1.eval(env) > exp2.eval(env);
        case LESS_EQUAL: 
            return exp1.eval(env) <= exp2.eval(env);
        case GREATER_EQUAL: 
            return exp1.eval(env) >= exp2.eval(env);
         default:
             throw new IllegalArgumentException("Invalid Operator " + relop);
        }
    }
}