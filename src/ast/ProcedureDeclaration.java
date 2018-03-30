package ast;

import java.util.*;

import environment.Environment;

/**
 * The ProcedureDeclaration class extends the Statement class and
 * acts like procedure declaration
 * 
 * @author Neil Patel
 * @version March 30, 2018
 *
 */
public class ProcedureDeclaration extends Statement
{
    private String name;
    private Statement stmt;
    private List<Variable> params;

    /**
     * The constructor for the ProcedureDeclaration class
     * 
     * @param name  the name of the procedure
     * @param stmt  the statement to be executed as a procedure
     * @param params    the list of parameter
     */
    public ProcedureDeclaration(String name, Statement stmt, List<Variable> params)
    {
        this.name = name;
        this.stmt = stmt;
        this.params = params;
    }

    /**
     * Gets the name of the procedure
     * 
     * @return  the name of the procedure
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * Gets the statement that's inside the procedure declaration
     * 
     * @return  the statement
     */
    public Statement getStatement()
    {
        return stmt;
    }

    /**
     * Gets the list of parameters
     * 
     * @return  the list of parameters
     */
    public List<Variable> getParameters()
    {
        return params;
    }

    /**
     * Executes the procedure declaration by setting ProcedureDeclaration in the map 
     * 
     * @param env   the environment to be used
     */
    public void exec(Environment env)
    {
        env.setProcedure(name, this);
    }
}