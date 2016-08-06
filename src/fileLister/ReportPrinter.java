package fileLister;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class ReportPrinter extends ConsoleCommunicate{
	
	/**
	 * Prints the file redundancy report 
	 * 
	 * @param redundencyList - The ArrayList<String[]> which contains all the names of
	 *                files which are redundant (by their exact names) 
	 * 
	 * @author Shagun Kaushik [shagunprakash19@gmail.com]
	 * @param directoriesList 
	 */
	static void printRedundancyReport(ArrayList<String> directoriesList, ArrayList<ArrayList<String>> redundencyList) throws FileNotFoundException
	{
		//for printing the output in the html file
        FileOutputStream fileoutput = new FileOutputStream("Redundancy Report.html");
	    PrintWriter pw = new PrintWriter(fileoutput);
		
	    ConsoleCommunicate.initiate("Preparing report...");
		int size = redundencyList.size();
		
		pw.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01//EN\" \"http://www.w3.org/TR/html4/strict.dtd\">"
				+ "<html>"
				+ "<head>"
				+ "<meta content=\"text/html; charset=ISO-8859-1\" http-equiv=\"content-type\">"
				+ "<title>Redundancy Report.html</title></head><body><br style=\"font-family: Courier New;\">"
				+ "<font style=\"font-family: Courier New;\" size=\"+2\"><span style=\"font-weight: bold;\">"
				+ "&nbsp; &nbsp;"
				+ "</span>"
				+ "</font>"
				+ "<big><big style=\"font-weight: bold; font-family: Courier New; color: rgb(204, 0, 0);\">"
				+ "<big>Video File Redundancy Report</big>&nbsp;"
				+ "</big>"
				+ "<span style=\"font-family: Courier New;\">"
				+ "<span style=\"font-weight: bold;\">v2.1"
				+ "</span>"
				+ "</span>"
				+ "</big>"
				+ "<br style=\"font-family: Courier New;\">"
				+ "<br style=\"font-family: Courier New;\">"
				+ "<span style=\"font-family: Courier New;\">"
				+ "&nbsp; &nbsp; &nbsp;"
				+ "<big>"
				+ "<span style=\"font-weight: bold;\">Report includes drives/directories:</span>"
				+ "</big>"
				+ "</span>"
				+ "<br style=\"font-family: Courier New;\">"
				+ "<ul style=\"font-family: Courier New;\">");
		
		for(int i= 1 ; i <= directoriesList.size() ; i++){
			pw.println("<li style=\"font-weight: bold; margin-left: 22px; width: 825px;\">"
				+ directoriesList.get(i-1)
				+ "</li>");
		}
				
		pw.println("</ul>"
				+ "<br style=\"font-family: Courier New;\">");
		
		// Table Heading
		pw.println("<table style=\"width: 90%; text-align: left; margin-left: auto; margin-right: auto;"
				+ " font-family: Courier New;\" border=\"1\"cellpadding=\"2\" cellspacing=\"2\">"
				+ "<tbody>"
				+ "<tr><td style=\"width: 248px; text-align: center; font-weight: bold;\">"
				+ "<font size=\"+2\">File Name</font>"
				+ "</td>"
				+ "<td style=\"width: 438px; text-align: center; font-weight: bold;\">"
				+ "<font size=\"+2\">Location</font></td>"
				+ "<td style=\"width: 113px; text-align: center; font-weight: bold;\">"
				+ "<font size=\"+2\">Size</font></td></tr>");
		
		
		for(int i = 0 ; i <= (size-1) ; i++)
		{
			ArrayList<String> data = redundencyList.get(i);
			int dataSize = data.size();

			pw.println("<tr>"
					+ "<td style=\"width: 248px; text-align: center; color: green; font-weight: bold;\" bgcolor=\"99ff9a\">"
					+ data.get(0)
					+ "</td>"
					+ "<td style=\"width: 438px; text-align: center; color: green; font-weight: bold;\" bgcolor=\"99ff9a\">"
					+ "<a href=\""+ makeProperPath(data.get(1))  +"\" target=\"_blank\">"+  data.get(1)  +"</a>"
					+ "</td>"
					+ "<td style=\"width: 113px; text-align: center; color: green; font-weight: bold;\" bgcolor=\"99ff9a\">"
					+ makeProperSize(data.get(2))
					+ "</td>"
					+ "</tr>");
			
			int dataSizePairs = (dataSize - 3)/2;
			for(int j = 1 ; j <= dataSizePairs ; j++)
			{
				pw.println("<tr>"
						+ "<td style=\"text-align: center;\" bgcolor=\"#9ba39b\"></td>"
						+ "<td style=\"text-align: center; color: red; font-weight: bold;\" bgcolor=\"#ffb2b2\">"
						+ "<a href=\""+ makeProperPath(data.get((j * 2)+1))  +"\" target=\"_blank\">"+  data.get((j * 2)+1)  +"</a>"
						+ "</td>"
						+ "<td style=\"text-align: center; color: red; font-weight: bold;\" bgcolor=\"#ffb2b2\">"
						+ makeProperSize(data.get((j * 2)+2))
						+ "</td>"
						+ "</tr>");
			}
		}
		
		pw.println("</td></tr></tbody></table><br></body></html>");		
		pw.close();
		ConsoleCommunicate.success("Report complete!");
	}

	static String makeProperPath(String path){
		if(System.getProperty("os.name").toLowerCase().contains("windows")){
			path = path.replace("\\", "\\\\");
			path = "file:///"+ path;
		}
		else if(true){
			// do accordingly to the OS
			// Yet to be done for other OSs
		}
		
		return path;
	}
	
	/**
	 * Returns the file size in Bytes, KBs, MBs, GBs and TBs 
	 * 
	 * @param sizeInBytes - File size in bits
	 * 
	 * @author Shagun Kaushik [shagunprakash19@gmail.com]
	 */
	static String makeProperSize(String sizeInBytes){
		String storageUnit = null;
		DecimalFormat df = new DecimalFormat("#.##"); 
		double sizeNumeric = Double.parseDouble(sizeInBytes.trim());
		
		if(sizeNumeric < 1000){
			storageUnit = "Bytes";
		}
		
		if(sizeNumeric >= 1000){
			sizeNumeric = sizeNumeric/1024;
			storageUnit = "KB";
		}
			
		if(sizeNumeric >= 1000){
			sizeNumeric = sizeNumeric/1024;
			storageUnit = "MB";
		}	
		
		if(sizeNumeric >= 1000){
			sizeNumeric = sizeNumeric/1024;
			storageUnit = "GB";
		}
		
		if(sizeNumeric >= 1000){
			sizeNumeric = sizeNumeric/1024;
			storageUnit = "TB";
		}
		return df.format(sizeNumeric) + " " + storageUnit;
	}
	
	
	
	
	
}
