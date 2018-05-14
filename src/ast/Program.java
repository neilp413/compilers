package ast;

import java.util.*;

import environment.Environment;

import emitter.Emitter;

/**
 * The Program class consists of a list of ProcedureDeclaration and
 * the statement to be executed
 * 
 * @author Neil Patel
 * @version May 2, 2018
 *
 */
public class Program
{
    private List<ProcedureDeclaration> procedures;
    private Statement stmt;
    private List<String> vars;

    /**
     * The constructor for the Program class that takes in the list of ProcedureDeclarations and
     * the statment to be executed
     * 
     * @param procedures    the list of ProcedureDeclaration to be declared
     * @param stmt          the statement to be executed after declaring the Procedures
     */
    public Program(List<ProcedureDeclaration> procedures, Statement stmt)
    {
        this.procedures = procedures;
        this.stmt = stmt;
    }

    /**
     * The constructor for the Program class (made for converting Pascal to MIPS) 
     * that takes in the statement to be compiled and the list of declared variable names
     * 
     * @param stmt  the statement to be compiled
     * @param vars  the list of the declared variable names
     */
    public Program(Statement stmt, List<String> vars)
    {
        this.stmt = stmt;
        this.vars = vars;
    }

    /**
     * Returns the list of variable names
     * 
     * @return  the list of declared variable names
     */
    public List<String> getVars()
    {
        return vars;
    }

    /**
     * Declares all the procedures and then executes the statement
     * 
     * @param env   the environment to be used
     */
    public void exec(Environment env)
    {
        Iterator<ProcedureDeclaration> it = procedures.iterator();
        while(it.hasNext())
            it.next().exec(env);
        stmt.exec(env);
    }

    /**
     * Turns the Pascal statement into readable MIPS code and the terminate comand
     * 
     * @param e     the emitter being used to create readable MIPS code
     */
    public void compile(Emitter e)
    {
        Iterator<String> it = vars.iterator();

        
        e.emit("#@author Neil Patel");
        e.emit("#@version May 14, 2018");
        e.emit(".data");
        e.emit("newLine: .asciiz \"\\n\"");
        
        // Adds the "var" to beginning of each variable name, adds it to the MIPS code, and sets
        // the value of each variable to 0
        while(it.hasNext())
            e.emit("var" + it.next() + ": .word 0");
        
        e.emit(".text");
        e.emit(".globl main");
        e.emit("main:");
        stmt.compile(e);
        e.emit("li $v0 10");
        e.emit("syscall");
    }


}