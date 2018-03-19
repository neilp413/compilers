package ast;

import environment.Environment;

public class For extends Statement
{
    private Assignment assig;
    private Number limit;
    private Statement stmt;
    
    public For(Assignment assig, Number limit, Statement stmt)
    {
        this.assig = assig;
        this.limit = limit;
        this.stmt = stmt;
    }


    public void exec(Environment env)
    {
        assig.exec(env);
       for(int k = assig.getExpression().eval(env); k < limit.getValue(); k++)
       {
           stmt.exec(env);
       }
    }
}