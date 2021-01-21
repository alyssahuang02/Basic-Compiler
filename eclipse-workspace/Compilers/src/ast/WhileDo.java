package ast;

import emitter.Emitter;
import environment.Environment;

/**
 * The WhileDo class is a statement representing a "WHILE" condition "DO" statement.
 * The statement will continue to execute itself as long as the condition holds true.
 * 
 * @author Alyssa Huang
 * @version 05/07/20
 *
 */
public class WhileDo extends Statement{
	
	private Condition cond; //condition determining if statement will be executed
	private Statement stmt; //statement to be executed while the condition holds true

	/**
	 * Creates an instance of the WhileDo class.
	 * 
	 * @param c condition determining whether the statement should be executed
	 * @param stat statement to be executed while the condition holds true
	 */
	public WhileDo(Condition c, Statement stat)
	{
		cond = c;
		stmt = stat;
	}
	
	/**
	 * Executes the WhileDo statement.
	 * 
	 * @param env the environment to be used while executing the WhileDo statement
	 */
	public void exec(Environment env)
	{
		while(cond.eval(env))
		{
			stmt.exec(env);
		}
	}
	
	/**
	 * Compiles the WhileDo statement.
	 * 
	 * @param e the emitter used while compiling the WhileDo statement
	 */
	public void compile(Emitter e)
	{
		int curCount = e.nextLabelID();
		String loopName = "loop" + curCount;
		e.emit(loopName + ": \n");
		String label = "endif" + curCount;
		cond.compile(e, label);
		stmt.compile(e);
		e.emit("j " + loopName);
		e.emit(label + ": \n");
	}

}
