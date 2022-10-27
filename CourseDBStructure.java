import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * this class represents the actual data structure holding the course elements. 
 * This class implements hashing using bucket chaining and works similarly
 *  to a ADT dictionary. I say similarly, because there are missing methods, 
 *  no distinct key and value objects, etc. This class does not implement rehashing. 
 * @author Christopher Perez Lebron
 *
 */
public class CourseDBStructure implements CourseDBStructureInterface {
	
	private int numberOfEntries; 
	private static final int MAX_CAPACITY = 10000; 
	
	private boolean integrityOK = false; 
	private static final double MAX_LOAD_FACTOR = 1.5; 
	private LinkedList<CourseDBElement>[] hashTable; 
	private int tableSize; //must be prime

	
	/**
	 * constructor that accepts an integer as the desired initial capacity 
	 * and sets the initial capacity to the first 4k + 3 prime number greater 
	 * than or equal to (initialCapacity / MAX_LOAD_FACTOR) 
	 * @param initialCapacity the desired capacity
	 */
	public CourseDBStructure(int initialCapacity) {
		tableSize = getNextPrime((int)(initialCapacity/MAX_LOAD_FACTOR));
		checkCapacity(tableSize); 
		
		@SuppressWarnings("unchecked")
		LinkedList<CourseDBElement>[] temp = (LinkedList<CourseDBElement>[]) new LinkedList[tableSize];
		hashTable = temp; 
		
		numberOfEntries = 0; 
		
		integrityOK = true;
		
	}
	
	/**
	 * creates a testing object? Not entirely sure what the true purpose 
	 * of this constructor is. I implemented it because the JUnit test
	 * makes use of it. The size parameter is inserted directly as the tableSize
	 * rather than trying to find the next prime number
	 * 
	 * @param str A string that I suppose is used to differentiate between this 
	 * constructor and the 1 argument instructor above
	 * @param size a integer representing the initial capacity of the hashTable 
	 */
	public CourseDBStructure(String str, int size) {
		/*
		 * NOTE: I am not sure how this constructor is remotely helpful in testing 
		 * but I will implementing because the JUnit test uses it
		 */
		tableSize = size; 
		
		checkCapacity(tableSize); 
		
		@SuppressWarnings("unchecked")
		LinkedList<CourseDBElement>[] temp = (LinkedList<CourseDBElement>[]) new LinkedList[tableSize];
		hashTable = temp; 
		
		numberOfEntries = 0; 
		
		integrityOK = true;
	}
	
	/**
	 * finds the "next" prime number that is greater than or equal to givenNum
	 * @param givenNum the starting point of the search 
	 * @return the "next" prime number that is greater than or equal to givenNum
	 */
	private int getNextPrime(int givenNum) {
		
		int k = givenNum / 4; //give the number of 4's in givenNum which will be our starting point for the search
		int prime = (4 * k) + 3; //by quotient remainder theorem this must be greater than or equal to givenNum
		
		/*
		 * it is unlikely this will occur but I include this to safeguard my isPrime method
		 */
		while(prime < 4) {
			k++; 
			prime = (4 * k) + 3;
		}
		
		while(!isPrime(prime)) {
			k++; 
			prime = (4 * k) + 3; 
		}
		
		return prime; 
	}
	
	/**
	 * checks is num is prime. This is done by checking if num is divisible by each integer 
	 * from 2 (including 2) to the floor of num's square root. 
	 * @param num a number that needs to be checked for being prime or not
	 * @return true if num is prime. False if num is not prime
	 */
	private boolean isPrime(int num) {
		//Precondition: num > 4; 
		boolean prime = true; 
		for(int count = 2; count <= (int)Math.sqrt(num); count++) {
			if(num % count == 0) {
				prime = false;
			}
		}
		return prime;
	}
	
	/**
	 * ensures that capacity parameter is less than or equal to MAX_CAPACITY field. 
	 * If not, an IllegalStateException is thrown
	 * @param capacity the size to be checked
	 * @throws IllegalStateException if the desire capacity is too large
	 */
	private void checkCapacity(int capacity) {
		if(capacity > MAX_CAPACITY) {
			throw new IllegalStateException("Capacity is too large");
		}
	}

	@Override
	/**
	 * adds a CourseDBElement object to the hash table. If the key already exists
	 * then the values in the existing/matching course object are updated. NOTE: 
	 * in my opinion this is a bad idea but once again I am adhering to the provided 
	 * JUnit test. 
	 * @param element a course to be added to the hash table
	 * @throws IllegalArgumentException if element or any of it's fields
	 *  are null or effectively null (-1) for integers
	 */
	public void add(CourseDBElement element) {
		checkIntegrity();
		
		if(element == null || element.getID() == null || element.getCRN() == -1 
				|| element.getNumberOfCredits() == -1 || element.getRoomNum() == null
				|| element.getInstructorName() == null)  //if the object is null or any of its fields are null or effectively null (-1) 
			throw new IllegalArgumentException("Database does not allow null entries or values"); 
		/*
		 * since CRN is known to be unique the hash function will simply be 
		 * CRN % tableSize
		 */
		String strCRN = "" + element.getCRN(); 
		int hashIndex = strCRN.hashCode() % tableSize; 
		
		
		/*
		 * check if the list at hashIndex (the bucket) is null 
		 * if so add a bucket then add the entry 
		 */
		if(hashTable[hashIndex] == null) {
			hashTable[hashIndex] = new LinkedList<CourseDBElement>();
			hashTable[hashIndex].add(element);
			numberOfEntries++;
		}
		/*
		 * check to see if this search key is already present in the 
		 * bucket that hashIndex points to 
		 */
		else { //hashTable[hashIndex] contains a linked list 
			Iterator<CourseDBElement> courseIterator = hashTable[hashIndex].iterator(); 
			CourseDBElement currentCourse = null; 
			boolean found = false;
			while(courseIterator.hasNext() && !found) { //iterator through all CourseDBElement objects in the bucket
				currentCourse = courseIterator.next(); 
				if(currentCourse.getCRN() == element.getCRN()) //if CourseDBElement with matching CRN is found
					found = true;
			}
			if(!found) {//if a matching search key was NOT found, then add the object to the bucket
				hashTable[hashIndex].add(element);
				numberOfEntries++;
			}
			if(found) { //if matching search key was found, then update all of its values (updating CRN is not necessary as they are equal) 
				currentCourse.setInstructorName(element.getInstructorName()); 
				currentCourse.setID(element.getID());
				currentCourse.setNumberOfCredits(element.getNumberOfCredits()); 
				currentCourse.setRoomNum(element.getRoomNum()); 
			}
		}
		
		//else, if a match was found do nothing 
	}
	
	/**
	 * checks the integrityOK field to ensure it is true
	 * @throws IllegalStateException if integrityOK is false (meaning 
	 * construction of this object did not terminate successfully 
	 */
	private void checkIntegrity() {
		if(!integrityOK) 
			throw new IllegalStateException();
	}

	@Override
	/**
	 * retrieves the CourseDBElement object with a matching CRN 
	 * @returns the CourseDBElement object if it is found (using CRN) 
	 * @throws IOException if matching CourseDBElement is not found. 
	 * NOTE: this really seems like the incorrect exception to 
	 * throw in this case. However, once again, I am adhering to 
	 * project specifications (Interface) 
	 */
	public CourseDBElement get(int crn) throws IOException {
		String strCRN = "" + crn;
		int hashIndex = strCRN.hashCode() % tableSize;
		
		if(hashTable[hashIndex] == null)
			throw new IOException("No match for CRN: " + crn + " in the database");
		else { //this bucket is not empty so we much search the bucket
			Iterator<CourseDBElement> courseIterator = hashTable[hashIndex].iterator();
			while(courseIterator.hasNext()) {
				CourseDBElement currentCourse = courseIterator.next();
				if(currentCourse.getCRN() == crn) //if match is found 
					return currentCourse;
			}
			
			/*
			 * if method gets to this point then a matching course was not found so 
			 * throw an IOException as specified by documentation
			 */
			throw new IOException("No match for CRN: " + crn + " in database"); 
		}
	}

	@Override
	/**
	 * retrieves all database objects as a ArrayList of strings
	 * @return resultList an ArrayList of strings in which each 
	 * element is the string representation of a CourseDBElement 
	 * object in the databse
	 */
	public ArrayList<String> showAll() {
		
		ArrayList<String> resultList = new ArrayList<String>(); 
		/*
		 * for each index in the hashTable
		 */
		for(int count = 0; count < tableSize; count++) {
			/*
			 * if current index is not null (meaning there must be CourseDBElement objects 
			 * stored at this index
			 */
			if(hashTable[count] != null) {
				Iterator<CourseDBElement> courseIterator = hashTable[count].iterator();
				/*
				 * go through each element in this bucket and store it's string representation in resultList
				 */
				while(courseIterator.hasNext())
					
					resultList.add(courseIterator.next().toString()); //NOTE: I changed overrode the toString method for CourseDBElement
			}
		}
		return resultList; 
	}

	@Override
	/**
	 * retrieves the size of this hash table
	 * @return tableSize the field representing the size of this hash table
	 */
	public int getTableSize() {
		return tableSize;
	}
}
