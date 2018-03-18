package ast;

import environment.Environment;

public class Writeln extends Statement
{
    private Expression exp;
    
    public Writeln(Expression exp)
    {
        this.exp = exp;
    }
    
    public Expression getExpression()
    {
        return exp;
    }
    
    public void exec(Environment env)
    {
        System.out.println(exp.eval(env));
    }

}