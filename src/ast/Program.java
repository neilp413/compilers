package ast;

import java.util.*;

import environment.Environment;

/**
 * The Program class consists of a list of ProcedureDeclaration and
 * the statement to be executed
 * 
 * @author Neil Patel
 * @version March 30, 2018
 *
 */
public class Program
{
    private List<ProcedureDeclaration> procedures;
    private Statement stmt;
    
    /**
     * The constructor for the Program class
     * 
     * @param procedures    the list of ProcedureDeclaration to be declared
     * @param stmt          the statement to be executed after declaring the Procedures
     */
    public Program(List<ProcedureDeclaration> procedures, Statement stmt)
    {
        this.procedures = procedures;
        this.stmt = stmt;
    }

    /**
     * Declares all the procedures and then executes the statement
     * 
     * @param env   the environment to be used
     */
    public void exec(Environment env)
    {
        Iterator<ProcedureDeclaration> it = procedures.iterator();
        while(it.hasNext())
            it.next().exec(env);
        stmt.exec(env);
    }
    
    
}