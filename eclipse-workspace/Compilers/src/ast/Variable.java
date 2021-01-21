package ast;

import emitter.Emitter;
import environment.Environment;

/**
 * The Variable class is an expression that is a string corresponding to a
 * variable name.
 * 
 * @author Alyssa Huang
 * @version 05/05/20
 *
 */
public class Variable extends Expression{

	private String name; //name of the variable
	
	/**
	 * Creates new instances of the Variable class.
	 * 
	 * @param n string corresponding to the variable name
	 */
	public Variable(String n) {
		name = n;
	}
	
	/**
	 * Evaluates the variable by retrieving the value of the variable
	 * using the environment.
	 * 
	 * @param env environment used to retrieve the value of the variable
	 * @return integer value of the variable
	 */
	public int eval(Environment env)
	{
		return env.getVariable(name);
	}
	
	/**
	 * Compiles the variable by emitting MIPS code that loads the
	 * variable's value into register $v0.
	 * 
	 * @param e emitter used to compile the variable
	 * @postcondition variable's value is stored in $v0
	 */
	public void compile(Emitter e)
	{
		e.emit("la $t0, var" + name);
		e.emit("lw $v0, ($t0)");
	}

}
