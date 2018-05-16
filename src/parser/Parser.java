package parser;

import java.io.*;
import java.util.*;

import scanner.ScanErrorException;
import scanner.Scanner;

import ast.Assignment;
import ast.BinOp;
import ast.Block;
import ast.Condition;
import ast.Expression;
import ast.For;
import ast.If;
import ast.Number;
import ast.ProcedureCall;
import ast.ProcedureDeclaration;
import ast.Program;
import ast.Readln;
import ast.Statement;
import ast.Variable;
import ast.While;
import ast.Writeln;
import emitter.Emitter;
import environment.Environment;

/**
 * Parses through tokens using the specific grammar created in the lab
 * 
 * 
 * @author Neil Patel
 * @version May 2, 2018
 */
public class Parser
{
    private Scanner scan;
    private String currentToken;

    /**
     * Constructor for the parser class
     * 
     * @param s the scanner to get the current token
     * @throws ScanErrorException when the char isn't recognized 
     */
    public Parser(Scanner s) throws ScanErrorException
    {
        scan = s;
        currentToken = scan.nextToken();
    } 

    /**
     * Eats the current token and advances to the next one
     * 
     * @param expected the expected token
     * 
     * @throws  ScanErrorException  throws an exception if the expected
     *                              token and current token do not match
     * @throws IllegalArgumentException throws if expected doesn't match
     *                           
     */
    private void eat(String expected) throws IllegalArgumentException, ScanErrorException
    {
        if(currentToken.equals(expected))
            currentToken = scan.nextToken();
        else
        {
            throw new ScanErrorException("Illegal Token - expected " 
                    + expected + " and found " + currentToken);
        }
    }

    /**
     * Parses the current number and returns it
     * 
     * @precondition: currentToken is a integer
     * @postcondition: currentToken is now parsed 
     * 
     * @return the number object that is parsed
     * 
     * @throws ScanErrorException if the current and 
     *                            expected do not match
     */
    public Number parseNumber() throws ScanErrorException
    {
        Number num = new Number(Integer.parseInt(currentToken));
        eat(currentToken);
        return num;
    }


    /**
     * Parses the WRITELN statement to prints a number
     * or can be used to parse a begin-end block
     * 
     * @precondition: currentToken is a statement
     * @postcondition: the statement is parsed
     * 
     * @return the statement that needs to executed
     * 
     * @throws ScanErrorException if the current and 
     *                            expected do not match
     */
    public Statement parseStatement() throws ScanErrorException
    {
        if(currentToken.equals("WRITELN"))
        {
            eat("WRITELN");
            eat("(");
            Expression val = parseExpression();
            eat(")");
            eat(";");
            return new Writeln(val);
        }
        else if(currentToken.equals("READLN"))
        {
            eat("READLN");
            eat("(");
            Statement stmt = new Readln(new Variable(currentToken));
            eat(currentToken);
            eat(")");
            eat(";");
            return stmt;
        }
        else if(currentToken.equals("BEGIN"))
        {
            List<Statement> stmts = new LinkedList<Statement>();
            eat("BEGIN");
            while(!currentToken.equals("END"))
            {
                stmts.add(parseStatement());
            }
            eat("END");
            eat(";");
            return new Block(stmts);
        }
        else if(currentToken.equals("IF"))
        {
            eat("IF");
            Condition cond = parseCondition();
            eat("THEN");
            Statement stmt = parseStatement();
            if(currentToken.equals("ELSE"))
            {
                eat("ELSE");
                return new If(cond, stmt, parseStatement());
            }
            return new If(cond,stmt);

        }
        else if(currentToken.equals("WHILE"))
        {
            eat("WHILE");
            Condition cond = parseCondition();
            eat("DO");
            return new While(cond, parseStatement());
        }
        else if(currentToken.equals("FOR"))
        {
            eat("FOR");
            String id = currentToken;
            eat(currentToken);
            eat(":=");
            Assignment assig = new Assignment(id, parseNumber());
            eat("TO");
            Number num = parseNumber();
            eat("DO");
            return new For(assig, num, parseStatement());
        }
        else
        {
            String id = currentToken;
            eat(currentToken);
            eat(":=");
            Statement stmt = new Assignment(id, parseExpression());
            eat(";");
            return stmt;
        }
    }

    /**
     * Parses a factor and returns it
     * 
     * @precondition: currentToken is a factor
     * @postcondition: the factor is parsed
     * 
     * @return the expression after it has been parsed
     * 
     * @throws ScanErrorException if the current and 
     *                            expected do not match
     */
    public Expression parseFactor() throws ScanErrorException
    {
        if(currentToken.equals("("))
        {
            eat("(");
            Expression val = parseExpression();
            eat(")");
            return val;
        }
        else if(currentToken.equals("-"))
        {
            eat("-");
            return new BinOp("*", new Number(-1), parseFactor());
        }
        else if(currentToken.chars().allMatch(Character::isDigit))
            return parseNumber();
        else
        {
            String id = currentToken;
            eat(id);
            if(currentToken.equals("("))
            {
                List<Expression> params = new LinkedList<Expression>();
                eat("(");
                while(!currentToken.equals(")"))
                {
                    if(currentToken.equals(","))
                        eat(",");
                    params.add(parseExpression());
                }
                eat(")");
                return new ProcedureCall(id, params);
            }
            return new Variable(id);
        }
    }

    /**
     * Parses and returns a number or expression representing the term
     * 
     * @precondition: currentToken is a term
     * @postcondition: term is parsed
     * 
     * @return the expression representing the term parsed
     * 
     * @throws ScanErrorException if the current and 
     *                            expected do not match
     */
    public Expression parseTerm() throws ScanErrorException
    {
        Expression exp = parseFactor();
        while(currentToken.equals("*") || currentToken.equals("/"))
        {
            if(currentToken.equals("*"))
            {
                eat("*");
                exp = new BinOp("*", exp, parseFactor());
            }
            else if(currentToken.equals("/"))
            {
                eat("/");
                exp = new BinOp("/", exp, parseFactor());
            }
            else if(currentToken.equals("mod"))
            {
                eat("mod");
                exp = new BinOp("%", exp, parseFactor());
            }
        }
        return exp;
    }

    /**
     * Parses and returns the equivalent number to the expression
     * 
     * @precondition: currentToken is an expression
     * @postcondition: the expression is parsed
     * 
     * @return  the expression based on the language
     * 
     * @throws ScanErrorException if the token is not recognized by the
     *                            eat method this exception will be thrown
     */
    public Expression parseExpression() throws ScanErrorException
    {
        Expression exp = parseTerm();
        while(currentToken.equals("+") || currentToken.equals("-"))
        {
            if(currentToken.equals("+"))
            {
                eat("+");
                exp = new BinOp("+", exp, parseTerm());
            }
            else if(currentToken.equals("-"))
            {
                eat("-");
                exp = new BinOp("-", exp, parseTerm());
            }          
        }
        return exp;
    }

    /**
     * Parses and returns the correct condition object
     * 
     * @precondition: currentToken is a condition
     * @postcondition: the condition is parsed
     * 
     * @return  the condition object created
     * @throws ScanErrorException if the token is not recognized by the
     *                            eat method this exception will be thrown
     */
    public Condition parseCondition() throws ScanErrorException
    {
        Expression exp1 = parseExpression();
        String op = currentToken;
        eat(op);
        Expression exp2 = parseExpression();
        return new Condition(op, exp1, exp2);
    }

    /**
     * Parses through the program files, first looking at all the declared variables
     * and the statement that follows
     * 
     * @return  the program to be executed
     * @throws ScanErrorException   if the token is not recognized by the
     *                            eat method this exception will be thrown
     */
    public Program parseProgram() throws ScanErrorException
    {   
        List<String> vars = new LinkedList<String>();
        List<ProcedureDeclaration> procedures = new LinkedList<ProcedureDeclaration>();

        while(currentToken.equals("VAR"))
        {
            eat("VAR");
            while(!currentToken.equals(";"))
            {
                vars.add(currentToken);
                eat(currentToken);
                if(currentToken.equals(","))
                    eat(",");
            }
            eat(";");
        }
        
        while(currentToken.equals("PROCEDURE"))
        {
            List<Variable> params = new LinkedList<Variable>();
            
            eat("PROCEDURE");
            String name = currentToken;
            eat(name);
            eat("(");
            while(!currentToken.equals(")"))
            {
                if(currentToken.equals(","))
                    eat(",");
                params.add(new Variable(currentToken));
                eat(currentToken);
            }
            eat(")");
            eat(";");
            procedures.add(new ProcedureDeclaration(name, parseStatement(), params));   
        }
        return new Program(procedures, vars, this.parseStatement());
    }

    /**
     * The main method for executing the program for testing
     * 
     * @param   args    The arguments for main
     * 
     * @throws  ScanErrorException   can throw a scan error exception if 
     *                               the lexeme is not recognized
     *                               
     * @throws  FileNotFoundException   throws this if file directory is 
     *                                  incorrect or unable to be found 
     */
    public static void main(String[] args) throws ScanErrorException, FileNotFoundException
    {
        FileInputStream inStream = new FileInputStream(new File(
                "tester/test.txt"));
        Scanner scanner = new Scanner(inStream);
        Parser parser = new Parser(scanner);
        Environment env = new Environment(null);
//        parser.parseProgram().exec(env);
        Emitter e = new Emitter("testfile.asm");
        Program program = parser.parseProgram();
//        Iterator<String> it = program.getVars().iterator();
//        e.emit("#@author Neil Patel");
//        e.emit("#@version May 14, 2018");
//        e.emit(".data");
//        e.emit("newLine: .asciiz \"\\n\"");
//        
//        // Adds the "var" to beginning of each variable name, adds it to the MIPS code, and sets
//        // the value of each variable to 0
//        while(it.hasNext())
//            e.emit("var" + it.next() + ": .word 0");
//        
//        e.emit(".text");
//        e.emit(".globl main");
//        e.emit("main:");
        program.compile(e);
        e.close();
    }
}