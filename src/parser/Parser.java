package parser;

import java.io.*;
import java.util.*;
import scanner.ScanErrorException;
import scanner.Scanner;

public class Parser
{
    private Scanner scan;
    private String currentToken;
    private Map<String, Integer> vars;
   // private Map<String, Boolean> vars;
    
    public Parser(Scanner s) throws ScanErrorException
    {
        vars = new HashMap<String, Integer>();
        scan = s;
        currentToken = scan.nextToken();
    } 

    
    //TODO: ADD SUPPORT FOR <= and := to scanner
    
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

    /*
     * TODO: FINISH COMMENT FOR gENERAL METHOD
     * 
     * precondition: current token is an integer
     * postcondition: number token has been eaten
     * @return the value of the parse integer
     */
    private int parseNumber() throws ScanErrorException
    {
        int num = Integer.parseInt(currentToken);
        eat(currentToken);
        return num;
    }

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
    public static void main(String[] args) throws Exception
    {
        FileInputStream inStream;
        inStream = new FileInputStream(new File("test/parser/parserTest4.txt"));
        Scanner scanner = new Scanner(inStream);
        Parser parser = new Parser(scanner);
        parser.parseStatement();
    }
}