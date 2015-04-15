package ruil.invertedindex;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import ruil.util.Config;
import ruil.util.ObjectSerializer;
import ruil.util.TimeWatch;

/*
 * Inverted index is a singleton class
 */
public class InvertedIndexRevised implements Serializable {

	private static final long serialVersionUID = 1L;

	private static InvertedIndexRevised instance = null;

	/*
	 * key is a word, value is the position list
	 */
	TreeMap<String, TreeMap<Integer, ArrayList<Integer>>> index;

	long totalWordCount;
	int dictionarySize;

	public static InvertedIndexRevised getInstance() {
		if (instance == null) {
			instance = new InvertedIndexRevised(Config.CORPUS_FILEPATHNAME);
		}
		return instance;
	}

	protected InvertedIndexRevised(String filePathName) {
		index = new TreeMap<String, TreeMap<Integer, ArrayList<Integer>>>();

		try {
			TimeWatch watch = TimeWatch.start();
			buildIndex(filePathName);
			long passedTimeInSeconds = watch.time(TimeUnit.SECONDS);
			System.out.println("Building index took "+passedTimeInSeconds+"s.");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		dictionarySize = index.size();
	}

	public long getTotalWordCount() {
		return totalWordCount;
	}

	public int getDictionarySize() {
		return this.dictionarySize;
	}

	public int getWordCount(String word) {
		String key = word.toUpperCase();
		if (index.containsKey(key)) {
			return index.get(key).size();
		}
		return 0;
	}

	/*
	 * L and U stand for the lower and upper bounds of distances such that
	 * L<=|p(w1)-p(w2)|<=U
	 */
	public int countCoocurrence(String w1, String w2, int L, int U) {
		int count = 0;

		w1 = w1.toUpperCase();
		w2 = w2.toUpperCase();

		TreeMap<Integer, ArrayList<Integer>> pLs1 = index.get(w1);
		TreeMap<Integer, ArrayList<Integer>> pLs2 = index.get(w2);

		if (pLs1 == null || pLs2 == null) {
			return 0;
		}
		
		Iterator<Integer> iterator=pLs1.keySet().iterator();
		while(iterator.hasNext()){
			int key=iterator.next();
			if(pLs2.containsKey(key)){
				count+=countCoocurrence(pLs1.get(key), pLs2.get(key), L, U);
			}
		}

		return count;
	}
	
	private int countCoocurrence(ArrayList<Integer> pLs1, ArrayList<Integer> pLs2, int L, int U){
		int count=0;
		int s1 = 0, s2 = 0;
		while (s1 < pLs1.size() && s2 < pLs2.size()) {
			int p1 = pLs1.get(s1);
			int p2 = pLs2.get(s2);
			if (checkBoundary(L, U, p1, p2)) {
				count++;
			}
			
			if (p1 > p2) {
				s2++;
			} else {
				s1++;
			}
		}
		return count;
	}

	private boolean checkBoundary(int L, int U, int p1, int p2) {
		int dst = Math.abs(p1 - p2);
		if (dst >= L && dst <= U) {
			return true;
		}

		return false;
	}

	public int getCoocurrence(String[] words) {
		String[] keys = new String[words.length];
		for (int i = 0; i < keys.length; i++) {
			keys[i] = words[i].toUpperCase();
		}

		return 0;
	}

	/*
	 * works for the corpus
	 * http://www.cs.cmu.edu/~roni/11761-s15/project/LM-train-100MW.txt.gz
	 */
	private void buildIndex(String filePathName) throws FileNotFoundException {
		int sid = 0;
		Scanner scanner = new Scanner(new File(filePathName));
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			line = line.replace("<s>", "");
			line = line.replace("</s>", "").trim();
			String[] seg = line.split(" ");
			for (int i = 0; i < seg.length; i++) {
				String key = seg[i];
				if (!index.containsKey(key)) {
					index.put(key, new TreeMap<Integer, ArrayList<Integer>>());
				}
				if(!index.get(key).containsKey(sid)){
					index.get(key).put(sid, new ArrayList<Integer>());
				}
				index.get(key).get(sid).add(i);

				totalWordCount++;
			}

			if(sid%10000==0){
				System.out.println("Reading... "+sid);
			}
			sid++;
		}
		System.out.println("Total number of line is: " + sid);
		scanner.close();
	}

	public static void main(String[] args) {
		InvertedIndexRevised index=InvertedIndexRevised.getInstance();
		ObjectSerializer.Write(Config.INDEX_CACHE, index);
	}

}
