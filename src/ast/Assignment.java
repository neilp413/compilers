package ast;

import environment.Environment;

/**
 * The Assignment class extends the Statement class and
 * takes care of the assignments of variables
 * 
 * @author Neil Patel
 * @version March 19, 2018
 */
public class Assignment extends Statement
{
    private Expression exp;
    private String var;
//    private Variable variable;
    
    /**
     * Constructor for the Assignment class
     * 
     * @param var   the name of the variable
     * @param exp   the expression to be stored
     */
    public Assignment(String var, Expression exp)
    {
        this.exp = exp;
        this.var = var;
//        this.variable = new Variable(var);
    }
    
    /**
     * Returns the name of the variable to be assigned
     * 
     * @return the name of the variable
     */
    public String getVar()
    {
        return var;
    }
    
    /**
     * Returns the expression to be stored in the variable
     * 
     * @return  the expression
     */
    public Expression getExpression()
    {
        return exp;
    }
    
    /**
     * Executes the assignment object by assigning the expression 
     * to the variable
     * 
     * @param env   the environment to be used
     */
    public void exec(Environment env)
    {
//        Variable variable = new Variable(var);
        env.setVariable(var, exp.eval(env));
    }  
}