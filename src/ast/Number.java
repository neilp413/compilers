package ast;

import environment.Environment;

import emitter.Emitter;

/**
 * The Number class acts as an expression but stores an integer value
 * 
 * @author Neil Patel
 * @version April 29, 2018
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
     * Evaluates the Number object by returning the value of the Number Object
     * 
     * @param env the environment being used to store the variables
     * @return the value of the expression
     */
    public int eval(Environment env)     
    {
        return value;
    }
    
    /**
     * Emits a line  of MIPS code that stores the value of the Number object 
     * into the register $v0 by using the emitter
     * 
     * @param e     the emitter being used the create the MIPS file
     */
    public void compile(Emitter e)
    {
        e.emit("li $v0 " + value);
    }
}