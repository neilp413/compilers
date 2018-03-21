package ast;

import environment.Environment;

/**
 * The While class extends the Statement class
 * and acts like a while loop
 * 
 * @author Neil Patel
 * @version March 19, 2018
 *
 */
public class While extends Statement
{
    private Condition cond;
    private Statement stmt;
    
    /**
     *  The constructor for the While class
     * 
     * @param cond  the condition to be met
     * @param stmt  the statement to be executed if the condition is met
     */
    public While(Condition cond, Statement stmt)
    {
        this.cond = cond;
        this.stmt = stmt;
    }
    
    /**
     * Executes the current While Object
     * 
     * @param env   the environment to be used
     */
    public void exec(Environment env)
    {
        while(cond.eval(env))
        {
            stmt.exec(env);
        }
    }
}