package scanner;
import java.io.*;

/**
 * Scanner is a simple scanner for Compilers and Interpreters (2014-2015) lab
 * exercise 1.
 * 
 * @author Neil Patel
 * @version 1/31/18
 * 
 *          Usage: The Scanner reads a file and returns each character/word
 *          after scanning it to make sure that it fits the specifications.
 * 
 *          Question: What if the next character had been a new line? The token
 *          would have ended. What if the next character had been an open
 *          parenthesis? The token wouldn't have ended and would look at the
 *          next character.
 */
public class Scanner
{
    private BufferedReader in;
    private char currentChar;
    private boolean eof;

    /**
     * Scanner constructor for construction of a scanner that uses an
     * InputStream object for input. Usage: FileInputStream inStream = new
     * FileInputStream(new File(<file name>); Scanner lex = new
     * Scanner(inStream);
     * 
     * @param inStream
     *            the input stream to use
     */
    public Scanner(InputStream inStream)
    {
        in = new BufferedReader(new InputStreamReader(inStream));
        eof = false;
        getNextChar();
    }

    /**
     * Scanner constructor for constructing a scanner that scans a given input
     * string. It sets the end-of-file flag an then reads the first character of
     * the input string into the instance field currentChar. Usage: Scanner lex
     * = new Scanner(input_string);
     * 
     * @param inString
     *            the string to scan
     */
    public Scanner(String inString)
    {
        in = new BufferedReader(new StringReader(inString));
        eof = false;
        getNextChar();
    }

    /**
     * Checks if the next character exists. If it does exist, the currentChar is
     * set to next one. If it doesn't exist, eof is set to true;
     */
    private void getNextChar()
    {
        try
        {
            int read = in.read();
            if (read == -1 )
            {
                eof = true;
            }
            else
            {
                currentChar = (char) read;
                if (currentChar == '.')
                    eof = true;
            }
        }
        catch (IOException e)
        {
            System.out.println("There is an error");
        }
    }

    /**
     * Checks if the currentChar is the same as the parameter passed
     * 
     * @param: the
     *             expected char that is being checked
     * @throws ScanerrorException
     *             if the chars don't match
     */
    private void eat(char expected) throws ScanErrorException
    {
        if (currentChar == expected)
        {
            getNextChar();
        }
        else
        {
            throw new ScanErrorException("Illegal character - expected " 
                    + currentChar + " and found " + expected);
        }
    }

    /**
     * Checks if there is another character after currentChar
     *
     * @return true if not at the end of the file otherwise, returns false
     */
    public boolean hasNext()
    {
        return !eof;
    }

    /**
     * Checks if the char is a digit
     * 
     * @param c
     *            the char being checked
     * @return true if char is a digit otherwise, false
     */
    public static boolean isDigit(char c)
    {
        return (c >= '0' && c <= '9');
    }

    /**
     * Checks if the char is a letter
     * 
     * @param c
     *            the char being checked
     * @return true if char is a letter otherwise, false
     */
    public static boolean isLetter(char c)
    {
        return ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'));
    }

    /**
     * Checks if the char is a white space
     * 
     * @param c
     *            the char being white space
     * @return true if char is a digit otherwise, false
     */
    public static boolean isWhiteSpace(char c)
    {
        return (c == ' ' || c == '\t' || c == '\r' || c == '\n');
    }

    /**
     * Checks if the char is a operand
     * 
     * @param c
     *            the char being checked
     * @return true if char is a operand otherwise, false
     */
    public static boolean isOperand(char c)
    {
        return (c == '=' || c == '+' || c == '-' || c == '*' || c == '/' || 
                c == '%' || c == '(' || c == ')' || c == ';' || c ==':' || 
                c == '<' || c == '>');
    }

    /**
     * Scan the number until it reaches a whitespace and returns the number
     * 
     * @return the lexeme created after scanning
     * @throws ScanErrorException 
     *             if it isn't a number
     */
    private String scanNumber() throws ScanErrorException
    {
        String num = "";

        while (hasNext() && isDigit(currentChar))
        {
            num += currentChar;
            eat(currentChar);
        }

        return num;
    }

    /**
     * Scans the identifier until it reaches a whitespace and returns the
     * identifier
     * 
     * @return the lexeme created after scanning
     * @throws ScanErrorException
     *             if the chars aren't a letter or digit
     */
    private String scanIdentifier() throws ScanErrorException
    {
        String id = "";

        while (hasNext() && (isDigit(currentChar) || isLetter(currentChar)))
        {
            id += currentChar;
            eat(currentChar);
        }

        return id;
    }

    /**
     * Scans the operand until it reaches a white space
     * 
     * @return the lexeme created after the scan
     * @throws ScanErrorException
     *             if the chars aren't an operand
     */
    private String scanOperand() throws ScanErrorException
    {
        String op = "";
        if(hasNext() && isOperand(currentChar))
//        while (hasNext() && isOperand(currentChar))
        {
            op += currentChar;
            eat(currentChar);
        }

        return op;
    }

    /**
     * It scans the in line comment until it reaches the end of the line
     * 
     * @throws ScanErrorException  when the lexeme is not recognized
     */
    public void scanInLineComment() throws ScanErrorException
    {
        while(currentChar != '\n')
        {
            eat(currentChar);
        }
        eat(currentChar);
    }

    /**
     * It scans the in block comment until it reaches the end of the block
     * 
     * @throws ScanErrorException  when the lexeme is not recognized
     */
    public void scanBlockComment() throws ScanErrorException
    {
        boolean isAtEnd = false;
        while(hasNext() && !isAtEnd)
        {
            if(currentChar == '*')
            {
                eat(currentChar);
                isAtEnd = currentChar == '/' ? true : false;
            }
            eat(currentChar);
        }
    }
    
    public void scanParanthesisComment() throws ScanErrorException
    {
        boolean isAtEnd = false;
        while(hasNext() && !isAtEnd)
        {
            if(currentChar == '*')
            {
                eat(currentChar);
                isAtEnd = currentChar == ')' ? true : false;
            }
            eat(currentChar);
        }
    }

    /**
     * Eats the current char until there isn't anymore white space
     * 
     * @throws ScanErrorException when the lexeme is not recognized
     */
    public void eatWhiteSpace() throws ScanErrorException
    {
        while (hasNext() && isWhiteSpace(currentChar))
        {
            eat(currentChar);
        }
    }
    
    public String scanDoubleOperand() throws ScanErrorException
    {
        if(currentChar == '<')
        {
            eat(currentChar);
            if(hasNext() && currentChar == '=')
            {
                eat('=');
                return "<=";
            }
            return "<";
        }
        else if(currentChar == '>')
        {
            eat(currentChar);
            if(hasNext() && currentChar == '=')
            {
                eat('=');
                return ">=";
            }
            return ">";
        }
        else if(currentChar == ':')
        {
            eat(currentChar);
            if(hasNext() && currentChar == '=')
            {
                eat('=');
                return ":=";
            }
            return ":";
        }
        else
            throw new ScanErrorException("Expected a double opperand");
    }

    /**
     * Scans the input stream and returns all of the lexemes (results of the
     * scanning).
     * 
     * @return all of the finished lexemes
     * @throws ScanErrorException
     *             if lexeme not recognized
     */
    public String nextToken() throws ScanErrorException
    {
        String str = "END";

        if (hasNext())
        {
            eatWhiteSpace();
            while(hasNext() && currentChar == '/')
            {
                eat(currentChar);
                if(hasNext() && currentChar == '/')
                    scanInLineComment();
                else if(hasNext() && currentChar == '*')
                    scanBlockComment();
                else
                    return "/";
                eatWhiteSpace();
            }
            while(hasNext() && currentChar == '(')
            {
                eat(currentChar);
                if(hasNext() && currentChar == '*')
                    scanParanthesisComment();
                else
                    return "(";
                eatWhiteSpace();
            }
            if (hasNext())
            {
                if(currentChar == '<' || currentChar == '>' || currentChar == ':')
                    return scanDoubleOperand();
                else if (isDigit(currentChar))
                    str = scanNumber();
                else if (isLetter(currentChar))
                    str = scanIdentifier();
                else if (isOperand(currentChar))
                    str = scanOperand();
                else
                {
                    throw new ScanErrorException("No Lexemme recognized, got " +
                            currentChar);
                }
            }
        }
        return str;
    }

    /**
     * Runs the Scan Class on a specific file
     * 
     * @param args
     *            the list of arguments
     * @throws Exception
     *             exception if file isn't found
     */
    public static void main(String[] args) throws Exception
    {
        FileInputStream inStream;
        //         inStream = new FileInputStream(new File("ScannerTest.txt"));
        inStream = new FileInputStream(new File("/Users/neilpatel/Documents/Projects/compilers/Scanner Lab/scannerTestAdvanced.txt"));
        Scanner scanner = new Scanner(inStream);
        while (scanner.hasNext())
        {
            System.out.println(scanner.nextToken());
        }
    }
}
