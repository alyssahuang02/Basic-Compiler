package ast;

import java.util.List;

import emitter.Emitter;
import environment.Environment;

/**
 * A Program class contains a list of zero or more variable names,
 * a list of zero or more procedure declarations, and a single statement in that order.
 * A Program can execute its contents or compile them (translate from PASCAL to MIPS).
 * 
 * @author Alyssa Huang
 * @version 05/05/20
 */
public class Program{
	
	private List<String> variables; //list of variable names
	private List<ProcedureDeclaration> procedures; //list of procedure declarations
	private Statement stmt; //statement following the procedure declarations

	/**
	 * Creates new instances of the Program class.
	 * 
	 * @param vars list of variable names
	 * @param procedures list of procedure declarations
	 * @param stmt statement following the procedure declaration
	 */
	public Program(List<String> vars, List<ProcedureDeclaration> procedures, Statement stmt)
	{
		this.variables = vars;
		this.procedures = procedures;
		this.stmt = stmt;
	}
	
	/**
	 * Executes the program.
	 * 
	 * @param env the environment to be used while executing the program
	 */
	public void exec(Environment env)
	{
		for(int i=0; i<variables.size(); i++)
		{
			env.declareVariable(variables.get(i));
		}
		for(int i=0; i<procedures.size(); i++)
		{
			procedures.get(i).exec(env);
		}
		stmt.exec(env);
	}
	
	/**
	 * Compiles the program by translating the PASCAL code to MIPS
	 * assembly code using an emitter.
	 * 
	 * @param fileName the name of the generated file containing the MIPS assembly code
	 */
	public void compile(String fileName)
	{
		Emitter e = new Emitter(fileName);
		e.emit(".data");
		e.emit("newLine: .asciiz " + "\"" + "\\" + "n" + "\"");
		for(int i=0; i<variables.size(); i++)
		{
			e.emit("var" + variables.get(i) + ": .word 0");
		}
		
		e.emit(".text");
		e.emit(".globl main");
		e.emit("main: ");
		stmt.compile(e);
		
		e.emit("li $v0, 10");
		e.emit("syscall #normal termination");
	}
}
