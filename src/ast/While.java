package ast;

import emitter.Emitter;
import environment.Environment;

/**
 * The While class extends the Statement class
 * and acts like a while loop
 * 
 * @author Neil Patel
 * @version May 2, 2018
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
    
    /**
     *  Emits the readable lines of MIP code that compiles the condition and uses the jumps
     *  to create a while and compiles the statements that are in the while loop
     * 
     * @param e     the emitter being used to create the MIPS readable file
     */
    public void compile(Emitter e)
    {
        int labelId = e.nextLabelID();
        String end = "endWhile" + labelId;
        String loop = "loop" + labelId;
        
        e.emit(loop + ":");
        cond.compile(e, end);
        stmt.compile(e);
        e.emit("j " + loop);
        e.emit(end + ":");
    }
}