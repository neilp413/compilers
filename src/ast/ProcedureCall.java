package ast;

import environment.Environment;

public class ProcedureCall extends Expression
{
    private String name;
    
    public ProcedureCall(String name)
    {
        this.name = name;
    }

    public int eval(Environment env)
    {
        return env.getProcedure(name).exec(env);
    }
}