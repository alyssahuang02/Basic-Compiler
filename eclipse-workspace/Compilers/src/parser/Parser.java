package parser;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import ast.Expression;
import ast.IfThen;
import ast.Statement;
import ast.Variable;
import ast.WhileDo;
import ast.Writeln;
import environment.Environment;
import ast.Number;
import ast.ProcedureCall;
import ast.ProcedureDeclaration;
import ast.Program;
import ast.Assignment;
import ast.BinOp;
import ast.Block;
import ast.Condition;
import scanner.ScanErrorException;
import scanner.Scanner;

/**
 * Parser is a simple parser for Compilers and Interpreters. It can parse
 * numbers, factors, terms, and expressions, statements, and programs.
 * It parses and executes input separately.
 * 
 * @author Alyssa Huang
 * @version 05/07/20
 */
public class Parser {
	
	Scanner scanner; //tokenizes the input stream
	String current; //current token read by the scanner
	Environment env; //environment storing variables & values

	/**
	 * Constructs a parser by initializing instance variables.
	 * 
	 * @param sc scanner used by the parser to advance to the next token
	 * @throws ScanErrorException if scanner cannot tokenize the input or
	 * if current character does not match expected character
	 */
	public Parser(Scanner sc) throws ScanErrorException {
		scanner = sc;
		current = scanner.nextToken();
		env = new Environment(null);
	}
	
	/**
	 * Checks to see if the current token matches the expected token and
	 * eats the current token.
	 * 
	 * @param expected the expected token to be read by the scanner
	 * @throws ScanErrorException if scanner cannot tokenize the input or
	 * if the current token does not match the expected token
	 */
	private void eat(String expected) throws ScanErrorException
	{
		if(expected.equals(current))
		{
			current = scanner.nextToken();
		}
		else
		{
			throw new ScanErrorException("Illegal character - expected " + current + " and found "+ expected);
		}
	}
	
	/**
	 * Parses numbers.
	 * 
	 * @precondition the scanner positioned in front of a number
	 * @postcondition the scanner has finished reading the number and is
	 * positioned after the number
	 * @return a Number object containing the given integer
	 * @throws ScanErrorException if scanner cannot tokenize the input or
	 * if the current token does not match the expected token
	 */
	private Number parseNumber() throws ScanErrorException
	{
		int num = Integer.parseInt(current);
		eat(current);
		return new Number(num);
	}
	
	/**
	 * Parses factors. Factors are defined as:
	 * 1) numbers
	 * 2) factors preceded by a negative sign; -factor
	 * 3) procedure id followed by parentheses with one or more
	 * arguments in between the parentheses; id(args)
	 * 4) expression surrounded by parentheses; (expr)
	 * 5) id representing a variable; id
	 * 
	 * @precondition the scanner is positioned in front of a factor
	 * @postcondition the scanner has finished reading the factor and is
	 * positioned after the factor
	 * @return an Expression object representing what has just been parsed
	 * @throws ScanErrorException if scanner cannot tokenize the input or
	 * if the current token does not match the expected token
	 */
	private Expression parseFactor() throws ScanErrorException
	{
		if(current.equals("-"))
		{
			eat(current);
			return new BinOp("*",(Expression)new Number(-1),parseFactor());
		}
		else if(current.equals("("))
		{
			eat(current);
			Expression exp = parseExpression();
			eat(")");
			return exp;
			
		}
		else if(env.containsVariable(current))
		{
			String temp = current;
			eat(current);
			Expression exp = new Number(env.getVariable(temp));
			return exp;
		}
		else
		{
			String temp = current;
			char c = temp.charAt(0);
			if(Character.isDigit(c))
			{
				return parseNumber();
			}			
			eat(current);
			if(current.equals("("))
			{
				eat("(");
				List<Expression> args = new LinkedList<Expression>();
				while(!current.equals(")"))
				{
					args.add(parseExpression());
					if(current.equals(",")) eat(",");
				}
				eat(")");
				return new ProcedureCall(temp, args);
			}
			return new Variable(temp);
		}

	}
	
	/**
	 * Parses terms. Terms are defined expressions that can be added
	 * or subtracted. Parentheses take precedence over multiplication and division.
	 * 
	 * @precondition the scanner is positioned in front of a term
	 * @postcondition the scanner is positioned directly after the term it just
	 * evaluated
	 * @return an Expression object representing what has just been parsed
	 * @throws ScanErrorException if scanner cannot tokenize the input or
	 * if the current token does not match the expected token
	 */
	private Expression parseTerm() throws ScanErrorException
	{
		Expression result = parseFactor();
		while(current.equals("*")||current.equals("/"))
		{
			if(current.equals("*"))
			{
				eat("*");
				result = new BinOp("*",result, parseFactor());
			}
			else if(current.equals("/"))
			{
				eat("/");
				result = new BinOp("/",result, parseFactor());
			}
		}
		return result;
	}
	
	/**
	 * Parses expressions. Expressions include the operations addition,
	 * subtraction, multiplication, and division. Parentheses are prioritized
	 * first. Then, multiplication and division are evaluated. Finally, addition
	 * and subtraction are evaluated.
	 * 
	 * @precondition the scanner is positioned in front of an expression
	 * @postcondition the scanner is positioned directly after the expression it
	 * just evaluated
	 * @return an Expression object representing what has just been parsed
	 * @throws ScanErrorException if scanner cannot tokenize the input or
	 * if the current token does not match the expected token
	 */
	private Expression parseExpression() throws ScanErrorException
	{
		Expression result = parseTerm();
		while(current.equals("+")||current.equals("-"))
		{
			if(current.equals("+"))
			{
				eat("+");
				result = new BinOp("+",parseExpression(),result);
			}
			else
			{
				eat("-");
				result = new BinOp("-",parseExpression(),result);
			}
		}
		while(current.equals("*")||current.equals("/"))
		{
			if(current.equals("*"))
			{
				eat("*");
				result = new BinOp("*",result, parseFactor());
			}
			else
			{
				eat("/");
				result = new BinOp("/",result, parseFactor());
			}
		}
		return result;
	}
	
	/**
	 * Parses statements. A statement can be one of three things:
	 * 1) A sequence of WRITELN() statements with an expression between
	 * the parentheses
	 * 2) A number of statements enclosed by BEGIN and END
	 * 3) A sequence of lines assigning values to variables
	 * 4) An If Then statement
	 * 5) A While statement
	 * 
	 * @precondition the scanner is positioned in front of a statement
	 * @postcondition the scanner is positioned directly after the statement it
	 * just evaluated
	 * @return Statement object representing the statement that has just been parsed
	 * @throws ScanErrorException if scanner cannot tokenize the input or
	 * if the current token does not match the expected token
	 */
	public Statement parseStatement() throws ScanErrorException
	{
		if(current.equals("WRITELN"))
		{
			eat("WRITELN");
			eat("(");
			Expression exp = parseExpression();
			eat(")");
			eat(";");
			return new Writeln(exp);
		}

		else if(current.equals("BEGIN"))
		{
			eat("BEGIN");
			List<Statement> stmts = new LinkedList<Statement>();
			while(!current.equals("END"))
			{
				stmts.add(parseStatement());
			}
			eat("END");
			eat(";");
			return new Block(stmts);
		}
		else if(current.equals("IF"))
		{
			eat("IF");
			Expression exp1 = parseExpression();
			String temp = current;
			eat(current);
			Expression exp2 = parseExpression();
			Condition cond = new Condition(exp1,exp2,temp);
			eat("THEN");
			return new IfThen(cond, parseStatement());
		}
		else if(current.equals("WHILE"))
		{
			eat("WHILE");
			Expression exp1 = parseExpression();
			String temp = current;
			eat(current);
			Expression exp2 = parseExpression();
			Condition cond = new Condition(exp1,exp2,temp);
			eat("DO");
			return new WhileDo(cond, parseStatement());
		}
		else
		{
			String name = current;
			eat(current);
			if(current.equals(":=")) eat(":=");
			else System.out.println("Invalid Statement");
			Expression exp = parseExpression();
			eat(";");
			return new Assignment(name,exp);
		}
	}
	
	/**
	 * Parses programs. Programs contain zero or more variable declarations,
	 * zero or more procedure declarations, and a single statement in that order.
	 * 
	 * @precondition the scanner is positioned in front of a Program
	 * @postCondition the scanner is positioned directly after the Program it
	 * just evaluated
	 * @return Program object representing the statement that has just
	 * been parsed
	 * @throws ScanErrorException if scanner cannot tokenize the input or
	 * if the current token does not match the expected token
	 */
	public Program parseProgram() throws ScanErrorException
	{
		List<String> vars = new LinkedList<String>();
		while(current.equals("VAR"))
		{
			eat("VAR");
			vars.add(current);
			eat(current);
			while(!current.equals(";"))
			{
				eat(",");
				vars.add(current);
				eat(current);
			}
			eat(";");
		}
		List<ProcedureDeclaration> procedures = new LinkedList<ProcedureDeclaration>();
		while(current.equals("PROCEDURE"))
		{
			eat("PROCEDURE");
			String id = current;
			eat(current);
			eat("(");
			List<String> params = new LinkedList<String>();
			while(!current.equals(")"))
			{
				params.add(current);
				eat(current);
				if(current.equals(",")) eat(",");
			}
			eat(")");
			eat(";");
			Statement stmt = parseStatement();
			ProcedureDeclaration temp = new ProcedureDeclaration(id, stmt, params);
			procedures.add(temp);
		}
		Statement statement = parseStatement();
		return new Program(vars, procedures, statement);
	}

}
