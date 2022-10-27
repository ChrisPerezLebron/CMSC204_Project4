import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;

public class ConsoleTesting {
	
	private static Random rand = new Random();
	
	public static void main(String[] args) throws FileNotFoundException {
		for (int count = 0; count < 1000; count++ ) {
			String str = "" + count; 
			System.out.println(str);
		}
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private static String randomChar() {
		return "" + Character.toUpperCase((char)(rand.nextInt(26) + 'a')); 
	}
	
	private static int randomDigit() {
		return rand.nextInt(10);
	}
	
	
	
	private static ArrayList<String> loadNames(String fileName) throws FileNotFoundException {
		/*
		 * PRECONDITION: names are organized one per line as follows
		 * chris
		 * angel
		 * gabriel 
		 * ....
		 */
		ArrayList<String> resultList = new ArrayList<String>(); 
		
		File file = new File(fileName); 
		Scanner fileScanner = new Scanner(file);
		
		while(fileScanner.hasNext()) {
			resultList.add(fileScanner.next().trim());
		}
		
		return resultList;
	}
}
