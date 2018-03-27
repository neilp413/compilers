
package ast;

import java.util.Scanner;

import environment.Environment;

/**
 * The Readln class extends the Statement class
 *  and stores a value gotten from the user 
 * 
 * @author Neil Patel
 * @version March 26, 2017
 *
 */
public class Readln extends Statement
{
    private Variable var;
    
    /**
     * The constructor for the Readln class
     * 
     * @param var   the variable to store the value into
     */
    public Readln(Variable var)
    {
        this.var = var;
    }

    
    /**
     * Executes the Readln object by getting input from user
     * 
     * @param env   the environment to be used
     */
    public void exec(Environment env)
    {
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter an integer: ");
        int val = scan.nextInt();
        Assignment a = new Assignment(var.getName(), new Number(val));
        a.exec(env);
    }
    
}