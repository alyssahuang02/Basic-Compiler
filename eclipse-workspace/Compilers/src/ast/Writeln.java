package ast;

import emitter.Emitter;
import environment.Environment;

/**
 * Writeln is a statement that begins with "WRITELN" followed by an expression.
 * 
 * @author Alyssa Huang
 * @version 05/05/20
 */
public class Writeln extends Statement{

	private Expression exp; //expression contained in the Writeln statement
	
	/**
	 * Creates new instances of the Writeln class.
	 * 
	 * @param e expression to be contained in the Writeln statement
	 */
	public Writeln(Expression e) {
		exp = e;
	}
	
	/**
	 * Executes the Writeln statement using the given environment.
	 * 
	 * @param env given environment
	 */
	public void exec(Environment env)
	{
		System.out.println(exp.eval(env));
	}
	
	/**
	 * Compiles the Writeln statement using the given emitter.
	 * 
	 * @param e given emitter
	 */
	public void compile(Emitter e)
	{
		exp.compile(e);
		e.emit("move $a0, $v0");
		e.emit("li $v0, 1");
		e.emit("syscall");
		e.emit("la $a0, newLine");
		e.emit("li $v0, 4");
		e.emit("syscall");
	}
}
