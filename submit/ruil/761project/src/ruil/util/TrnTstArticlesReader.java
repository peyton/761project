package ruil.util;

import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import ruil.training.Article;

public class TrnTstArticlesReader {

	public static ArrayList<ArrayList<ArrayList<String>>> readArticles(
			String filePathName) throws FileNotFoundException {
		ArrayList<ArrayList<ArrayList<String>>> articles = new ArrayList<ArrayList<ArrayList<String>>>();
		Scanner scanner = new Scanner(new File(filePathName));
		ArrayList<ArrayList<String>> article = null;
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			if (line.contains("~~~~~")) {
				article = new ArrayList<ArrayList<String>>();
				articles.add(article);
				continue;
			}

			String sentence = SentenceNormalizer.normalize(line);
			article.add(SentenceNormalizer.tokenize(sentence));
		}

		scanner.close();

		return articles;
	}

	private static ArrayList<ArrayList<String>> readArticlesI(
			String filePathName) throws FileNotFoundException {
		ArrayList<ArrayList<String>> articles = new ArrayList<ArrayList<String>>();
		Scanner scanner = new Scanner(new File(filePathName));
		ArrayList<String> article = null;
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			if (line.contains("~~~~~")) {
				article = new ArrayList<String>();
				articles.add(article);
				continue;
			}

			String sentence = SentenceNormalizer.normalize(line);
			article.add(sentence);
		}

		scanner.close();
		return articles;
	}

	public static ArrayList<Article> readArticles(String articleFilePathName,
			String labelFilePathName) throws FileNotFoundException {
		ArrayList<ArrayList<String>> articles = readArticlesI(articleFilePathName);
		ArrayList<Short> labels = readLabels(labelFilePathName);
		ArrayList<Article> newArticles = new ArrayList<Article>();
		for (int i = 0; i < articles.size(); i++) {
			newArticles.add(new Article(articles.get(i), labels.get(i)
					.shortValue()));
		}

		return newArticles;
	}

	public static ArrayList<Short> readLabels(String filePathName)
			throws FileNotFoundException {
		ArrayList<Short> labelLs = new ArrayList<Short>();
		Scanner scanner = new Scanner(new File(filePathName));
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			labelLs.add(Short.parseShort(line));
		}

		scanner.close();
		return labelLs;
	}

	public static void main(String[] args) throws FileNotFoundException {
		ArrayList<ArrayList<ArrayList<String>>> articles = TrnTstArticlesReader
				.readArticles(Config.TRAINING_ARTICALE_FILEPATHNAME);
		System.out.println("Total article size is: " + articles.size());

		ArrayList<Short> labels = TrnTstArticlesReader
				.readLabels(Config.TRAINING_ARTICALE_LABEL_FILEPATHNAME);
		System.out.println("Total article label size is: " + labels.size());
	}

}
