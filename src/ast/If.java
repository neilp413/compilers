package ast;

import environment.Environment;

public class If extends Statement 
{
    private Condition cond;
    private Statement stmt;
    private Statement elseStmt;
    
    public If(Condition cond, Statement stmt)
    {
        this.cond = cond;
        this.stmt = stmt;
        this.elseStmt = null;
    }
    
    public If(Condition cond, Statement stmt, Statement elseStmt)
    {
        this.cond = cond;
        this.stmt = stmt;
        this.elseStmt = elseStmt;
    }
    
    public Condition getCondition()
    {
        return cond;
    }
    
    public Statement getStatement()
    {
        return stmt;
    }

    @Override
    public void exec(Environment env)
    {
        if(cond.eval(env))
        {
            stmt.exec(env);
        }
        else
        {
            if(elseStmt != null)
                elseStmt.exec(env);
        }
    }
}