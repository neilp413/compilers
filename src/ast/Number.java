package ast;

import environment.Environment;

/**
 * The Number class acts as an expression but stores an integer value
 * 
 * @author Neil Patel
 * @version March 19, 2018
 */
public class Number extends Expression
{
    private int value;
    
    /**
     * Constructor for the Number class
     * 
     * @param value the value of the Number object
     */
    public Number(int value)
    {
        this.value = value;
    }

    /**
     * Evaluates the Number object by returing the value of the Number Object
     * 
     * @param env the environment being used to store the variables
     * @return the value of the expression
     */
    public int eval(Environment env)     
    {
        return value;
    }
}