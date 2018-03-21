package ast;

import environment.Environment;

/**
 * Abstract class for Statements
 * 
 * @author Neil Patel
 * @version March 19, 2018
 *
 */
public abstract class Statement
{
    /**
     * Executes the statement object
     * 
     * @param env the environment being used to store the variables
     */
    public abstract void exec(Environment env);
}