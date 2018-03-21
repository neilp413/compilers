package ast;

import environment.Environment;

/**
 * The Writeln class extends the Statement class and
 * prints the evaluated expression 
 * 
 * @author Neil Patel
 * @version March 19, 2018
 *
 */
public class Writeln extends Statement
{
    private Expression exp;
    
    /**
     * The constructor for the Writeln class
     * 
     * @param exp   the expression to be printed
     */
    public Writeln(Expression exp)
    {
        this.exp = exp;
    }
    
    /**
     * Executes the Writeln Object by printing the evaluated expression
     * 
     * @param env   the environment to be used 
     */
    public void exec(Environment env)
    {
        System.out.println(exp.eval(env));
    }

}