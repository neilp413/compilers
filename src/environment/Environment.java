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
 * @version March 30, 2017
 *
 */
public class Environment
{
    private Map<String, Integer> vars;

    private Map<String, ProcedureDeclaration> procedures;

    private Environment parent;
    private Environment global;

    /**
     * The constructor for the environment object
     * 
     * @param parent    the parent environment
     */
    public Environment(Environment parent)
    {
        vars = new HashMap<String, Integer>();
        procedures = new HashMap<String, ProcedureDeclaration>();
        this.parent = parent;
        this.global = this.getGlobal(this);
    }

    /**
     * Returns the parent environment
     * 
     * @return  the parent environment
     */
    public Environment getParent()
    {
        return parent;
    }

    /**
     * Returns the global environment
     * 
     * @param e the environment to get the global environment
     * @return  the global environment
     */
    public Environment getGlobal(Environment e)
    {
        if(e.getParent() == null)
            return e;
        return getGlobal(e.getParent());

    }

    /**
     * Sets and stores the value of a variable in this environment
     * 
     * @param variable  the name of the variable 
     * @param value     the value of the variable
     */
    public void declareVariable(String variable, int value)
    {
        vars.put(variable, value);
    }

    /**
     * Puts a variable in the global environment if it isn't in the current environment
     * 
     * @param variable  the name of the variable 
     * @param value     the value of the variable
     */
    public void setVariable(String variable, int value)
    {
        if(!vars.containsKey(variable))
            global.declareVariable(variable, value);
        else
            this.declareVariable(variable, value);
    }

    /**
     * Retrieves the value of the variable based on the name of the variable
     * and also checks if it is in the global environment
     * 
     * @param variable  the name of the variable
     * @return          the value of the variable
     */
    public int getVariable(String variable)
    {
        if (vars.containsKey(variable))
            return vars.get(variable);
        else if(this == global)
            throw new IllegalArgumentException("No Variable: " + variable);
        else
            return global.vars.get(variable);
    }

    /**
     * Removes a variable from the environment if not in this environment
     * checks in the global environment
     * 
     * @param variable  the name of the variable that needs to be removed
     */
    public void removeVariable(String variable)
    {
        if(vars.containsKey(variable))
            vars.remove(variable);
        else if(this == global)
            throw new IllegalArgumentException("No Variable called" + variable);
        else
            global.vars.remove(variable);
    }

    /**
     * Stores a ProcedureDeclaration in the global environment
     * 
     * @param procedure the name of the procedure
     * @param pro       the ProcedureDeclaration that gets set
     */
    public void setProcedure(String procedure, ProcedureDeclaration pro)
    {
        global.procedures.put(procedure, pro);
    }

    /**
     * Gets the correct ProcedureDeclaration from the name 
     * 
     * @param procedure the name of the procedure
     * @return  the ProcedureDeclaration that has been retrieved
     */
    public ProcedureDeclaration getProcedure(String procedure)
    {
        if(global.procedures.containsKey(procedure))
            return global.procedures.get(procedure);
        else
            throw new IllegalArgumentException("No Procedure called: " + procedure);
    }



}