package ruil.util;

import java.util.ArrayList;

public class SentenceNormalizer {

	public static String normalize(String sentence) {
		sentence = sentence.replace("<s>", "");
		sentence = sentence.replace("</s>", "").trim();
		sentence = sentence.replace("'S", "");
		return sentence;
	}

	public static ArrayList<String> tokenize(String sentence) {
		ArrayList<String> tokens = new ArrayList<String>();
		String[] seg = sentence.split(" ");
		for (int i = 0; i < seg.length; i++) {
			tokens.add(seg[i]);
		}

		return tokens;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
