package emitter;

import java.io.*;
import java.util.Iterator;

import ast.ProcedureDeclaration;
import ast.Variable;

/**
 * Creates an emitter 
 *
 * @author Neil Patel
 * @version May 3, 2018
 */
public class Emitter
{
    private PrintWriter out;
    private ProcedureDeclaration currentPro;
    private int labelNum;
    private int excessStackHeight;

    /**
     * Constructor for the Emitter class
     *
     * @param outputFileName  the filename
     */	public Emitter(String outputFileName)
     {
         labelNum = 0;
         try
         {
             out = new PrintWriter(new FileWriter(outputFileName), true);
         }
         catch(IOException e)
         {
             throw new RuntimeException(e);
         }
     }


     /**
      * Prints one line of code to file (with non-labels indented)
      *
      * @param code  the code being emitted to the file
      */	public void emit(String code)
      {
          if (!code.endsWith(":"))
              code = "\t" + code;
          out.println(code);
      }

      /**
       * Closes the file being emitted
       */
      public void close()
      {
          out.close();
      }

      /**
       * Emits code that pushes a register to the stack
       *
       * @param reg   the string for the file
       */
      public void emitPush(String reg)
      {
          excessStackHeight += 4;
          emit("#pushes register " + reg + " onto the stack");
          emit("subu $sp, $sp, 4");
          emit("sw " + reg +", ($sp)");
      }

      /**
       * Emits code that pops a register from stack
       *
       * @param reg   the string for the file
       */
      public void emitPop(String reg)
      {
          excessStackHeight -= 4;
          emit("#pops register " + reg + " onto the stack");
          emit("lw " + reg + ", ($sp)");
          emit("addu $sp, $sp,4");
      }

      /**
       * Changes the label number for the file
       * 
       * @return the label number
       */
      public int nextLabelID()
      {
          labelNum++;
          return labelNum;
      }
      
      //Remembers pro as the current procedure context
      public void setProcedureContext(ProcedureDeclaration pro)
      {
          excessStackHeight = 0;
          currentPro = pro;
      }
      
      //clear current procedure context (remember null)
      public void clearProcedureContext()
      {
          currentPro = null;
      }
      
      public boolean isLocalVariable(String varName)
      {
          //Iterator<Variable> it = currentPro.getParameters().iterator();
          return (currentPro.getParamNames().indexOf(varName) != -1);
         /* while(it.hasNext())
          {
              String name = it.next().getName();
              
              if(varName.equals(name) || varName.equals(currentPro.getName()))
                  return true;
          }
          return false;*/
      }
      
      // precondition: localVarName is the name of a local variable
      // for the procedure currently being compiled
      public int getOffset(String localVarName)
      {
//          Iterator<Variable> it = currentPro.getParameters().iterator();
//          int count = 0;
          
          return 4 * (currentPro.getParameters().size() - currentPro.getParamNames().indexOf(localVarName)) + excessStackHeight;
//          while(it.hasNext())
//          {
//              if(localVarName.equals(it.next().getName()))
//                  return 4 * (currentPro.getParameters().size() - count) + excessStackHeight;
//              count++;
//          }
//          return -1;
      }
}