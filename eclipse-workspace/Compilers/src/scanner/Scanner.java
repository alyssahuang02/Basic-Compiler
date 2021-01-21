package scanner;
import java.io.*;

/**
 * Scanner is a simple scanner for Compilers and Interpreters. It can tokenize 
 * digits, identifiers, and operands.
 * 
 * @author Alyssa Huang
 * February 6th, 2020
 *
 */
public class Scanner
{
    private BufferedReader in;
    private char currentChar;	//current character in input stream
    private boolean eof;		//true if end of file
    /**
     * Scanner constructor for construction of a scanner that 
     * uses an InputStream object for input.  
     * Usage: 
     * FileInputStream inStream = new FileInputStream(new File(<file name>);
     * Scanner lex = new Scanner(inStream);
     * @param inStream the input stream to use
     */
    public Scanner(InputStream inStream)
    {
        in = new BufferedReader(new InputStreamReader(inStream));
        eof = false;
        getNextChar();
    }
    /**
     * Scanner constructor for constructing a scanner that 
     * scans a given input string.  It sets the end-of-file flag an then reads
     * the first character of the input string into the instance field currentChar.
     * Usage: Scanner lex = new Scanner(input_string);
     * 
     * @param inString the string to scan
     */
    public Scanner(String inString)
    {
        in = new BufferedReader(new StringReader(inString));
        eof = false;
        getNextChar();
    }
    
    /**
     * Checks if the given character is a digit.
     * 
     * @param input the character to be checked if it is a digit
     * @return true if the input character is a digit; otherwise,
     * 		   false
     */
    public static boolean isDigit(char input)
    {
    	return (input >= '0' && input <= '9');
    }
    
    /**
     * Checks if the given character is a letter, either upper or lower case.
     * 
     * @param input the character to be checked if it is a letter
     * @return true if the input character is a letter; otherwise,
     * 		   false
     */
    public static boolean isLetter(char input)
    {
    	if(input >= 'a' && input <= 'z') return true;
    	if(input >= 'A' && input <= 'Z') return true;
    	return false;
    }
    
    /**
     * Checks if the given character is white space, meaning spaces, new lines, tabs, or returns.
     * 
     * @param input the character to be checked if it is whitespace
     * @return true if the input character is white space; otherwise,
     * 		   false
     */
    public static boolean isWhiteSpace(char input)
    {
    	return (input == ' ' || input == '\t' || input == '\r' || input == '\n');
    }
    
    /**
     * Checks to see if the expected character parameter matches the currentChar instance variable
     * and moves the input stream forward by one character.
     * 
     * @param expected the character expected to match currentChar
     * @throws ScanErrorException if the expected character does not match currentChar
     */
    private void eat(char expected) throws ScanErrorException
    {
        if(expected == currentChar)
        {
        	getNextChar();
        }
        else
        {
        	throw new ScanErrorException("Illegal character - expected " + currentChar + " and found "+expected);
        }
    }
    
    /**
     * Scans and returns a number starting at currentChar. A number is defined as
     * digit(digit)*
     * 
     * @precondition This method will only be called if the currentCharacter is a digit.
     * @return String the number that occurs with the first character being the
     * currentChar when the method is called
     * @throws ScanErrorException if the expected character does not match currentChar
     */
    private String scanNumber() throws ScanErrorException
    {
    	String result = "";
    	while(isDigit(currentChar))
    	{
    		result += currentChar;
    		eat(currentChar);
    	}
    	return result;
    }
    
    /**
     * Scans and returns an identifier at currentChar. An identifier is defined as
     * letter(letter|digit)*
     * 
     * @precondition This method will only be called if the currentChar is a letter.
     * @return String the identifier that occurs with the first character being the
     * currentChar when the method is called
     * @throws ScanErrorException if the expected character does not match currentChar
     */
    private String scanIdentifier() throws ScanErrorException
    {
    	String result = "";
    	while(isLetter(currentChar) || isDigit(currentChar))
    	{
    		result += currentChar;
    		eat(currentChar);
    	}
    	return result;
    }
    
    /**
     * Scans and returns an operand at currentChar. An operand is defined as
     * ['=','+','-','*','/','%','(',')',';',',']
     * 
     * @precondition This method will be called if the currentChar is an operand.
     * @return String the operand that occurs at currentChar
     * @throws ScanErrorException if the expected character does not match currentChar
     */
    private String scanOperand() throws ScanErrorException
    {
    	String result = "" + currentChar;
    	eat(currentChar);
    	return result;
    }
    
    /**
     * Scans and returns a comparative at currentChar. A comparative is defined as
     * ['<','>',':','<=','>=',':=','<>']
     * 
     * @precondition This method will be called if the currentChar is a comparative.
     * @return String either a single or double character operand
     * @throws ScanErrorException if the expected character does not match currentChar
     */
    private String scanComparative() throws ScanErrorException
    {
    	String result = "" + currentChar;
    	eat(currentChar);
    	if(currentChar == '=')
    	{
    		result += currentChar;
    		eat(currentChar);
    	}
    	else if(result.equals("<") && currentChar == '>')
    	{
    		result += currentChar;
    		eat(currentChar);
    	}
    	return result;
    }
    
    /**
     * Checks to see if the input stream has reached the end of file.
     * 
     * @return true if there exists a next character; otherwise,
     * 		   false
     */
    public boolean hasNext()
    {
       return !eof;
    }
    /**
     * Finds the next token in the input stream and tokenizes it.
     * 
     * @return the next token in the input stream
     * @throws ScanErrorException if the expected character does not match currentChar
     * 							  or the input cannot be tokenized
     */
    public String nextToken() throws ScanErrorException
    {
    	try
    	{
    		while(isWhiteSpace(currentChar))
    		{
    			eat(currentChar);
    		}
    		if(currentChar == '/')
    		{
    			eat(currentChar);
    			if(currentChar == '/')
    			{
    				while(currentChar != '\n')
    				{
    					eat(currentChar);
    				}
    			}
    			else
    			{
    				return "/";
    			}
    		}
    		while(isWhiteSpace(currentChar))
    		{
    			eat(currentChar);
    		}
    		if(isDigit(currentChar)) return scanNumber();
    		if(isLetter(currentChar)) return scanIdentifier();
    		if(currentChar == '=' || currentChar == '+' || currentChar == '-' || 
    				currentChar == '*' || currentChar == '/' || currentChar == '%' ||
    				currentChar == '(' || currentChar == ')' || currentChar == ';' ||
    				currentChar == ',')
    		{
    			return scanOperand();
    		}
    		if(currentChar == '<' || currentChar == '>' || currentChar == ':')
    		{
    			return scanComparative();
    		}
    		if(eof) return "END";
    		throw new ScanErrorException("unrecognized character "+ currentChar);

    	}
    	catch(ScanErrorException e)
        {
        	System.out.println(e.getMessage());
        	eat(currentChar);
        	return null;
        }
    }
    
    /**
     * The getNextChar method attempts to get the next character from the input
     * stream.  It sets the endOfFile flag true if the end of file is reached on
     * the input stream.  Otherwise, it reads the next character from the stream
     * and converts it to a Java String object.
     * postcondition: The input stream is advanced one character if it is not at
     * end of file and the currentChar instance field is set to the String 
     * representation of the character read from the input stream.  The flag
     * endOfFile is set true if the input stream is exhausted.
     */
    private void getNextChar()
    {
        try
        {
            int inp = in.read();
            if(inp == -1) 
                eof = true;
            else 
                currentChar = (char) inp;
            	if(currentChar == '.') eof = true;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
