package environment;

import java.util.*;
import ast.Variable;

public class Environment
{
    //TODO: think about storage system of variables
    private Map<String, Variable> vars;
    private Map<Variable, Integer> varVals;

    public Environment()
    {
        vars = new HashMap<String, Variable>();
        varVals = new HashMap<Variable, Integer>();
    }

    public void setVariable(String variable, int value)
    {
//        System.out.println("set variable env " + variable + value);
        Variable var = new Variable(variable);
        vars.put(variable, var);
        varVals.put(var, value);
    }

    public int getVariable(String variable)
    {
//       ß System.out.println("get variable env " + variable);
        if (vars.containsKey(variable))
            return varVals.get(vars.get(variable));
        else
            throw new IllegalArgumentException("Invalid Variable: " + variable);
    }

}