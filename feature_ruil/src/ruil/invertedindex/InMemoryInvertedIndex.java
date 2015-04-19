package ruil.invertedindex;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import ruil.util.Config;
import ruil.util.ObjectSerializer;
import ruil.util.TimeWatch;

/*
 * Inverted index is a singleton class
 */
public class InMemoryInvertedIndex implements Serializable {

	private static final long serialVersionUID = 1L;

	private static InMemoryInvertedIndex instance = null;

	/*
	 * key is a word, value is the position list
	 */
	TreeMap<String, ArrayList<Position>> index;

	long totalWordCount;
	int dictionarySize;

	public static InMemoryInvertedIndex getInstance() {
		if (instance == null) {
			instance = new InMemoryInvertedIndex(Config.CORPUS_FILEPATHNAME);
		}
		return instance;
	}

	protected InMemoryInvertedIndex(String filePathName) {
		index = new TreeMap<String, ArrayList<Position>>();

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
	public int getCoocurrence(String w1, String w2, int L, int U) {
		int count = 0;

		w1 = w1.toUpperCase();
		w2 = w2.toUpperCase();

		ArrayList<Position> pLs1 = index.get(w1);
		ArrayList<Position> pLs2 = index.get(w2);

		if (pLs1 == null || pLs2 == null) {
			return 0;
		}

		int s1 = 0, s2 = 0;
		while (s1 < pLs1.size() && s2 < pLs2.size()) {
			Position p1 = pLs1.get(s1);
			Position p2 = pLs2.get(s2);
			if (p1.sid == p2.sid) {
				if (checkBoundary(L, U, p1.offset, p2.offset)) {
					count++;
				}
			} else if (p1.sid > p2.sid) {
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
		boolean overflow = false;
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
					index.put(key, new ArrayList<Position>());
				}
				if ((short) i != i) {
					overflow = true;
				}
				index.get(key).add(new Position(sid, (short) i));

				totalWordCount++;
			}

			if (sid % 10000 == 0) {
				System.out.println("Reading... " + sid);
			}
			sid++;
		}
		System.out.println("Total number of line is: " + sid);
		if (overflow) {
			System.out.println("Overflow occured!");
		}

		scanner.close();
	}

	/*
	 * the position is used for the inverted index
	 */
	class Position implements Comparable<Position>, Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		int sid;
		short offset;

		public Position(int sid, short offset) {
			this.sid = sid;
			this.offset = offset;
		}

		@Override
		public int compareTo(Position arg0) {
			if (arg0.sid > this.sid) {
				return 1;
			} else if (arg0.sid == this.sid) {
				if (arg0.offset > this.offset) {
					return 1;
				}
			}
			return 0;
		}
	}

	public static void main(String[] args) {
		InMemoryInvertedIndex index = InMemoryInvertedIndex.getInstance();
		ObjectSerializer.Write(Config.INDEX_CACHE, index);
	}

}
