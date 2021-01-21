package ast;

import java.util.List;

import environment.Environment;

/**
 * ProcedureDeclaration class is a statement that contains a string representing
 * the id of the declaration and the statement that's the body of the procedure
 * declaration. ProcedureDeclaration also contains a list of zero or more parameters.
 * 
 * @author Alyssa Huang
 * @version 04/11/20
 */
public class ProcedureDeclaration extends Statement{
	
	private String id; //name of the procedure declaration
	private Statement stmt; //statement representing the body of the procedure declaration
	private List<String> params; //list containing the parameters of the procedure declaration

	/**
	 * Creates new instances of the ProcedureDeclaration object.
	 * 
	 * @param id string representing the name of the procedure declaration
	 * @param stmt statement in the body of the procedure declaration
	 * @param params list of parameters
	 */
	public ProcedureDeclaration(String id, Statement stmt, List<String> params)
	{
		this.id = id;
		this.stmt = stmt;
		this.params = params;
	}
	
	/**
	 * Retrieves the parameters of the procedure declaration.
	 * 
	 * @return list of strings containing parameters
	 */
	public List<String> getParams()
	{
		return params;
	}
	
	/**
	 * Retrieves the procedure declaration's body statement.
	 * 
	 * @return statement of the procedure declaration
	 */
	public Statement getStatement()
	{
		return stmt;
	}
	
	/**
	 * Executes the procedure declaration.
	 * 
	 * @param env the environment used to execute the procedure declaration
	 */
	public void exec(Environment env)
	{
		env.setProcedure(id, this);
	}

}
