package ast;

import java.util.*;
import environment.Environment;

public class Block extends Statement
{
    private List<Statement> stmts;
    
    public Block(List<Statement> stmts)
    {
        this.stmts = stmts;
    }
    
    public List<Statement> getStatements()
    {
        return stmts;
    }

    public void exec(Environment env)
    {
        Iterator<Statement> it = stmts.iterator();
        while(it.hasNext())
            it.next().exec(env);
    }
}