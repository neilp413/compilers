package ast;

import environment.Environment;

import emitter.Emitter;

/**
 * Abstract class for Statements
 * 
 * @author Neil Patel
 * @version April 29, 2018
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
    
    /**
     * A method that turns the an aspect of our Pascal code into a readable MIPS file 
     * 
     * @param e     the emitter being used to create the MIPS file
     */
    public  void compile(Emitter e)
    {
        System.out.println("Placeholder for compiling methods");

    }
}