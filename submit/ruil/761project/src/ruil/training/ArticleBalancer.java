package ruil.training;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import ruil.util.Config;
import ruil.util.FileUtil;
import ruil.util.TrnTstArticlesReader;

public class ArticleBalancer {

	static int[] SIZES = { 1, 1, 2, 3, 4, 5, 7, 10, 15, 20 };

	static ArrayList<Article> sampling(ArrayList<Article> articles) {
		ArrayList<Article> newArticles = new ArrayList<Article>();
		while (true) {
			for (int i = 0; i < SIZES.length; i++) {
				Article aStar = null;
				for (int j = 0; j < articles.size(); j++) {
					Article curArticle = articles.get(j);
					if (curArticle.sentences.size() >= SIZES[i]) {
						if (aStar == null) {
							aStar = curArticle;
						} else {
							if (curArticle.sentences.size() < aStar.sentences
									.size()) {
								aStar = curArticle;
							}
						}
					}
				}

				if (aStar == null) {
					return newArticles;
				}

				Article newArticle = new Article(new ArrayList<String>(),
						aStar.label);
				for (int j = 0; j < SIZES[i]; j++) {
					newArticle.sentences.add(aStar.sentences.get(0));
					aStar.sentences.remove(0);
				}
				newArticles.add(newArticle);
			}
		}
	}

	static void write(String outputTrnFilePathName,
			String outputLabelFilePathName, ArrayList<Article> articles) {
		StringBuilder trnBuilder = new StringBuilder();
		StringBuilder labelBuilder = new StringBuilder();
		for (int i = 0; i < articles.size(); i++) {
			trnBuilder.append("~~~~~\n");
			for (int j = 0; j < articles.get(i).sentences.size(); j++) {
				trnBuilder.append("<s>");
				trnBuilder.append(articles.get(i).sentences.get(j));
				trnBuilder.append("</s>\n");
			}

			labelBuilder.append(articles.get(i).label);
			if (i != articles.size() - 1) {
				labelBuilder.append("\n");
			}
		}

		FileUtil.writeToFile(outputTrnFilePathName, trnBuilder);
		FileUtil.writeToFile(outputLabelFilePathName, labelBuilder);
	}

	public static void main(String[] args) throws FileNotFoundException {
		ArticleBalancer.write(Config.BAL_TRAINING_ARTICALE_FILEPATHNAME,
				Config.BAL_TRAINING_LABEL_FILEPATHNAME,
				ArticleBalancer.sampling(TrnTstArticlesReader.readArticles(
						Config.TRAINING_ARTICALE_FILEPATHNAME,
						Config.TRAINING_ARTICALE_LABEL_FILEPATHNAME)));
	}

}
