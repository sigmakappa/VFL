package fileLister;

import java.sql.Timestamp;

public class ConsoleCommunicate {
	final static String ANSI_RESET = "\u001B[0m";
	final static String ANSI_GREEN = "\u001B[32m";
	final static String ANSI_RED = "\u001B[31m";
	final static String ANSI_YELLOW = "\u001B[33m";
	
	/*public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";*/
	
	static void initiate(String message){
		java.util.Date date= new java.util.Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		System.out.println(timestamp+" : "+ANSI_YELLOW + message + ANSI_RESET);
	}
	
	static void success(String message){
		java.util.Date date= new java.util.Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		System.out.println(timestamp+" : "+ ANSI_GREEN +message + ANSI_RESET);
	}
	
	static void alert(String message){
		java.util.Date date= new java.util.Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		System.out.println(timestamp+" : "+ ANSI_RED +message + ANSI_RESET);
	}
	
	
}
