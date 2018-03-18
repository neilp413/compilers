package ast;

import environment.Environment;

public class Assignment extends Statement
{
    private Expression exp;
    private String var;
    
    public Assignment(String var, Expression exp)
    {
        this.exp = exp;
        this.var = var;
    }
    
    public String getVar()
    {
        return var;
    }
    
    public Expression getExpression()
    {
        return exp;
    }
    
    public void exec(Environment env)
    {
        env.setVariable(var, exp.eval(env));
    }
    
}