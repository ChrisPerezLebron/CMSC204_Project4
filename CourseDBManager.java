import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * this class serves as a wrapper class for the CourseDBStructure class 
 * providing it with a readFile method that allows the client to 
 * read course data from a file
 * @author Christopher Perez Lebron
 *
 */
public class CourseDBManager implements CourseDBManagerInterface{
	
	private CourseDBStructure structure; 
	
	/**
	 * default constructor. Initializes the size of the internal CourseDBStructure object
	 *  to 121. 
	 */
	public CourseDBManager() {
		structure = new CourseDBStructure(121); 
	}
	
	/**
	 * adds a course element into the CourseDBStructure object. This is done by first creating a
	 * CourseDBElement object with the specified information and then adds it to the internal 
	 * CourseDBStructure object
	 * @param id a string representing the course id
	 * @param crn a integer representing the crn
	 * @param credits the number of credits the course is worth 
	 * @param roomNum a string representing the room number
	 * @param instructor a string representing the instructor name
	 * 
	 */
	public void add(String id, int crn, int credits, String roomNum, String instructor) {
		CourseDBElement newCourse = new CourseDBElement(id, crn, credits, roomNum, instructor);
		structure.add(newCourse);
	}
	
	/**
	 * retrieves the CourseDBElement in the database with the given CRN 
	 * @return result CourseDBElement in the database with the given CRN 
	 * OR null if no COurseDBElement object within the database has a 
	 * matching CRN
	 */
	public CourseDBElement get(int crn) {
		/*
		 * the CourseDBManagerInterface specifies no throws clause so I will handle 
		 * any IOException and then do nothing
		 */
		CourseDBElement result = null;
		try {
			result =  structure.get(crn);
		} catch(IOException e) {
			System.out.println(e.getMessage()); 
		}
		
		return result;
	}
	
	/**
	 * reads course data from a file and inserts it into the database
	 * @param input a File object linked to the file containing the 
	 * data to be read
	 * @throws FileNotFoundException if the file does not exist 
	 */
	public void readFile(File input) throws FileNotFoundException {
		/*
		 * ASSUMPTION: the text file is formated EXACTLY like the courses.txt 
		 * file provided in the project folder. That is, 
		 * courseID{space}CRN{space}credits{space}roomNumber{space}instructorName
		 * 
		 * if any one of the lines do not meet this structure those line numbers
		 * will be printed in a errorLog.txt file
		 */
		
		PrintWriter pWriter = new PrintWriter("errorLog.txt");
		
		Scanner fileScanner = new Scanner(input);
		int currentLine = 1;
		while (fileScanner.hasNext()) {
			Scanner lineScanner = new Scanner(fileScanner.nextLine()); 
			try {
				String id = lineScanner.next(); 
				int crn = lineScanner.nextInt(); 
				int credits = lineScanner.nextInt();
				String roomNum = lineScanner.next(); 
				String prof = lineScanner.nextLine().trim();
				CourseDBElement course = new CourseDBElement(id, crn, credits, roomNum, prof);
				structure.add(course);
			} catch(NoSuchElementException e) {
				pWriter.write("ERROR on line " + currentLine + "\n");
			}
			currentLine++; 
		}
		fileScanner.close();
		pWriter.close();
			
	}
	/**
	 * retrieve all database elements as an ArrayList of strings
	 * @return an ArrayList of strings containing the string representation 
	 * of each course object
	 */
	public ArrayList<String> showAll() {
		return structure.showAll();
	}
	
}
