package ast;

import environment.Environment;

/**
 * Abstract class for expressions
 * 
 * @author Neil Patel
 * @version March 19, 2018
 */
public abstract class Expression
{
    /**
     * Evaluates the expression object
     * 
     * @param env the environment being used to store the variables
     * @return the value of the expression
     */
    public abstract int eval(Environment env);
}