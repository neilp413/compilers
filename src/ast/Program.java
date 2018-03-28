package ast;

import java.util.*;

public class Program
{
    private List<ProcedureDeclaration> procedures;
    private Statement stmt;
    
    public Program(List<ProcedureDeclaration> procedures, Statement stmt)
    {
        this.procedures = procedures;
        this.stmt = stmt;
    }
    
    
}