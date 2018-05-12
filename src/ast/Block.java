package ast;

import java.util.*;

import environment.Environment;

import emitter.Emitter;

/**
 * The Block class extends the Statement class
 * and acts like a block of code
 * 
 * @author Neil Patel
 * @version April 30, 2018
 */
public class Block extends Statement
{
    private List<Statement> stmts;
    
    /**
     * The constructor for the Block class
     * 
     * @param stmts the list of statements in the block
     */
    public Block(List<Statement> stmts)
    {
        this.stmts = stmts;
    }
    
    /**
     * Returns the list of statements in the block
     * 
     * @return  the list of statements in the block
     */
    public List<Statement> getStatements()
    {
        return stmts;
    }

    /**
     * Executes all of the statements inside of the Block object
     * 
     * @param env   the environment to be used
     */
    public void exec(Environment env)
    {
        Iterator<Statement> it = stmts.iterator();
        while(it.hasNext())
            it.next().exec(env);
    }
    
    /**
     *  Compiles all of the statements with the block which basically turns all the Pascal
     *  code within the statement to MIPS code
     * 
     * @param e     the emitter that is being used to create a readable MIPS file
     */
    public void compile(Emitter e)
    {
        Iterator<Statement> it = stmts.iterator();
        while(it.hasNext())
            it.next().compile(e);
    }
}