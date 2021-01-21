package ast;

import emitter.Emitter;
import environment.Environment;

/**
 * Statement is an abstract class that encompasses all lines of input that
 * are executable. For this lab, the Assignment, Block, IfThen, WhileDo, and Writeln
 * classes extend the Statement class.
 * 
 * @author Alyssa Huang
 * @version 05/05/20
 *
 */
public abstract class Statement {

	/**
	 * Executes the statement.
	 * 
	 * @param env the environment that the statement should use in
	 * its execution
	 */
	public abstract void exec(Environment env);
	
	/**
	 * Compiles the statement by emitting the corresponding code
	 * in MIPS assembly code.
	 * 
	 * @param e the emitter used to compile the statement
	 * @throws RunTime Exception if child class has not overriden this method
	 */
	public void compile(Emitter e)
	{
		throw new RuntimeException("Implement me!");
	}

}
