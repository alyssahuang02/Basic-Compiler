package environment;

import java.util.HashMap;
import java.util.Map;

import ast.ProcedureDeclaration;
import ast.Statement;

/**
 * The Environment class keeps track of all the variables and their corresponding
 * integer values.
 * 
 * @author Alyssa Huang
 * @version 04/11/20
 */
public class Environment {

	private Map<String, Integer> vars; //map containing variable names and values
	private Map<String, ProcedureDeclaration> procedures; //map containing procedure ids and procedure declarations
	private Environment parentEnv; //contains the parent environment; global environment is null
	
	/**
	 * Constructor for the Environment class.
	 * 
	 * @param parentEnv the parent environment; the parent environment will have null passed here
	 */
	public Environment(Environment parentEnv)
	{
		vars = new HashMap<>();
		procedures = new HashMap<>();
		this.parentEnv = parentEnv;
	}
	
	/**
	 * Sets the variable with the given name to the given value.
	 * 
	 * @param variable the variable name as a string
	 * @param value the integer value associated with the variable
	 */
	public void setVariable(String variable, int value)
	{
		if(this.containsVariable(variable))
		{
			vars.put(variable, value);
		}
		else
		{
			if(parentEnv != null && parentEnv.containsVariable(variable))
			{
				parentEnv.setVariable(variable, value);
			}
			else
			{
				vars.put(variable, value);
			}
		}
	}
	
	/**
	 * Declares a variable with a given name and sets its value to zero.
	 * 
	 * @param variable the name of the variable
	 */
	public void declareVariable(String variable)
	{
		vars.put(variable, 0);
	}
	
	/**
	 * Retrieves the integer value of the given variable. If this current
	 * environment contains the variable, then returns the value of that variable.
	 * Otherwise, returns the value of the variable in the global environment.
	 * 
	 * @param variable the variable whose value must be retrieved
	 * @return integer value of the variable
	 */
	public int getVariable(String variable)
	{
		if(this.containsVariable(variable))
		{
			return vars.get(variable);
		}
		else
		{
			return parentEnv.getVariable(variable);
		}
	}
	
	/**
	 * Sets the procedure with the given id and given procedure declaration
	 * in the global environment.
	 * 
	 * @param id the id of the procedure
	 * @param s the procedure declaration associated with the given id
	 */
	public void setProcedure(String id, ProcedureDeclaration s)
	{
		if(parentEnv == null)
		{
			procedures.put(id, s);
		}
		else
		{
			parentEnv.setProcedure(id, s);
		}
	}
	
	/**
	 * Retrieves the procedure declaration with the given id from the
	 * global environment.
	 * 
	 * @param id the id of the procedure
	 * @return procedure declaration associated with the given id
	 */
	public ProcedureDeclaration getProcedure(String id)
	{
		if(parentEnv == null) return procedures.get(id);
		return parentEnv.getProcedure(id);
	}
	
	/**
	 * Checks if the current environment contains a variable with the given name.
	 * 
	 * @param cur the variable name to be checked if present in the current environment
	 * @return true if environment contains the variable; otherwise,
	 * 		   false
	 */
	public boolean containsVariable(String cur)
	{
		return vars.containsKey(cur);
	}
	
	/**
	 * Checks if the current environment contains a procedure with the given name.
	 * 
	 * @param cur the procedure name to be checked if present in the current environment
	 * @return true if the environment contains the procedure; otherwise,
	 * 		   false
	 */
	public boolean containsProcedure(String cur)
	{
		return procedures.containsKey(cur);
	}
}
