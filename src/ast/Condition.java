package ast;

import emitter.Emitter;
import environment.Environment;

/**
 * The Condition class deals acts as a conditional that returns a boolean
 * 
 * @author Neil Patel
 * @version May 2, 2018
 *
 */
public class Condition 
{
    /**
     * Holds the value of the operator for equals to
     */
    public static final String EQUAL = "=";
    /**
     * Holds the value of the operator for not equals to
     */
    public static final String NOT_EQUAL = "<>";
    /**
     * Holds the value of the operator for less than
     */
    public static final String LESS = "<";
    /**
     * Holds the value of the operator for greater than
     */
    public static final String GREATER = ">";
    /**
     * Holds the value of the operator for less than or equal to
     */
    public static final String LESS_EQUAL = "<=";
    /**
     * Holds the value of the operator for greater than or equal to
     */
    public static final String GREATER_EQUAL = ">=";

    private String relop;
    private Expression exp1;
    private Expression exp2;

    /**
     * The constructor of the Condition class
     * 
     * @param relop the operator to compare the two expressions
     * @param exp1  the first expression
     * @param exp2  the second expression
     */
    public Condition(String relop, Expression exp1, Expression exp2)
    {
        this.relop = relop;
        this.exp1 = exp1;
        this.exp2 = exp2;
    }

    /**
     * Returns the operator to compare the two expressions
     * 
     * @return  the operator to compare the two expressions
     */
    public String getRelop()
    {
        return relop;
    }

    /**
     * Returns the first expression
     * 
     * @return  the first expression
     */
    public Expression getExpressionOne()
    {
        return exp1;
    }

    /**
     * Returns the second expression
     * 
     * @return the second expression
     */
    public Expression getExpressionTwo()
    {
        return exp2;
    }

    /**
     * Evaluates the Condition by comparing the two expressions 
     * 
     * @param env   the environment to be used
     * @return      the value of the condition
     */
    public boolean eval(Environment env)
    {
        switch(relop)
        {
            case EQUAL:
                return exp1.eval(env) == exp2.eval(env);
            case NOT_EQUAL:
                return exp1.eval(env) != exp2.eval(env);
            case LESS:
                return exp1.eval(env) < exp2.eval(env);
            case GREATER:
                return exp1.eval(env) > exp2.eval(env);
            case LESS_EQUAL: 
                return exp1.eval(env) <= exp2.eval(env);
            case GREATER_EQUAL: 
                return exp1.eval(env) >= exp2.eval(env);
            default:
                throw new IllegalArgumentException("Invalid Operator " + relop);
        }
    }

    /**
     *  Emits the readable lines of MIP code that places the value of the first expression to 
     *  register $t0 then places the value of the second expression into the register 
     *  into the register $v0. Then does the correct comparison and places a label
     * 
     * @param e     the emitter being used to create the MIPS readable file
     * @param label the label to jump if the condition is met
     */
    public void compile(Emitter e, String label)
    {
        exp1.compile(e);
        e.emit("move $t1, $v0");
        exp2.compile(e);

        switch(relop)
        {
            case EQUAL:
                e.emit("bne $t1, $v0, " + label);
                break;
            case NOT_EQUAL:
                e.emit("beq $t1, $v0, " + label);
                break;
            case LESS:
                e.emit("bge $t1, $v0, " + label);
                break;
            case GREATER:
                e.emit("ble $t1, $v0, " + label);
                break;
            case LESS_EQUAL:
                e.emit("bgt $t1, $v0, " + label);
                break;
            case GREATER_EQUAL:
                e.emit("blt $t1, $v0, " + label);
                break;
            default:
                throw new IllegalArgumentException("Invalid Operator " + relop);
        }
    }
}