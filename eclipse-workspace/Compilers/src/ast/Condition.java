package ast;

import emitter.Emitter;
import environment.Environment;

/**
 * Condition is a class representing two expressions with a comparative operator
 * in between. For this lab, comparative operators include "=, <>, <, >, <=, >=".
 * 
 * @author Alyssa Huang
 * @version 05/05/20
 *
 */
public class Condition{
	
	private Expression exp1; //the expression on the left hand side
	private Expression exp2; //the expression on the right hand side
	private String cond; //a string representing the comparative operator

	
	/**
	 * Creates new instances of the Condition class.
	 * 
	 * @param e1 the expression to the left
	 * @param e2 the expression to the right
	 * @param c string representing the comparative operator
	 */
	public Condition(Expression e1, Expression e2, String c)
	{
		exp1 = e1;
		exp2 = e2;
		cond = c;
	}

	/**
	 * Evaluates the condition.
	 * 
	 * @param env the environment which should be used when evaluating the condition
	 * @return true if the condition is true; otherwise,
	 * 		   false
	 */
	public boolean eval(Environment env)
	{
		if(cond.equals("="))
		{
			return exp1.compareTo(exp2, env) == 0;
		}
		else if(cond.equals("<>"))
		{
			return exp1.compareTo(exp2, env) != 0;
		}
		else if(cond.equals("<"))
		{
			return exp1.compareTo(exp2, env) < 0;
		}
		else if(cond.equals(">"))
		{
			return exp1.compareTo(exp2, env) > 0;
		}
		else if(cond.equals("<="))
		{
			return exp1.compareTo(exp2, env) < 0 || exp1.compareTo(exp2, env) == 0;
		}
		else
		{
			return exp1.compareTo(exp2, env) > 0 || exp1.compareTo(exp2, env) == 0;
		}
	}
	
	/**
	 * Compiles the condition by compiling both expressions and jumping
	 * to the given target label if the condition fails.
	 * 
	 * @param e emitter used to compile the condition
	 * @param targetLabel the label to jump to if the condition fails
	 */
	public void compile(Emitter e, String targetLabel)
	{
		exp1.compile(e);
		e.emit("move $t0, $v0");
		exp2.compile(e);
		if(cond.equals("="))
		{
			e.emit("bne $t0, $v0, " + targetLabel);
		}
		else if(cond.equals("<>"))
		{
			e.emit("beq $t0, $v0, " + targetLabel);
		}
		else if(cond.equals("<"))
		{
			e.emit("bge $t0, $v0, " + targetLabel);
		}
		else if(cond.equals(">"))
		{
			e.emit("ble $t0, $v0, " + targetLabel);
		}
		else if(cond.equals("<="))
		{
			e.emit("bgt $t0, $v0, " + targetLabel);
		}
		else
		{
			e.emit("blt $t0, $v0, " + targetLabel);
		}
	}

}
