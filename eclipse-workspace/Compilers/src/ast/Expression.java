package ast;

import emitter.Emitter;
import environment.Environment;

/**
 * Expression is an abstract class that is the parent of all classes that have the
 * ability to be evaluated to an integer value. For this lab, the BinOp, Number, and
 * Variable classes extend the Expression class.
 * 
 * @author Alyssa Huang
 * @version 05/07/20
 *
 */
public abstract class Expression {
	
	/**
	 * Evaluates the value of the expression.
	 * 
	 * @param env the environment to be used in case the expression contains
	 * variables
	 * @return integer value of the expression
	 */
	public abstract int eval(Environment env);
	
	/**
	 * Compares the value of this expression to another expression.
	 * 
	 * @param exp the expression to which this expression will be compared to
	 * @param env the environment used to evaluate and compare the expressions
	 * @return integer value indicating the following:
	 * 		   < 0 means that this expression is smaller than the other expression
	 * 		   = 0 means that the two expressions are equal
	 * 		   > 0 means that this expression is greater than the other expression
	 */
	public int compareTo(Expression exp, Environment env)
	{
		int val1 = this.eval(env);
		int val2 = exp.eval(env);
		return val1-val2;
	}
	
	/**
	 * Compiles the expression by emitting the corresponding code
	 * in MIPS assembly code.
	 * 
	 * @postcondition the value of the expression is stored in register $v0
	 * @param e the emitter used to compile the expression
	 * @throws RunTime Exception if child class has not overriden this method
	 */
	public void compile(Emitter e)
	{
		throw new RuntimeException("Implement me!");
	}
}
