package ast;

import emitter.Emitter;
import environment.Environment;

/**
 * The Number class is an expression that represents an integer.
 * 
 * @author Alyssa Huang
 * @version 05/05/20
 *
 */
public class Number extends Expression{
	
	private int value; //value of the number as an integer

	/**
	 * Creates instances of the Number class.
	 * 
	 * @param v value of the number in integer form
	 */
	public Number(int v) {
		value = v;
	}
	
	/**
	 * Evaluates the number.
	 * 
	 * @param env the environment used to evaluate the number
	 * @return integer value of the number
	 */
	public int eval(Environment env)
	{
		return value;
	}
	
	/**
	 * Compiles the number by loading it into $v0 register.
	 * 
	 * @param e emitter used to compile the number
	 * @postcondition the number's value is stored in $v0
	 */
	public void compile(Emitter e)
	{
		e.emit("li $v0, " + value);
	}

}
