package parser;

import java.io.*;
import java.util.*;
import scanner.ScanErrorException;
import scanner.Scanner;

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
     * @return the number(int) that is parsed
     * 
     * @throws ScanErrorException if the current and 
     *                            expected do not match
     */
    private int parseNumber() throws ScanErrorException
    {
        int num = Integer.parseInt(currentToken);
        eat(currentToken);
        return num;
    }


    /**
     * Parses the WRITELN statement to prints a number
     * or can be used to parse a begin-end block
     * 
     * @throws ScanErrorException if the current and 
     *                            expected do not match
     */
    public void parseStatement() throws ScanErrorException
    {
        if(currentToken.equals("WRITELN"))
        {
            eat("WRITELN");
            eat("(");
            int val = parseExpression();
            eat(")");
            System.out.println(val);
        }
        else if(currentToken.equals("BEGIN"))
        {
            eat("BEGIN");
            while(!currentToken.equals("END"))
            {
                parseStatement();
            }
            eat("END");
        }
        else
        {
            String id = currentToken;
            eat(currentToken);
            eat(":=");
            vars.put(id, parseExpression());
        }
        eat(";");
    }

    /**
     * Parses a factor and returns it
     * 
     * @return the factor after it has been parsed
     * 
     * @throws ScanErrorException if the current and 
     *                            expected do not match
     */
    public int parseFactor() throws ScanErrorException
    {
        if(currentToken.equals("("))
        {
            eat("(");
            int val = parseExpression();
            eat(")");
            return val;
        }
        else if(currentToken.equals("-"))
        {
            eat("-");
            return -1 * parseFactor();
        }
        else if(currentToken.chars().allMatch(Character::isDigit))
            return parseNumber();
        else
        {
            int val = vars.get(currentToken);
            eat(currentToken);
            return val;
        }
    }

    /**
     * Parses and returns a number or expression representing the term
     * 
     * @return an integer representing the term parsed
     * 
     * @throws ScanErrorException if the current and 
     *                            expected do not match
     */
    public int parseTerm() throws ScanErrorException
    {
        int val = parseFactor();
        while(currentToken.equals("*") || currentToken.equals("/"))
        {
            if(currentToken.equals("*"))
            {
                eat("*");
                val *= parseFactor();
            }
            else if(currentToken.equals("/"))
            {
                eat("/");
                val /= parseFactor();
            }
            else if(currentToken.equals("mod"))
            {
                eat("mod");
                val %= parseFactor();
            }
        }
        return val;
    }

    /**
     * Parses and returns the equivalent number to the expression
     * 
     * @return  the integer representing the expression 
     * 
     * @throws ScanErrorException if the token is not recognized by the
     *                            eat method this exception will be thrown
     */
    public int parseExpression() throws ScanErrorException
    {
        int val = parseTerm();
        while(currentToken.equals("+") || currentToken.equals("-"))
        {
            if(currentToken.equals("+"))
            {
                eat("+");
                val += parseTerm();
            }
            else if(currentToken.equals("-"))
            {
                eat("-");
                val -= parseTerm();
            }          
        }
        return val;
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