package emitter;
import java.io.*;

/**
 * An emitter generates a text file one line at a time based on the string it is
 * tasked with printing. The emitter serves the generate the text file
 * representing the MIPS assembly code counterpart to the PASCAL code.
 * 
 * @author Alyssa Huang
 * @version 05/07/20
 */
public class Emitter
{
	private PrintWriter out;
	private int count; //keeps track of endif's labelID

	/**
	 * Creates an emitter for writing to a new file with given name.
	 * 
	 * @param outputFileName the name of the generated file
	 */
	public Emitter(String outputFileName)
	{
		count = 0;
		try
		{
			out = new PrintWriter(new FileWriter(outputFileName), true);
		}
		catch(IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	/**
	 * Prints one line of code to file (with non-labels indented).
	 * 
	 * @param code the line of code to be printed
	 */
	public void emit(String code)
	{
		if (!code.endsWith(":"))
			code = "\t" + code;
		out.println(code);
	}

	/**
	 * Closes the file. Should be called after all calls to emit.
	 */
	public void close()
	{
		out.close();
	}
	
	/**
	 * Prints the lines corresponding to the MIPS assembly code to push
	 * the contents of the given register onto the stack.
	 * 
	 * @param reg the register whose contacts should be pushed onto the stack
	 * @postcondition stack pointer ($sp) should decrease by 4
	 */
	public void emitPush(String reg)
	{
		this.emit("subu $sp, $sp, 4");
		this.emit("sw " + reg + ", ($sp)");
	}
	
	/**
	 * Prints the lines corresponding to the MIPS assembly code to pop
	 * the contents of the stack into a given register.
	 * 
	 * @param reg the register that will contain the contents of the element
	 * at the top of the stack
	 * @postcondition stack pointer ($sp) should increase by 4
	 */
	public void emitPop(String reg)
	{
		this.emit("lw " + reg + ", ($sp)");
		this.emit("addu $sp, $sp, 4");
	}
	
	/**
	 * Returns the next label ID of the if or while loop.
	 * 
	 * @return the next label ID
	 */
	public int nextLabelID()
	{
		count++;
		return count;
	}
}