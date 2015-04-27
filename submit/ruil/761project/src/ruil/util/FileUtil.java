package ruil.util;
import java.util.Collections;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

	public static List<String> readFile(String filePathName) {
		List<String> lines = new ArrayList<String>();
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(filePathName));
			String line = br.readLine();
			while (line != null) {
				lines.add(line.trim());
				line = br.readLine();
			}
			br.close();
		} catch (Exception e) {
		}
		return lines;
	}

	public static void writeToFile(String filename, StringBuilder buffer) {
		BufferedWriter bufferedWriter = null;

		try {
			// Construct the BufferedWriter object
			bufferedWriter = new BufferedWriter(new FileWriter(filename));

			// Start writing to the output stream
			bufferedWriter.write(buffer.toString());

		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			// Close the BufferedWriter
			try {
				if (bufferedWriter != null) {
					bufferedWriter.flush();
					bufferedWriter.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	public static void mergeFeaturesFromFiles(String filePath, String outputFilePathName) {
		 ArrayList<String> featureFileName=listFilesForFolder(new File(filePath));
         System.out.println("filePath " + filePath + " outFilePath " + outputFilePathName );
		System.out.println("got file Name" + featureFileName);
		StringBuilder builder = new StringBuilder();
		List<List<String>> features = new ArrayList<List<String>>();
		for (int i = 0; i < featureFileName.size(); i++) {
			String curFilePathName = filePath + "/" + featureFileName.get(i);
			List<String> lines = readFile(curFilePathName);
			features.add(lines);
		}

		for (int i = 0; i < features.get(0).size(); i++) {
			for (int j = 0; j < features.size(); j++) {
				builder.append(features.get(j).get(i)+" ");
			}
			builder.append("\n");
		}
		
		writeToFile(outputFilePathName, builder);
	}

	public static ArrayList<String> listFilesForFolder(final File folder) {
		ArrayList<String> list = new ArrayList<String>();
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				listFilesForFolder(fileEntry);
			} else {
				list.add(fileEntry.getName());
				// System.out.println(fileEntry.getName());
			}
		}
        Collections.sort(list);
		return list;
	}

}
