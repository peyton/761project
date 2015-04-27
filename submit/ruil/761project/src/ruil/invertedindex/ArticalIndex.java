package ruil.invertedindex;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import ruil.util.Config;
import ruil.util.ObjectSerializer;
import ruil.util.SentenceNormalizer;
import ruil.util.StopWords;
import ruil.util.TimeWatch;

/*
 * Inverted index is a singleton class
 */
public class ArticalIndex extends InMemoryInvertedIndex{

	private static ArticalIndex instance = null;
	private static int articalSize = 5;

	/*
	 * key is a word, value is the position list
	 */
	TreeMap<String, TreeMap<Integer, ArrayList<Short>>> index;

	long totalWordCount;
	int dictionarySize;
	long totalNonStopWordCount;

	public static ArticalIndex getInstance() {
		if (instance == null) {
			instance = new ArticalIndex(Config.CORPUS_FILEPATHNAME);
		}
		return instance;
	}

	protected ArticalIndex(String filePathName) {
		StopWords.getInstance();
		index = new TreeMap<String, TreeMap<Integer, ArrayList<Short>>>();

		try {
			TimeWatch watch = TimeWatch.start();
			buildIndex(filePathName);
			long passedTimeInSeconds = watch.time(TimeUnit.SECONDS);
			System.out.println("Building index took " + passedTimeInSeconds
					+ "s.");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		dictionarySize = index.size();
	}

	public long getTotalWordCount() {
		return totalWordCount;
	}

	public long getTotalNonStopWordCount() {
		return totalNonStopWordCount;
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

		TreeMap<Integer, ArrayList<Short>> pLs1 = index.get(w1);
		TreeMap<Integer, ArrayList<Short>> pLs2 = index.get(w2);

		if (pLs1 == null || pLs2 == null) {
			return 0;
		}

		Iterator<Integer> iterator = pLs1.keySet().iterator();
		while (iterator.hasNext()) {
			int key = iterator.next();
			if (pLs2.containsKey(key)) {
				count += countCoocurrence(pLs1.get(key), pLs2.get(key), L, U);
			}
		}

		return count;
	}

	
	private int countCoocurrence(ArrayList<Short> pLs1,
			ArrayList<Short> pLs2, int L, int U) {
		int count=0;
		for(int i=0;i<pLs1.size();i++){
			int cur=pLs1.get(i);
			int leftH=cur-(L-1);
			int leftL=cur-(U-1);
			
			int idxLeftL=BinarySearch.searchFirstGreater(pLs2, leftL);
			int idxLeftH=BinarySearch.searchFirstGreater(pLs2, leftH);
			if(pLs2.get(idxLeftH)!=leftH){
				idxLeftH--;
			}
			
			int tmp=idxLeftH-idxLeftL+1;
			if(tmp<=0){
				tmp=0;
			}else{
				count+=tmp;
				//continue;
			}
			
			int rightL=cur+(L-1);
			int rightH=cur+(U-1);
			
			int idxRightL=BinarySearch.searchFirstGreater(pLs2, rightL);
			int idxRightH=BinarySearch.searchFirstGreater(pLs2, rightH);
			if(pLs2.get(idxRightH)!=rightH){
				idxRightH--;
			}
			
			tmp=idxRightH-idxRightL+1;
			if(tmp<=0){
				tmp=0;
			}else{
				count+=tmp;
				//continue;
			}
		}
		
		return count;
	}
	
	private int countCoocurrenceI(ArrayList<Short> pLs1,
			ArrayList<Short> pLs2, int L, int U) {
		int count=0;
		for(int i=0;i<pLs1.size();i++){
			int cur=pLs1.get(i);
			int leftH=cur-(L-1);
			int leftL=cur-(U-1);
			
			int idxLeftL=BinarySearch.searchFirstGreater(pLs2, leftL);
			int idxLeftH=BinarySearch.searchFirstGreater(pLs2, leftH);
			if(pLs2.get(idxLeftH)!=leftH){
				idxLeftH--;
			}
			
			int tmp=idxLeftH-idxLeftL+1;
			if(tmp<=0){
				tmp=0;
			}
			count+=tmp;
			
			int rightL=cur+(L-1);
			int rightH=cur+(U-1);
			
			int idxRightL=BinarySearch.searchFirstGreater(pLs2, rightL);
			int idxRightH=BinarySearch.searchFirstGreater(pLs2, rightH);
			if(pLs2.get(idxRightH)!=rightH){
				idxRightH--;
			}
			
			tmp=idxRightH-idxRightL+1;
			if(tmp<=0){
				tmp=0;
			}
			count+=tmp;
		}
		
		return count;
	}
	
	private int countCoocurrenceII(ArrayList<Integer> pLs1,
			ArrayList<Integer> pLs2, int L, int U) {
		int count=0;
		for(int i=0;i<pLs1.size();i++){
			for(int j=0;j<pLs2.size();j++){
				if (checkBoundary(L, U, pLs1.get(i), pLs2.get(j))) {
					count++;
				}
			}
		}

		return count;
	}

	private boolean checkBoundary(int L, int U, int p1, int p2) {
		int dst = Math.abs(p1 - p2);
		if (dst >= L-1 && dst <= U-1) {
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
		int lineCount = 1;
		int posIdx = 0;
		Scanner scanner = new Scanner(new File(filePathName));
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			line = SentenceNormalizer.normalize(line);

			String[] seg = line.split(" ");
			for (int i = 0; i < seg.length; i++) {
				String key = seg[i];

				if (!StopWords.contains(key.toLowerCase())) {
					if (!index.containsKey(key)) {
						index.put(key,
								new TreeMap<Integer, ArrayList<Short>>());
					}
					if (!index.get(key).containsKey(sid)) {
						index.get(key).put(sid, new ArrayList<Short>());
					}
					if ((short) posIdx != posIdx) {
						System.out.println("Overflow!");
						System.exit(0);
					}
					index.get(key).get(sid).add((short)posIdx);
					totalNonStopWordCount++;
				}

				posIdx++;
				totalWordCount++;
			}

			if (lineCount % 10000 == 0) {
				System.out.println("Reading... " + lineCount);
			}

			if (lineCount % articalSize == 0) {
				sid++;
				posIdx = 0;
			}
			lineCount++;
		}
		System.out.println("Total number of line is: " + sid);
		scanner.close();
	}

	public static void main(String[] args) {
		ArticalIndex.getInstance();
	}

}
