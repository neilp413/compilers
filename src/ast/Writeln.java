package ast;

import environment.Environment;

import emitter.Emitter;

/**
 * The Writeln class extends the Statement class and
 * prints the evaluated expression 
 * 
 * @author Neil Patel
 * @version April 29, 2018
 *
 */
public class Writeln extends Statement
{
    private Expression exp;
    
    /**
     * The constructor for the Writeln class
     * 
     * @param exp   the expression to be printed
     */
    public Writeln(Expression exp)
    {
        this.exp = exp;
    }
    
    /**
     * Executes the Writeln Object by printing the evaluated expression
     * 
     * @param env   the environment to be used 
     */
    public void exec(Environment env)
    {
        System.out.println(exp.eval(env));
    }
    
    /**
     * Emits MIPS code that will print the value stored in the register $v0 onto the console
     * and move to the next line of the console by using the emitter class
     * 
     * @param e     the emitter being used to create the MIPS file
     */
    public void compile(Emitter e)
    {
        exp.compile(e);
        e.emit("move $a0 $v0");
        e.emit("li $v0 1");
        e.emit("syscall");
        e.emit("la $a0 newLine");
        e.emit("li $v0 4");
        e.emit("syscall");
    }

}