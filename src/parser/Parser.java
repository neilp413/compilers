package parser;

import java.io.*;
import java.util.*;
import scanner.ScanErrorException;
import scanner.Scanner;
import ast.Assignment;
import ast.BinOp;
import ast.Block;
import ast.Expression;
import ast.Number;
import ast.Statement;
import ast.Variable;
import ast.Writeln;

/**
 * Parses throw tokens using a specific grammar
 * 
 * 
 * 
 * @author Neil Patel
 * @version March 8, 2018
 */
public class Parser
{
    private Scanner scan;
    private String currentToken;
    private Map<String, Integer> vars;
    // private Map<String, Boolean> vars;

    /**
     * Constructor for the parser class
     * 
     * @param s the scanner to get the current token
     * @throws ScanErrorException when the char isn't recognized 
     */
    public Parser(Scanner s) throws ScanErrorException
    {
        vars = new HashMap<String, Integer>();
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
     * @throws IllegalArgumentException throws if expected doesnt match
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
     * Checks to see if the token is done
     * 
     * @return  true if has next 
     */
    public boolean hasNext()
    {
        return !(currentToken.equals(".") || currentToken.equals("END"));
    }

    /**
     * Parses the current number and returns it
     * 
     * @precondition: currentToken is a integer
     * @postcondition: currentToken is now parsed 
     * 
     * @return the number(int) that is parsed
     * 
     * @throws ScanErrorException if the current and 
     *                            expected do not match
     */
    private Number parseNumber() throws ScanErrorException
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
     * @throws ScanErrorException if the current and 
     *                            expected do not match
     */
    public Statement parseStatement() throws ScanErrorException
    {
        Statement stmt;
        if(currentToken.equals("WRITELN"))
        {
            eat("WRITELN");
            eat("(");
            Expression val = parseExpression();
            eat(")");
//            System.out.println(val);
            stmt = new Writeln(val);
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
            stmt = new Block(stmts);
        }
        else
        {
            String id = currentToken;
            eat(currentToken);
            eat(":=");
            stmt = new Assignment(id, parseExpression());
//            vars.put(id, parseExpression());
        }
        eat(";");
        return stmt;
    }

    /**
     * Parses a factor and returns it
     * 
     * @precondition: currentToken is a factor
     * @postcondition: the factor is parsed
     * 
     * @return the factor after it has been parsed
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
//            return -1 * parseFactor();
        }
        else if(currentToken.chars().allMatch(Character::isDigit))
            return parseNumber();
        else
        {
            Expression exp = new Variable(currentToken);
//            int exp = vars.get(currentToken);
            eat(currentToken);
            return exp;
        }
    }

    /**
     * Parses and returns a number or expression representing the term
     * 
     * @precondition: currentToken is a term
     * @postcondition: term is parsed
     * 
     * @return an integer representing the term parsed
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
//               exp *= parseFactor();
            }
            else if(currentToken.equals("/"))
            {
                eat("/");
                exp = new BinOp("/", exp, parseFactor());
//                exp /= parseFactor();
            }
            else if(currentToken.equals("mod"))
            {
                eat("mod");
                exp = new BinOp("%", exp, parseFactor());
//                exp %= parseFactor();
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
     * @return  the integer representing the expression 
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
//                exp += parseTerm();
            }
            else if(currentToken.equals("-"))
            {
                eat("-");
                exp = new BinOp("-", exp, parseTerm());
//                exp -= parseTerm();
            }          
        }
        return exp;
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
        FileInputStream inStream;
        inStream = new FileInputStream(new File("test/parser/parserTest3.txt"));
        Scanner scanner = new Scanner(inStream);
        Parser parser = new Parser(scanner);
        while(parser.hasNext())
        {
            parser.parseStatement();
        }
    }
}