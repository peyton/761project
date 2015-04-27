package ruil.feature;

import ruil.util.Config;
import ruil.util.FileUtil;

public class NGramFeatureExtraction {

	public static void main(String[] args) {
		/*
		 * merge n-gram features
		 */
		FileUtil.mergeFeaturesFromFiles(Config.TRAINING_FEATURE_NGRAM, Config.TRAINING_FEATURE_NGRAM_MERGE);

		FileUtil.mergeFeaturesFromFiles(Config.DEVELOPMENT_FEATURE_NGRAM, Config.DEVELOPMENT_FEATURE_NGRAM_MERGE);

		FileUtil.mergeFeaturesFromFiles(Config.BAL_TRAINING_FEATURE_NGRAM, Config.BAL_TRAINING_FEATURE_NGRAM_MERGE);
	}

}
