package scanner;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Tester {

	public Tester() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws ScanErrorException, FileNotFoundException {
		FileInputStream inStream = new FileInputStream(new File("./src/scanner/ScannerTest.txt"));
	    Scanner scan = new Scanner(inStream);
		// TODO Auto-generated method stub
		while(scan.hasNext())
		{
			String result = scan.nextToken();
			if(result!=null) System.out.println(result);
		}
	}

}
