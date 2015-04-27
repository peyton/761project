package ruil.feature;

import java.io.FileNotFoundException;

import ruil.util.Config;
import ruil.util.FileUtil;
import ruil.util.TrnTstArticlesReader;

public class FeatureExtraction {

	public static void main(String[] args) throws FileNotFoundException {
		String testingFilePathName=args[0];
		
    	/*
		 * Feature 1
		 */
		
		TriggerNetworkSentenceFeatureExtraction f1 = new TriggerNetworkSentenceFeatureExtraction();
		
    	FileUtil.writeToFile(Config.TESTING_FEATURE_TRIGGERSNETWORK_SEN, f1.extractTriggerNetworkFeatures(TrnTstArticlesReader
				.readArticles(testingFilePathName)));

	
        /*
		 * Feature 2
		 */
		SelfTriggerFeatureExtraction f2 = new SelfTriggerFeatureExtraction();

		FileUtil.writeToFile(Config.TESTING_FEATURE_SELFTRIGGER, f2
				.extractSelfTriggerFeatures(TrnTstArticlesReader
						.readArticles(testingFilePathName)));
		
		/*
		 * merge n-gram features
		 */
		FileUtil.mergeFeaturesFromFiles(Config.TESTING_FEATURE_NGRAM, Config.TESTING_FEATURE_NGRAM_MERGE);

		FeatureStandarization.normalizeTest(
				Config.TESTING_FEATURE_TRIGGERSNETWORK_SEN,
				Config.TESTING_FEATURE_SELFTRIGGER,
				Config.TESTING_FEATURE_NGRAM_MERGE,
				//Config.TESTING_ARTICALE_LABEL_FILEPATHNAME,
				Config.TESTING_FEATURE);
	}

}
