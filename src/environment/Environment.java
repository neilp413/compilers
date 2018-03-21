package environment;

import java.util.*;
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

    /**
     * The constructor for the environment object
     */
    public Environment()
    {
        vars = new HashMap<String, Variable>();
        varVals = new HashMap<Variable, Integer>();
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

}