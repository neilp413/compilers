package ast;

import environment.Environment;

public class Number extends Expression
{
    private int value;
    
    public Number(int value)
    {
        this.value = value;
    }
    
    public int getValue()
    {
        return value;
    }

    public int eval(Environment env)
    {
        return value;
    }
}