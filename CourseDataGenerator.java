import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;
/**
 * This class generate a file with course data for testing 
 * @author Christopher Perez Lebron
 *
 */
public class CourseDataGenerator {
	
	private static Random rand = new Random();
	
	public static void main(String[] args) throws FileNotFoundException {
		ArrayList<String> firstNameList = loadNames("FirstNames.txt"); 
		ArrayList<String> lastNameList = loadNames("LastNames.txt");
		int numCourses = 400; //insert the number of courses you want to generate here
		
		PrintWriter pWriter = new PrintWriter("CoursesData.txt"); 
		
		for(int count = 0; count < numCourses; count++) {
			String str = ""; 
			//add random course id of form aaa999 followed by space
			str += randomChar() + randomChar() + randomChar() + randomDigit() + randomDigit() + randomDigit() + " ";
			//add random crn int between 1000 and 99999
			str += (rand.nextInt(89999) + 1000) + " "; 
			//add random number of credits 1-6
			str+= (rand.nextInt(5) + 1) + " "; 
			//random room number of form aa999
			str += randomChar() + randomChar() + randomDigit() + randomDigit() + randomDigit() + " ";
			//add random name 
			str += firstNameList.get(rand.nextInt(firstNameList.size())) + " " + lastNameList.get(rand.nextInt(lastNameList.size())); 
			
			pWriter.write(str + "\n"); 
		}
		
		pWriter.close();
		
		
	}
	
	/**
	 * @return a random capital letter 
	 */
	private static String randomChar() {
		return "" + Character.toUpperCase((char)(rand.nextInt(26) + 'a')); 
	}
	
	/**
	 * @return a random digit from 0 to 9
	 */
	private static int randomDigit() {
		return rand.nextInt(10);
	}
	
	
	/**
	 * given a string named fileName representing the file name of a text file 
	 * containing names in the following format:
	 * name1 
	 * name2 
	 * ...
	 * nameN
	 * this method will load each line as a different entry in the 
	 * resultant array list for use in generating instructor names 
	 * 
	 * @param fileName the string representation of a file name
	 * @return an ArrayList<String> object containing the names in the file
	 * @throws FileNotFoundException if the provided file name does not exist. 
	 * However, this should not occur because it is a private method we call 
	 */
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
