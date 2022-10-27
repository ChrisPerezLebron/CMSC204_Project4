import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * This JUnit test aims to validate the operations of the 
 * CourseDBManager class. Comments have been added within 
 * the test cases to clarify the logic used. 
 * @author Christopher Perez Lebron
 * 
 *
 */
class CourseDBManager_STUDENT_Test {

	private CourseDBManager manager; 
	
	@BeforeEach
	void setUp() throws Exception {
		manager = new CourseDBManager();
	}

	@AfterEach
	void tearDown() throws Exception {
		manager = null;
	}

	@Test
	void testCourseDBManager() {
		
		/*
		 * the class does not provide any methods that would 
		 * enable me to test the constructor. All I can do 
		 * is check to ensure the database is empty 
		 */
		
		ArrayList<String> resultList = manager.showAll(); 
		assertEquals(0, resultList.size());
	}

	@Test
	void testAdd() {
		/*
		 * to test add I will add a couple courses and ensure they were actually 
		 * added to the database and there aren't any extra unexpected courses
		 */
		
		manager.add("DVD470", 89114, 5, "OZ933", "Matthew Riggs"); //distinct 
		manager.add("MJC088", 14729, 4, "KP942", "Claire Parker"); //distinct
		manager.add("KFC631", 38783, 5, "LF257", "Keely Deal"); //distinct 
		
		/*
		 * now I will add a entry that has a CRN equal to one of 
		 * the courses I have already added 
		 * 
		 * NOTE: I do not believe it is optimal for the database to 
		 * "update" course info upon receiving a CRN that is already 
		 * in the database. However, the JUnit provided with the project 
		 * enforces that course info should be updated upon duplicate keys 
		 * so I will be adhering to that and enforcing it here as well. 
		 */
		
		manager.add("UKG815", 14729, 1, "KU482", "Willis Kay"); //not distinct (sane crn as second course added
		
		ArrayList<String> resultList = manager.showAll();
		
		/*
		 * check to ensure size is 3 (b/c the last entry "added" is a duplicate search key and should only update 
		 * the values of the previously stored course with the same crn rather than store a new course. (so, 
		 * size should not change when adding duplicate keys) 
		 */
		
		 
		
		assertEquals(3, resultList.size()); 
		
		/*
		 * now check to see if the correct info was stored along side each crn 
		 */
		
		assertEquals("Matthew Riggs", manager.get(89114).getInstructorName()); //checking first added 
		
		assertEquals("KU482", manager.get(14729).getRoomNum()); //check second added who's course info was later overwritten/updated 
		
		assertEquals(5, manager.get(38783).getNumberOfCredits()); //check third added 
		
		
	}

	@Test
	void testGet() {
		/*
		 * already tested it's ability to properly retrieve in the add test but I 
		 * will provide an additional round of testing along with testing what occurs when 
		 * you try to get a non-existing entry 
		 */
		
		manager.add("KRU311", 67578, 3, "VU666", "Harvey Kinney"); //distinct 
		manager.add("PTO068", 82532, 1, "RF495", "Tad High"); //distinct
		manager.add("WTV651", 64443, 4, "TV021", "Francis Livingston"); //distinct 
		
		assertEquals(3, manager.get(67578).getNumberOfCredits()); //check first added 
		assertEquals("PTO068", manager.get(82532).getID()); //check second added 
		assertEquals("Francis Livingston", manager.get(64443).getInstructorName());
		
		/*
		 * now check what occurs when get tries to retrieve a course using a crn 
		 * that has not been added to the database
		 *  
		 *  in my implementation I simply return null to indicate the crn 
		 *  is not in the database
		 *  
		 *  NOTE: CourseDBStructure interface says to throw an IOException whenever 
		 *  course is not found. I do not think it should be throwing this type of 
		 *  exception but thats not important to this note. The CourseDBManagerInterface 
		 *  enforces that it's get method should NOT throw any checked exceptions
		 *  (b/c no throws clause) so my only option is to handle the IOException. 
		 *  But, then I have one inner method (CourseDBStructure's get method) 
		 *  throwing an exception and the other (CourseDBManager's get method) 
		 *  catching it and using a different methodology (returning null) to 
		 *  signify a unexpected situation. 
		 *  
		 *  I believe the two methods should be consistent in what they do 
		 *  under unexpected situations. In my opinion, either both should 
		 *  return null or both should throw an exception and GUI should 
		 *  catch it and print the message to the user. 
		 *  
		 */
		
		assertEquals(null, manager.get(19210)); //note: this will print a warning message to console as well
		
	}

//	@Test
//	void testReadFileWithGeneratedData() {
//		/*
//		 * first run CourseDataGenerator's main method 
//		 * 
//		 * default settings will create 400 lines of dummy 
//		 * course data to test with 
//		 * 
//		 * I will try to remember to provide more info on this 
//		 * class in the write up. 
//		 * 
//		 * NOTE: you will need to have the "FirstNames.txt" and "LastNames.txt" 
//		 * source data files in the root directory to run this test properly. I 
//		 * provide my entire project folder when turning in the assignment to 
//		 * ensure you have these. 
//		 */
//		
//		try {
//			CourseDataGenerator.main(null); //generates course data
//		} catch(FileNotFoundException e) { //should not fail 
//			fail("ensure the \"FirstNames.txt\" and \"LastNames.txt\" data files are included in the source directory of the project"); 
//		}
//		
//		File file = new File("CoursesData.txt");
//		
//		try {
//			manager.readFile(file);
//		} catch(FileNotFoundException e) {
//			fail("This will only fail if CourseDataGenerator's main method can't find the first name or last names "
//					+ "file specified in the note and first fail case");
//		}
//		
//		try {
//			Scanner fileScanner = new Scanner(file); 
//		} catch(FileNotFoundException e) {
//			fail("This will only fail if CourseDataGenerator's main method can't find the first name or last names "
//					+ "file specified in the note and first fail case");
//		}
//		
//		/*
//		 * here I realized that doing this would require knowing if crns in the generated file were duplicated. To 
//		 * do this I would either need to change the data generation class or search the entire file for the last 
//		 * instance of the CRN in the file so I know exactly what the values should be. 
//		 * 
//		 * At this point I am just going to give up on this attempt because there are also a bunch of 
//		 * "conditionals" (proper source files, proper generated file, etc) which I do not think 
//		 * matches traditional JUnit convention. That is, I am not sure if I am even allowed to do 
//		 * JUnit testing in this manner by generating dummy data and using it to test a method. 
//		 * 
//		 * I will still use generated data in the implementation of this test that I do keep
//		 * but the test data will be the same in every test to make it easier to work with. 
//		 */
//	}

	@Test
	void testReadFile() {
		/*
		 * ensure you have JUnitData.txt in the source folder 
		 * I will provide it with my project submission. That is, 
		 * I will send you my entire project folder. 
		 */
		File file = new File("JUnitData.txt"); 
		try {
			manager.readFile(file);
		} catch(FileNotFoundException e) {
			fail("this should not throw a FileNotFoundException as long as 'JUnitData.txt' is in the source folder"); 
		}
		
		/*
		 * now I will some CRNs from the file at random to test if they were added. 
		 * 
		 * NOTE: I do not know if all CRNs in the are unique or how many CRNs are not unique so I cannot
		 * check the size with this data set. 
		 */
		
		assertEquals("EIX761", manager.get(43296).getID()); //line 326
		assertEquals("FU438", manager.get(38687).getRoomNum()); //line 218
		assertEquals("Tamara Campbell", manager.get(62401).getInstructorName()); //line 85
		
		assertEquals("SBN829", manager.get(81734).getID()); //line 181
		assertEquals("UB644", manager.get(25980).getRoomNum()); //line 262
		assertEquals("Pedro Blake", manager.get(67401).getInstructorName()); //line 102
		
		
		assertEquals("TFQ072", manager.get(59701).getID()); //line 245
		assertEquals("ZG301", manager.get(89891).getRoomNum()); //line 399
		assertEquals("Karina Hoover", manager.get(14580).getInstructorName()); //line 77
		
		
	}
	@Test
	void testShowAll() {
		manager.add("KRU311", 67578, 3, "VU666", "Harvey Kinney"); //distinct 
		manager.add("PTO068", 82532, 1, "RF495", "Tad High"); //distinct
		manager.add("WTV651", 64443, 4, "TV021", "Francis Livingston"); //distinct 
		manager.add("DVD470", 89114, 5, "OZ933", "Matthew Riggs"); //distinct 
		manager.add("KFC631", 38783, 5, "LF257", "Keely Deal"); //distinct 
		
		
		manager.add("MJC088", 14729, 4, "KP942", "Claire Parker"); //not distinct
		manager.add("UKG815", 14729, 1, "KU482", "Willis Kay"); //not distinct 
		
		/*
		 * note: it is not a good idea to test iteration order so I will test the 
		 * size to ensure the correct number of elements have been added 
		 */
		
		ArrayList<String> resultList = manager.showAll(); 
		
		assertEquals(6, resultList.size()); 
		
		
		/*
		 * check if each crn is contained in the combined string 
		 */
		String combinedStr = "";
		for(String course : resultList){
			combinedStr += course;
		}
		
		assertTrue(combinedStr.contains("67578"));
		assertTrue(combinedStr.contains("82532"));
		assertTrue(combinedStr.contains("64443"));
		assertTrue(combinedStr.contains("89114"));
		assertTrue(combinedStr.contains("38783"));		
		assertTrue(combinedStr.contains("14729"));
	}

}
