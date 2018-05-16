package ast;

import java.util.*;

import emitter.Emitter;
import environment.Environment;

/**
 * The ProcedureCall class extends the Expression class
 * and acts like a procedure is called when the object is evaluated
 * 
 * @author Neil Patel
 * @version March 30, 2017
 *
 */
public class ProcedureCall extends Expression
{
    private String name;
    private List<Expression> params;

    /**
     * The constructor for the ProcedureCall Object
     * 
     * @param name      the name of the procedure
     * @param params    the list of parameters
     */
    public ProcedureCall(String name, List<Expression> params)
    {
        this.name = name;
        this.params = params;
    }

    /**
     * Evaluates the ProcedureCall object by executing the statement of procedure
     * 
     * @param env   the environment to be used
     * @return the value of the procedure call
     */
    public int eval(Environment env)
    {
        Environment local = new Environment(env);
        ProcedureDeclaration procedure = env.getProcedure(name);
        Variable temp = new Variable(procedure.getName());
        List<Variable> paramVars = procedure.getParameters();
        Iterator<Expression> itVal = params.iterator();
        Iterator<Variable> itVar = paramVars.iterator();
        
        env.setVariable(temp.getName(), 0);   
        if(params.size() != paramVars.size())
            throw new IllegalArgumentException("Number of parameters don't"
                    + " match declaration parameters.");      
        while(itVal.hasNext() && itVar.hasNext())
        {
            local.declareVariable(itVar.next().getName(), itVal.next().eval(env));
        }
        procedure.getStatement().exec(local);
        return temp.eval(local);
    }
    
    public void compile(Emitter e)
    {
        Iterator<Expression> it = params.iterator();
        
        while(it.hasNext())
        {
            it.next().compile(e);
            e.emitPush("$v0");
        }
        e.emit("jal proc" + name);
    }
}