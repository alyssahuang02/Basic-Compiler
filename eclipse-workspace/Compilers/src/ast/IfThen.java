package ast;

import emitter.Emitter;
import environment.Environment;

/**
 * The IfThen class is a statement representing "IF" condition "THEN" statement where
 * the statement is only executed if the condition evaluates to true.
 * 
 * @author Alyssa Huang
 * @version 05/05/20
 *
 */
public class IfThen extends Statement{
	
	private Condition cond; //condition following the "IF"
	private Statement stm; //statement to be executed if the condition evaluates to true

	/**
	 * Creates instances of the IfThen class.
	 * 
	 * @param cond condition following the "IF"
	 * @param stm statement to be executed if the condition evaluates to true
	 */
	public IfThen(Condition cond, Statement stm)
	{
		this.cond = cond;
		this.stm = stm;
	}
	
	/**
	 * Executes the IfThen statement by first evaluating the condition then
	 * executing the statement if the condition evaluates to true.
	 * 
	 * @param env the environment used to execute the statement
	 */
	@Override
	public void exec(Environment env)
	{
		if(cond.eval(env))
		{
			stm.exec(env);
		}
	}
	
	/**
	 * Compiles the IfThen statement by creating an end label, 
	 * compiling the condition and statement, and emitting the end
	 * label after the statement has been compiled.
	 * 
	 * @param e emitter used when compiling
	 */
	public void compile(Emitter e)
	{
		String label = "endif" + e.nextLabelID();
		cond.compile(e, label);
		stm.compile(e);
		e.emit(label + ": \n");
	}

}
