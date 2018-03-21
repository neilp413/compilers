package ast;

import environment.Environment;

/**
 * BinOp Class uses an operator to combine two expressions
 * 
 * @author Neil Patel
 * @version March 19, 2018
 */
public class BinOp extends Expression
{
    /**
     * Holds the value of the operator for multiplication
     */
    public static final String MULTIPLICATION = "*";
    /**
     * Holds the value of the operator for division
     */
    public static final String DIVISION = "/";
    /**
     * Holds the value of the operator for addition
     */
    public static final String ADDITION = "+";
    /**
     * Holds the value of the operator for subtraction
     */
    public static final String SUBTRACTION = "-";
    /**
     * Holds the value of the operator for mod
     */
    public static final String MOD = "%";
    
    private String op;
    private Expression exp1;
    private Expression exp2;
    
    /**
     * Constructor for the BinOp class
     * 
     * @param op   the operation that needs to occur
     * @param exp1 the first expression
     * @param exp2 the second expression
     */
    public BinOp(String op, Expression exp1, Expression exp2)
    {
        this.op = op;
        this.exp1 = exp1;
        this.exp2 = exp2;
    }
    
    /**
     * Gets the value of the operator instance variable
     * 
     * @return  the operation that needs to occur
     */
    public String getOperator()
    {
        return op;
    }
    
    /**
     * Gets the value of the first expression instance variable
     * 
     * @return  the first expression
     */
    public Expression getExpressionOne()
    {
        return exp1;
    }
    
    /**
     * Gets the value of the second expression instance variable
     * 
     * @return  the second expression
     */
    public Expression getExpressionTwo()
    {
        return exp2;
    }
    
    /**
     * Evaluates the BinOp object by doing the operation given
     * 
     * @param env the environment being used to store the variables
     * @return the value of the expression
     */
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