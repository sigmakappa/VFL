package fileLister;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

public class redundancyCheck extends ReportPrinter{

	public static void main(String[] args) throws IOException {
		final String ANSI_RED = "\u001B[31m";
		final String ANSI_RESET = "\u001B[0m";

		ArrayList<String> directoriesList = new ArrayList<String>(); 
		ArrayList<ArrayList<String>> files = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String>> redundencyList = new ArrayList<ArrayList<String>>();

		InputStreamReader istream = new InputStreamReader(System.in) ;
		BufferedReader bufRead = new BufferedReader(istream) ;

		System.out.println("Enter number of directories to be scanned for redundancy: ");
		System.out.println(ANSI_RED + "[NOTE: No more than 7 directories]" + ANSI_RESET);
		String directories = bufRead.readLine();

		if (Integer.parseInt(directories) > 7){
			System.out.println("You friggin' jokin'? I said lesser than 7! "
					+ "If you entered wrong one more time, I'm goin home!"
					+ "Go on, enter once again...");
			directories = bufRead.readLine();
		}

		if (Integer.parseInt(directories) > 7){
			System.out.println("I'm goin' home! See you never again... ;)");
			System.exit(0);
		} 

		if (Integer.parseInt(directories) <= 7){
			String dir;

			for(int number = 1 ; number <= Integer.parseInt(directories) ; number ++){
				System.out.println("Enter directory "+number+" :");
				dir = bufRead.readLine();
				//String directoryName = getDirectory(dir);
				directoriesList.add(dir);
			}
		}

		// preparing the list of files
		ConsoleCommunicate.initiate("Scanning files in directory...");
		for(int number = 1 ; number <= Integer.parseInt(directories) ; number ++){
			File directory = new File(directoriesList.get(number-1));
			listCreator(directory.toString(), files);
		}
		ConsoleCommunicate.success("Scanning of files complete!");

		int size = files.size();

		//System.out.println("Before sorting...");
		//printList(files);

		bubbleFileSorter(files);

		//System.out.println("");
		//System.out.println("After sorting...");
		//System.out.println("");
		//printList(files);

		redundencyList = checkSureRedundancyList(files);

		//System.out.println("");
		//System.out.println("After redundancy checks, the redundancy report:");
		//System.out.println("");

		size = redundencyList.size();
		for(int i = 0 ; i <= (size-1) ; i++)
		{
			ArrayList<String> data = redundencyList.get(i);
			int dataSize = data.size();
			//System.out.println(data.get(0)+"              "+data.get(1)+"              "+data.get(2));
			int dataSizePairs = (dataSize - 3)/2;
			for(int j = 1 ; j <= dataSizePairs ; j++){}
			//System.out.println("<-----Space---->   "+data.get((j * 2) + 1)+"              "+data.get((j * 2) + 2));
		}

		printRedundancyReport(directoriesList, redundencyList);
	}

	/**
	 * Checks for redundancy in a single list of files sorted lexicographically 
	 * 
	 * @param files - The ArrayList<String[]> containing all the names of files, their 
	 *                corresponding directory locations and respective sizes (sorted 
	 *                lexicographically w.r.t the file names)
	 * 
	 * @return sureRedundancy - The ArrayList<String[]> which contains all the names of
	 *                files which are redundant (by their exact names) 
	 *                
	 * @author Shagun Kaushik [shagunprakash19@gmail.com]
	 */
	private static ArrayList<ArrayList<String>> checkSureRedundancyList(ArrayList<ArrayList<String>> files) 
	{
		ArrayList<ArrayList<String>> sureRedundancyList = new ArrayList<ArrayList<String>>();
		//ArrayList<String> redundancyRecord = null;
		String first, second;
		ArrayList<String> data1;
		ArrayList<String> data2;
		int size = files.size();

		ConsoleCommunicate.initiate("Preparing the list of redundant files in directory...");
		for (int i=0 ; i <= (size-2) ; i++ )
		{
			data1 = files.get(i);
			data2 = files.get(i+1);
			first = data1.get(0);
			second = data2.get(0);

			// The below method compareToIgnoreCase returns :
			// a negative integer .... String is greater than
			// zero               .... Equal to
			// a positive integer .... Less than this String
			// ignoring case considerations (lexicographically)
			if (first.compareToIgnoreCase(second) == 0)
			{
				// filling up the redundancy list when the list is empty
				if (sureRedundancyList.size() == 0)
				{
					ArrayList<String> redundancyRecord = null;
					redundancyRecord = data1;			// Add all data from first record
					redundancyRecord.add(data2.get(1));		// Add location from second record
					redundancyRecord.add(data2.get(2));		// Add size from second data
					sureRedundancyList.add(redundancyRecord);	// Add the above data to the main data file
					//redundancyRecord.clear();
				}

				// filling up the redundancy list when the list is !empty
				// Here checking if the redundant data is already present in the sureRedundancyList
				// If "YES", then it'll do the steps below
				else if (sureRedundancyList.size() > 0)
				{
					ArrayList<String> data = sureRedundancyList.get(sureRedundancyList.size()-1);
					if (data.get(0).compareToIgnoreCase(second) == 0)
					{
						ArrayList<String> redundancyRecord = null;
						redundancyRecord = data;
						sureRedundancyList.remove(sureRedundancyList.size()-1);		// Remove main data from sureRedundancyList
						redundancyRecord.add(data2.get(1));		// Add location from second record
						redundancyRecord.add(data2.get(2));		// Add size from second data
						sureRedundancyList.add(redundancyRecord);	// Add the above data to the main data file
						//redundancyRecord.clear();
					}

					// If "NO", then it'll do the steps below
					else 
					{
						ArrayList<String> redundancyRecord = null;
						redundancyRecord = data1;			// Add all data from first record
						redundancyRecord.add(data2.get(1));		// Add location from second record
						redundancyRecord.add(data2.get(2));		// Add size from second data
						sureRedundancyList.add(redundancyRecord);	// Add the above data to the main data file
						//redundancyRecord.clear();
					}// else
				}// if (sureRedundancyList.size() > 0)
			}// if (first.compareToIgnoreCase(second) == 0)
		}// for

		ConsoleCommunicate.success("List of redundant files prepared!");
		return sureRedundancyList;

	}

	/**
	 * Sorts the list of files lexicographically [using bubble sort] 
	 * 
	 * @param files - The ArrayList<String[]> containing all the names of
	 *                files (unsorted), their corresponding directory locations and respective sizes 
	 * 
	 * @author Shagun Kaushik [shagunprakash19@gmail.com]
	 */
	private static ArrayList<ArrayList<String>> bubbleFileSorter(ArrayList<ArrayList<String>> files) {

		String first, second;
		ArrayList<String> data1;
		ArrayList<String> data2;
		int size = files.size();

		ConsoleCommunicate.initiate("Sorting files in directory...");
		for(int i=0 ; i <= size-1; i++)
		{
			for(int j=0 ; j < size-i-1; j++)
			{
				data1 = files.get(j);
				data2 = files.get(j+1);
				first = data1.get(0);
				second = data2.get(0);

				// The below method compareToIgnoreCase returns :
				// a negative integer .... String is greater than
				// zero               .... Equal to
				// a positive integer .... Less than this String
				// ignoring case considerations (lexicographically)

				if (first.compareToIgnoreCase(second) > 0)
				{
					files.add(j, data2);     // if second name is smaller, it is copied first
					files.remove(j+2);       // and the earlier entry is removed!
				}//if	
			}//for
		}//for

		ConsoleCommunicate.success("Sorting of files complete!");
		return files;

	}//bubbleFileSorter

	/**
	 * Checking if the directory provided above is a directory or a drive path.
	 * If it is a drive path, this assigns the String 'directoryName' the 
	 * value corresponding to the drive path
	 * 
	 * @param directory - The directory or the drive path
	 * 
	 * @author Shagun Kaushik [shagunprakash19@gmail.com]
	 */
	static String getDirectory(File directory){
		String directoryName = null;

		if(directory.getParent() == null)
		{
			JFileChooser fc = new JFileChooser(directory);
			FileSystemView fsv = fc.getFileSystemView();
			directoryName = fsv.getSystemDisplayName(directory).toString();
			int length = directoryName.split(" ").length;
			String[] directoryNames = directoryName.split(" ");
			directoryName = directoryNames[length-1];
			directoryName = directoryName.replace(":", "");
			directoryName = directoryName.replace(")", "");
			directoryName = directoryName.replace("(", "");
		}//if
		else
			directoryName = directory.getName();

		return directoryName;
	}//getDirectory

	/**
	 * Makes a list of all relevant/required files present recursively in the specified directory 
	 * 
	 * @param directoryName - The name of directory or the drive path
	 * @param files - The ArrayList<String[]> which shall contain all the names of
	 *                files, their corresponding directory locations and respective sizes 
	 * 
	 * @author Shagun Kaushik [shagunprakash19@gmail.com]
	 * @return 
	 */
	public static ArrayList<ArrayList<String>> listCreator(String directoryName, ArrayList<ArrayList<String>> files)
	{
		File directory = new File(directoryName);

		// get all the files from a directory
		File[] fList = directory.listFiles();
		for (File file : fList) 
		{
			if (file.isFile()) 
			{
				String value = checkFormat(file.getName());
				if (value.equals("true")){
					ArrayList<String> fileData = new ArrayList<String>(); 
					fileData.add(file.getName());
					fileData.add(file.getParent());
					fileData.add(String.valueOf(file.length()));
					files.add(fileData);
				}
			}

			if (file.isDirectory()) {
				/* Condition below applicable only on windows systems
				 * Reason: Recycle Bin folders starting with $ create problem while traversal
				 * and also 'System Volume Information' folders while traversal
				 */
				if(System.getProperty("os.name").toLowerCase().contains("windows") 
						&& (file.getName().toString().startsWith("$")
								|| file.getName().contains("System Volume Information")))
					continue;
				else
				{
					String absPath = file.getPath();
					//System.out.println(absPath);
					listCreator(absPath, files);
				}
			}//if
		}//for

		return files;
	}//listCreator


	/**
	 * Checks if a file belongs to any of the specified file format 
	 * 
	 * @param fileName - The name of file (including its file format)
	 * 
	 * @return check - 'true' when file belongs to any of the specified file formats;
	 * 				   else false
	 * 
	 * @author Shagun Kaushik [shagunprakash19@gmail.com]
	 */
	private static String checkFormat(String fileName) {
		String check = "false";
		String[] validFormats = {
				".mp4",
				".avi",
				".dat",
				".flv",
				".mkv",
				".mov",
				".wmv",							   
		};

		for (int i=0; i<=validFormats.length - 1; i++)
		{
			if (fileName.toLowerCase().endsWith(validFormats[i].toString()))
				check = "true";
		}
		return check;
	}//checkFormat


	private static void printList(ArrayList<ArrayList<String>> files) {
		int size = files.size();
		for(int i=0; i< size ; i++)
		{
			ArrayList<String> data = files.get(i);
			System.out.println(data.get(0)+"              "+data.get(1)+"              "+data.get(2));
			//data.clear();
		}
	}
}
