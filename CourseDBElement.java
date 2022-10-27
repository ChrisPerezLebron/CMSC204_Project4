/**
 * This class represents an entry in the database. The CRN 
 * field is used as the key and the entire object is returned as 
 * the "value" 
 * @author Christopher Perez Lebron 
 *
 */
public class CourseDBElement implements Comparable<CourseDBElement>{

	private String courseID; 
	private int crn; 
	private int numberOfCredits; 
	private String roomNumber; 
	private String instructorName; 
	
	/**
	 * constructor that initializes all the fields
	 * @param course a string representing the course id EX: CMSC203
	 * @param courseRegNum a integer representing the CRN 
	 * @param numCredits a integer representing the number of credits the 
	 * course is worth
	 * @param roomNum a string representing the room number for the class
	 * @param profName a string representing the professor's name 
	 */
	public CourseDBElement(String course, int courseRegNum, 
				int numCredits, String roomNum, String profName) {
		courseID = course;
		crn = courseRegNum; 
		numberOfCredits = numCredits; 
		roomNumber = roomNum; 
		instructorName = profName;
	}
	
	/**
	 * no-arg constructor. It is only provided because 
	 * FXMainPane uses it. See note inside. 
	 */
	public CourseDBElement() {
		/*
		 * NOTE: I think having a no-arg constructor is a bad idea as it is more 
		 * likely to lead to null values. However, FXMainPane uses it. 
		 */
		courseID = null; 
		crn = -1; 
		numberOfCredits = -1; 
		roomNumber = null;
		instructorName = null;
		
	}
	
	@Override
	/**
	 * classic compareTo method. The method is used to compare two course objects. 
	 * Importantly, the compareTo method only compares the CRN of this object 
	 * and the CRN of the other object. See note inside.
	 * @return 1 if this crn is larger than course's crn 
	 * 0 if this crn is equal to course's crn
	 * -1 if this crn is less than coure's crn
	 */
	public int compareTo(CourseDBElement course) {
		/*
		 * no info was given why or how the project wants us to implement this method. Personally, 
		 * I see no purpose in having this method. In this database we have no reason to check if 
		 * two courseDBElement objects are equal, or if one object is less than or greater than 
		 * another object. The only way to make use of this method is to make it return 0 if 
		 * the CRN's are equal (w/o taking into account the other fields) but using the compareTo 
		 * method within my code just to check if the CRN's are equal would make my code much 
		 * harder to read. 
		 * 
		 * Therefore, I will implement this method using only the CRN's to compare the two objects. 
		 * However, I will not use it in my code as it will only serve to make my code harder to 
		 * read. 
		 */
		int returnValue = -9999; 
		if(this.crn == course.crn) 
			returnValue = 0; 
		else if(this.crn < course.crn)
			returnValue = -1; 
		else if(this.crn > course.crn)
			returnValue = 1; 
		
		return returnValue; 
		
	}
	
	/**
	 * retrieves the courseID
	 * @return courseID's value
	 */
	public String getID() {
		return this.courseID;
	}
	
	/**
	 * sets courseID value
	 * @param id a string representing the value to be inserted into the 
	 * courseID field
	 */
	public void setID(String id) {
		courseID = id; 
	}
	
	/**
	 * retrieve the CRN for this course
	 * @return the CRN for this course
	 */
	public int getCRN() {
		return this.crn;
	}
	
	/**
	 * get the number of credits this course is worth
	 * @return numberOfCredits field
	 */
	public int getNumberOfCredits() {
		return this.numberOfCredits;
	}
	
	/**
	 * set the number of credits this course is worth
	 * @param numCred the new number of credits this course will be worth. 
	 * that is, the number to be inserted into the numberOfCredits field. 
	 */
	public void setNumberOfCredits(int numCred) {
		numberOfCredits = numCred;
	}
	
	/**
	 * retrieve the room number for this course
	 * @return roomNumber field
	 */
	public String getRoomNum() {
		return this.roomNumber;
	}
	
	/**
	 * set the room number for this course
	 * @param room a string representing the new room number for this 
	 * course
	 */
	public void setRoomNum(String room) {
		roomNumber = room;
	}
	
	/**
	 * retrieve the name of the instructor for this course
	 * @return instructorName field
	 */
	public String getInstructorName() {
		return this.instructorName;
	}
	
	/**
	 * set the instructor name for this course
	 * @param name a String object representing the 
	 * new name for the instructor
	 */
	public void setInstructorName(String name) {
		instructorName = name;
	}
	
	/**
	 * this method set's the CRN value for the course. SEE NOTE INSIDE 
	 * (I think having this method is a bad idea but FXMainPane uses it. 
	 * @param courseRegNum a integer representing the new CRN for this course object
	 */
	public void setCRN(int courseRegNum) {
		/*
		 * NOTE: I think being able to set the CRN after construction 
		 * is a bad idea but FXMainPane uses this method
		 */
		crn = courseRegNum;
	}
	
	@Override
	/**
	 * converts this course object into a String representation of the form: 
	 * Course:CMSC500 CRN:39999 Credits:4 Instructor:Nobody InParticular Room:SC100
	 * @return str a string representing this course object 
	 */
	public String toString() {
		//Course:CMSC500 CRN:39999 Credits:4 Instructor:Nobody InParticular Room:SC100
		
		String str = ""; 
		
		str += "\nCourse:" + courseID + " CRN:" + crn + " Credits:" + numberOfCredits + " Instructor:" + instructorName + " Room:" + roomNumber;
		return str;
	}
	
	
	

	
}
