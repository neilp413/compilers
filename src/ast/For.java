package ast;

import environment.Environment;

/**
 * The For class extends the Statement class
 *  and acts like a For loop
 * 
 * @author Neil Patel
 * @version March 19, 2017
 *
 */
public class For extends Statement
{
    private Assignment assig;
    private Number limit;
    private Statement stmt;

    /**
     * The constructor for the For class
     * 
     * @param assig     the assignment of the variable in the for loop
     * @param limit     the limit of the initial variable
     * @param stmt      the statement that needs to be executed 
     */
    public For(Assignment assig, Number limit, Statement stmt)
    {
        this.assig = assig;
        this.limit = limit;
        this.stmt = stmt;
    }

    /**
     * Executes the For object by using an actual for loop
     * 
     * @param env   the environment to be used
     */
    public void exec(Environment env)
    {
        assig.exec(env);
        for(int k = assig.getExpression().eval(env); k < limit.eval(env); k++)
        {
            stmt.exec(env);
        }
    }
}