package ast;

import environment.Environment;

public class Variable extends Expression
{
    private String name;
    
    public Variable(String name)
    {
        this.name = name;
    }
    
    public String getName()
    {
        return name;
    }

    public int eval(Environment env)
    {
        return env.getVariable(name);
    }
}