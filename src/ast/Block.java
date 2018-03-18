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

    @Override
    public void exec(Environment env)
    {
        
    }
}