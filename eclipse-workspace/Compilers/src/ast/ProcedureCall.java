package ast;

import java.util.List;

import environment.Environment;

/**
 * ProcedureCall is an expression representing a call to a procedure located
 * in the body of the program after the procedure declarations.
 * Each ProcedureCall takes the same number of arguments as parameters 
 * there are in its corresponding ProcedureDeclaration (the ProcedureDeclaration
 * with the same id).
 * 
 * @author Alyssa Huang
 * @version 04/11/20
 */
public class ProcedureCall extends Expression{

	private String id; //id or name of the procedure call
	private List<Expression> args; //list containing the arguments of the procedure call
	
	/**
	 * Creates new instances of ProcedureCall object.
	 * 
	 * @param id name of the procedure call
	 * @param args list containing the arguments of the procedure call
	 */
	public ProcedureCall(String id, List<Expression> args) 
	{
		this.id = id;
		this.args = args;
	}
	
	/**
	 * Evaluates the procedure call using the following steps:
	 * 1) Creates a new environment hanging off of the global environment
	 * 2) Declares the parameters in this new environment
	 * 3) Evaluates the arguments and assign values to corresponding parameters
	 * in new environment
	 * 4) Executes the procedure's body statement in the new environment
	 * 5) Return the value of the variable with the same name as the procedure's id
	 * 	  Returns 0 if the procedure returns nothing
	 * 
	 * @param the global environment
	 */
	public int eval(Environment env)
	{
		Environment childEnv = new Environment(env);
		childEnv.declareVariable(id);
		ProcedureDeclaration procedure = env.getProcedure(id);
		List<String> params = procedure.getParams();
		for(int i=0; i<args.size(); i++)
		{
			int val = args.get(i).eval(env);
			String param = params.get(i);
			childEnv.declareVariable(param);
			childEnv.setVariable(param, val);
		}
		procedure.getStatement().exec(childEnv);
		return childEnv.getVariable(id);
	}

}
