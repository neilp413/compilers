package environment;

import java.util.*;

import ast.ProcedureCall;
import ast.ProcedureDeclaration;
import ast.Statement;
import ast.Variable;

/**
 * The Environment class sets an environment that stores all the 
 * variables being used 
 * 
 * @author Neil Patel
 * @version March 19, 2017
 *
 */
public class Environment
{
    private Map<String, Variable> vars;
    private Map<Variable, Integer> varVals;
    
    private Map<String, ProcedureDeclaration> procedures;
    private Map<ProcedureDeclaration, Statement> procedureStmts;

    /**
     * The constructor for the environment object
     */
    public Environment()
    {
        vars = new HashMap<String, Variable>();
        varVals = new HashMap<Variable, Integer>();
        procedures = new HashMap<String, ProcedureDeclaration>();
        procedureStmts = new HashMap<ProcedureDeclaration, Statement>();
    }

    /**
     * Sets and stores the value of a variable to the appropriate spot in the map
     * 
     * @param variable  the name of the variable 
     * @param value     the value of the variable
     */
    public void setVariable(String variable, int value)
    {
        Variable var = new Variable(variable);
        vars.put(variable, var);
        varVals.put(var, value);
    }

    /**
     * Retrieves the value of the variable based on the name of the variable
     * 
     * @param variable  the name of the variable
     * @return          the value of the variable
     */
    public int getVariable(String variable)
    {
        if (vars.containsKey(variable))
            return varVals.get(vars.get(variable));
        else
            throw new IllegalArgumentException("No Variable: " + variable);
    }
    
    /**
     * Removes a variable from the environment
     * 
     * @param variable  the name of the variable that needs to be removed
     */
    public void removeVariable(String variable)
    {
        if(vars.containsKey(variable))
            varVals.remove(vars.remove(variable));
        else
            throw new IllegalArgumentException("No Variable called" + variable);
    }
    
    public void setProcedure(String procedure, Statement stmt)
    {
        ProcedureDeclaration pro = new ProcedureDeclaration(procedure, stmt);
        procedures.put(procedure, pro);
//        procedureStmts.put(pro, stmt);
    }
    
    public Statement getProcedure(String procedure)
    {
        if(procedures.containsKey(procedure))
            return procedures.get(procedures).getStatement();
        else
            throw new IllegalArgumentException("No Procedure called: " + procedure);
    }

}