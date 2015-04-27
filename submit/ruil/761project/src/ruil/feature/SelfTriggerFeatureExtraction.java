package ruil.feature;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import ruil.feature.triggersnetwork.SelfTriggers;
import ruil.util.Config;
import ruil.util.FileUtil;
import ruil.util.TrnTstArticlesReader;

public class SelfTriggerFeatureExtraction {
	StringBuilder extractSelfTriggerFeatures(
			ArrayList<ArrayList<ArrayList<String>>> articles) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < articles.size(); i++) {
			StringBuilder current = new StringBuilder();
			ArrayList<ArrayList<String>> article = articles.get(i);
			double[] fs=new double[3];
			fs[0] = SelfTriggers.extract_withoutSW(article);
			fs[1] = SelfTriggers.extract(article);
			fs[2] = SelfTriggers.normalizedEntropy(article);
			current.append(fs[0]+" "+fs[1] + " "+fs[2]+"\n");
			builder.append(current);
		}
		return builder;
	}

	public static void main(String[] args) throws FileNotFoundException {
		SelfTriggerFeatureExtraction f = new SelfTriggerFeatureExtraction();

		FileUtil.writeToFile(Config.TRAINING_FEATURE_SELFTRIGGER, f
				.extractSelfTriggerFeatures(TrnTstArticlesReader
						.readArticles(Config.TRAINING_ARTICALE_FILEPATHNAME)));
		
		FileUtil.writeToFile(
				Config.DEVELOPMENT_FEATURE_SELFTRIGGER,
				f.extractSelfTriggerFeatures(TrnTstArticlesReader
						.readArticles(Config.DEVELOPMENT_ARTICALE_FILEPATHNAME)));
		
		FileUtil.writeToFile(Config.BAL_TRAINING_FEATURE_SELFTRIGGER, f
				.extractSelfTriggerFeatures(TrnTstArticlesReader
						.readArticles(Config.BAL_TRAINING_ARTICALE_FILEPATHNAME)));
	}

}
