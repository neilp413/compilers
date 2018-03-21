package ast;

import environment.Environment;

/**
 * The Variable class extends the Expression class and
 * acts like a variable
 * 
 * @author Neil Patel
 * @version March 19, 2018
 */
public class Variable extends Expression
{
    private String name;
    
    /**
     * The constructor for the Variable class
     * 
     * @param name  the name of the variable
     */
    public Variable(String name)
    {
        this.name = name;
    }
    
    /**
     * Gets the value of the name of the string
     * 
     * @return  the name of the string
     */
    public String getName()
    {
        return name;
    }

    /**
     * Evaluates the Variable object by getting hte value of the variable
     * 
     * @param env the environment being used to store the variables
     * @return the value of the expression
     */
    public int eval(Environment env)
    {
        return env.getVariable(name);
    }
}