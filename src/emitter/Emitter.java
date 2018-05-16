package emitter;

import java.io.*;

import ast.ProcedureDeclaration;

/**
 * Creates an emitter 
 *
 * @author Neil Patel
 * @version May 3, 2018
 */
public class Emitter
{
    private PrintWriter out;
    private ProcedureDeclaration current;
    
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
          emit("sw" + reg +", ($sp)");
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
      
      //remember proc as the current procedure context
      public void setProcedureContext(ProcedureDeclaration proc)
      {
          excessStackHeight = 0;
          current = proc;
      }
      
      //clear current procedure context
      public void clearProcedureContext()
      {
          current = null;
      }
      
      public boolean isLocalVariable(String varName)
      {
          return current.getParamNames().indexOf(varName) != -1;
      }
      
      public int getOffset(String localVarName)
      {
          return 4 * (current.getParamNames().size() - current.getParamNames().indexOf(localVarName)) + excessStackHeight;
      }
}