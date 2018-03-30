package ast;

import java.util.*;

import environment.Environment;

public class Program
{
    private List<ProcedureDeclaration> procedures;
    private Statement stmt;
    
    public Program(List<ProcedureDeclaration> procedures, Statement stmt)
    {
        this.procedures = procedures;
        this.stmt = stmt;
    }

    public void exec(Environment env)
    {
        Iterator<ProcedureDeclaration> it = procedures.iterator();
        while(it.hasNext())
            it.next().exec(env);
        stmt.exec(env);
    }
    
    
}