package ast;

import emitter.Emitter;
import environment.Environment;

/**
 * The If class extends the Statement class and
 * acts like an if block with the implementation of else
 * 
 * @author Neil Patel
 * @version May 2, 2018
 */
public class If extends Statement 
{
    private Condition cond;
    private Statement stmt;
    private Statement elseStmt;
    
    /**
     * The constructor for the If class if no else statement is present
     * 
     * @param cond  the condition to be met
     * @param stmt  the statement to be executed if the condition is met
     */
    public If(Condition cond, Statement stmt)
    {
        this.cond = cond;
        this.stmt = stmt;
        this.elseStmt = null;
    }
    
    /**
     * The constructor for the If class if there is an else statement
     * 
     * @param cond      the condition to be met
     * @param stmt      the statement to be executed if the condition is met
     * @param elseStmt  the statement to be exectued if the condition isn't met
     */
    public If(Condition cond, Statement stmt, Statement elseStmt)
    {
        this.cond = cond;
        this.stmt = stmt;
        this.elseStmt = elseStmt;
    }

    
    /**
     * Executed the current If object
     * 
     * @param env   the environment to be used
     */
    public void exec(Environment env)
    {
        if(cond.eval(env))
        {
            stmt.exec(env);
        }
        else
        {
            if(elseStmt != null)
                elseStmt.exec(env);
        }
    }
    
    /**
     *  Emits the readable lines of MIP code that compiles the condition and the statements 
     *  that follow the if/if-else statement
     * 
     * @param e     the emitter being used to create the MIPS readable file
     */
    public void compile(Emitter e)
    {
        String end = "endIf" + e.nextLabelID();
        
        if(elseStmt != null)
        {
            cond.compile(e, end);
            stmt.compile(e);
            e.emit(end + ":");
            elseStmt.compile(e);

        }
        else
        {
            cond.compile(e, end);
            stmt.compile(e);
            e.emit(end + ":");
        }
    }
}