package ruil.invertedindex;

public abstract class InMemoryInvertedIndex {
	abstract public int countCoocurrence(String w1, String w2, int L, int U);
	abstract public int getWordCount(String word);
	abstract public long getTotalWordCount();
	abstract public long getTotalNonStopWordCount();
}
