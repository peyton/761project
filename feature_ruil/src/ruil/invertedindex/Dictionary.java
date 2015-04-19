package ruil.invertedindex;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Scanner;

import ruil.util.Config;
import ruil.util.ObjectSerializer;

public class Dictionary implements Serializable{
	private static final long serialVersionUID = 1L;

	long totalWordCount=0;
	
	HashSet<String> set=new HashSet<String>();
	
	private void buildDictionary(String filePathName) throws FileNotFoundException {
		int sid = 0;
		Scanner scanner = new Scanner(new File(filePathName));
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			line = line.replace("<s>", "");
			line = line.replace("</s>", "").trim();
			String[] seg = line.split(" ");
			for (int i = 0; i < seg.length; i++) {
				String key = seg[i];
				set.add(key);
				totalWordCount++;
			}

			if(sid%10000==0){
				System.out.println("Reading... "+sid);
			}
			sid++;
		}
		System.out.println("Total number of line is: " + sid);
		System.out.println("Total number of words in dictionary is: " + set.size());
		System.out.println("Total number of tokens in corpus is: " + totalWordCount);
		scanner.close();
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		Dictionary dict=new Dictionary();
		dict.buildDictionary(Config.CORPUS_FILEPATHNAME);
		ObjectSerializer.Write(Config.INDEX_CACHE, dict);
	}
}
