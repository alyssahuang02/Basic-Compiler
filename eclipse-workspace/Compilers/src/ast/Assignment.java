package ast;
import java.lang.String;

import emitter.Emitter;
import environment.Environment;

/**
 * Assignment is a statement that assigns an expression to a variable.
 * 
 * @author Alyssa Huang
 * @version 05/05/20
 *
 */
public class Assignment extends Statement{

	private String var; //variable name
	private Expression exp; //expression assigned to variable

	/**
	 * Creates instances of the assignment class. Called by the Parser class
	 * when it parses an assignment statement.
	 * 
	 * @param s the variable name
	 * @param e the expression to be assigned to the variable
	 */
	public Assignment(String s, Expression e)
	{
		var = s;
		exp = e;
	}

	/**
	 * Executes the assignment statement by evaluating the expression associated
	 * with the variable and assigning that value to the variable in the environment.
	 * 
	 * @param env the environment in which the variable and its valued will be stored
	 */
	public void exec(Environment env)
	{
		int val = exp.eval(env);
		env.setVariable(var, val);
	}
	
	/**
	 * Compiles the assignment statement by modifying the contents of the
	 * variable with the corresponding name in the .data section of the MIPS file.
	 * 
	 * @param e emitter used to compile the Assignment statement
	 */
	public void compile(Emitter e)
	{
		exp.compile(e);
		e.emit("la $a0, var" + var);
		e.emit("move $a1, $v0");
		e.emit("sw $a1, ($a0)");
	}

}
