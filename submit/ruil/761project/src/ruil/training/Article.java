package ruil.training;

import java.util.ArrayList;

public class Article {
	public ArrayList<String> sentences;
	public short label;

	public Article(ArrayList<String> sentences, short label) {
		this.sentences = sentences;
		this.label = label;
	}
}
