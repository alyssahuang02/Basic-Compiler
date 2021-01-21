package ast;
import java.lang.String;

import emitter.Emitter;
import environment.Environment;

/**
 * BinOp is an expression that combines two expressions with one of four mathematical
 * operations (addition, subtraction, multiplication, division).
 * 
 * @author Alyssa Huang
 * @version 05/05/20
 *
 */
public class BinOp extends Expression{
	
	private String op; //string containing the mathematical operation
	private Expression exp1; //first expression on left of operator
	private Expression exp2; //second expression on right of operator

	/**
	 * Creates a new instance of the BinOp class.
	 * 
	 * @param string representing one of the four mathematical operations
	 * @param e1 first expression on the left of the operator
	 * @param e2 second expression on the right of the operator
	 */
	public BinOp(String string, Expression e1, Expression e2) {
		op = string;
		exp1 = e1;
		exp2 = e2;
	}
	
	/**
	 * Evaluates the BinOp by evaluating the expression on the left and the right
	 * then performing the right operation given the instance variable op.
	 * 
	 * @param env the environment to be used while evaluating the BinOp
	 * @return integer value of the BinOp
	 */
	@Override
	public int eval(Environment env)
	{
		if(op.equals("+"))
		{
			int num1 = exp1.eval(env);
			int num2 = exp2.eval(env);
			return num1 + num2;
		}
		else if(op.equals("-"))
		{
			int num1 = exp1.eval(env);
			int num2 = exp2.eval(env);
			return num1 - num2;
		}
		else if(op.equals("*"))
		{
			int num1 = exp1.eval(env);
			int num2 = exp2.eval(env);
			return num1 * num2;
		}
		else
		{
			int num1 = exp1.eval(env);
			int num2 = exp2.eval(env);
			return num1 / num2;
		}
	}
	
	/**
	 * Compiles the BinOp by compiling both expressions and
	 * emitting code that performs the necessary operation in MIPS.
	 * 
	 * @param e emitter used to compile the BinOp
	 * @postcondition result of binop is stored in $v0
	 */
	public void compile(Emitter e)
	{
		exp1.compile(e);
		e.emitPush("$v0");
		exp2.compile(e);
		e.emitPop("$t0");
		if(op.equals("+"))
		{
			e.emit("addu $v0, $t0, $v0");
		}
		else if(op.equals("-"))
		{
			e.emit("subu $v0, $t0, $v0");
		}
		else if(op.equals("*"))
		{
			e.emit("mult $v0, $t0");
			e.emit("mflo $v0");
		}
		else
		{
			e.emit("div $v0, $t0");
			e.emit("mflo $v0");
		}
	}
}
