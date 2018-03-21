package ast;

import environment.Environment;

/**
 * The Condition class deals acts as a conditional that returns a boolean
 * 
 * @author Neil Patel
 * @version March 19, 2018
 *
 */
public class Condition 
{
    /**
     * Holds the value of the operator for equals to
     */
    public static final String EQUAL = "=";
    /**
     * Holds the value of the operator for not equals to
     */
    public static final String NOT_EQUAL = "<>";
    /**
     * Holds the value of the operator for less than
     */
    public static final String LESS = "<";
    /**
     * Holds the value of the operator for greater than
     */
    public static final String GREATER = ">";
    /**
     * Holds the value of the operator for less than or equal to
     */
    public static final String LESS_EQUAL = "<=";
    /**
     * Holds the value of the operator for greater than or equal to
     */
    public static final String GREATER_EQUAL = ">=";

    private String relop;
    private Expression exp1;
    private Expression exp2;

    /**
     * The constructor of the Condition class
     * 
     * @param relop the operator to compare the two expressions
     * @param exp1  the first expression
     * @param exp2  the second expression
     */
    public Condition(String relop, Expression exp1, Expression exp2)
    {
        this.relop = relop;
        this.exp1 = exp1;
        this.exp2 = exp2;
    }

    /**
     * Returns the operator to compare the two expressions
     * 
     * @return  the operator to compare the two expressions
     */
    public String getRelop()
    {
        return relop;
    }

    /**
     * Returns the first expression
     * 
     * @return  the first expression
     */
    public Expression getExpressionOne()
    {
        return exp1;
    }

    /**
     * Returns the second expression
     * 
     * @return the second expression
     */
    public Expression getExpressionTwo()
    {
        return exp2;
    }

    /**
     * Evaluates the Condition by comparing the two expressions 
     * 
     * @param env   the environment to be used
     * @return      the value of the condition
     */
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