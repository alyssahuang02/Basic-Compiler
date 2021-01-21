package ast;

import java.util.List;

import emitter.Emitter;
import environment.Environment;

/**
 * Block is a statement that is a group of statements located between the
 * strings "BEGIN" and "END."
 * 
 * @author Alyssa Huang
 * @version 05/07/20
 *
 */
public class Block extends Statement{

	private List<Statement> stmts; //list of the block's statements in the order which they appear
	/**
	 * Creates new instances of the Block class.
	 * 
	 * @param s list containing the statements contained in the block in the order in which
	 * they appear
	 */
	public Block(List<Statement> s)
	{
		stmts = s;
	}
	
	/**
	 * Executes the statements in the block in order.
	 * 
	 * @param env the environment to be used when executing the block
	 */
	public void exec(Environment env)
	{
		for(int i=0; i<stmts.size(); i++)
		{
			Statement cur = stmts.get(i);
			cur.exec(env);
		}
	}
	
	/**
	 * Compiles the statements in the block in order.
	 * 
	 * @param e emitter used while compiling the block
	 */
	public void compile(Emitter e)
	{
		for(int i=0; i<stmts.size(); i++)
		{
			Statement cur = stmts.get(i);
			cur.compile(e);
		}
	}

}
