package parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import ast.Program;
import ast.Statement;
import scanner.ScanErrorException;
import scanner.Scanner;

public class ParserTester {

	public ParserTester() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws FileNotFoundException, ScanErrorException {
		FileInputStream inStream = new FileInputStream(new File("./src/parser/parserTest0.txt"));
	    Scanner scan = new Scanner(inStream);
	    Parser pars = new Parser(scan);
	    while(scan.hasNext())
	    {
	    	Program cur = pars.parseProgram();
	    	//cur.exec(pars.env);
	    	cur.compile("compileTest");
	    }
		

	}

}
