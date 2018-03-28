package ast;

import environment.Environment;

public class ProcedureDeclaration extends Statement
{
    private String name;
    private Statement stmt;
    
    public ProcedureDeclaration(String name, Statement stmt)
    {
        this.name = name;
        this.stmt = stmt;
    }
    
     public Statement getStatement()
     {
         return stmt;
     }

    @Override
    public void exec(Environment env)
    {
        env.setProcedure(name, stmt);
    }
}