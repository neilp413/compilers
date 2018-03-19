package ast;

import environment.Environment;

public class While extends Statement
{
    private Condition cond;
    private Statement stmt;
    
    public While(Condition cond, Statement stmt)
    {
        this.cond = cond;
        this.stmt = stmt;
    }
    

    public void exec(Environment env)
    {
        while(cond.eval(env))
        {
            stmt.exec(env);
        }
    }
}