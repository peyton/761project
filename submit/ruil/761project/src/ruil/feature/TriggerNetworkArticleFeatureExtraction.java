package ruil.feature;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ruil.feature.triggersnetwork.SelfTriggers;
import ruil.feature.triggersnetwork.TriggersNetwork;
import ruil.invertedindex.SentenceIndex;
import ruil.invertedindex.ArticalIndex;
import ruil.invertedindex.InMemoryInvertedIndex;
import ruil.util.Config;
import ruil.util.FileUtil;
import ruil.util.StopWords;
import ruil.util.TrnTstArticlesReader;

public class TriggerNetworkArticleFeatureExtraction {
	InMemoryInvertedIndex index;

	TriggersNetwork trigger;

	public TriggerNetworkArticleFeatureExtraction() {
		index = ArticalIndex.getInstance();
		trigger = new TriggersNetwork(index);
		StopWords.getInstance();
	}

	StringBuilder extractTriggerNetworkFeatures(ArrayList<ArrayList<ArrayList<String>>> articles
			) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < articles.size(); i++) {
			HashMap<String, Double> mymap=new  HashMap<String, Double>();
			StringBuilder current = new StringBuilder();
			ArrayList<ArrayList<String>> article = articles.get(i);
			for (int j = 0; j < article.size(); j++) {
				ArrayList<String> sentence = article.get(j);
				ArrayList<Integer> counts=new ArrayList<Integer>();
				double f = trigger.averageWeightOfEdges(counts, sentence, mymap);
				current.append(j + ":" + f +" ");
				//current.append(j + ":" + f + "-"+counts.get(0)+"-"+sentence.size()+" ");
			}
			current.append("\n");
			builder.append(current);
			System.out.println("Article: "+i);
		}
		return builder;
	}


	public static void main(String[] args) throws FileNotFoundException {
		TriggerNetworkArticleFeatureExtraction f = new TriggerNetworkArticleFeatureExtraction();
		
		FileUtil.writeToFile(Config.TRAINING_FEATURE_TRIGGERSNETWORK_ART, f.extractTriggerNetworkFeatures(TrnTstArticlesReader
				.readArticles(Config.TRAINING_ARTICALE_FILEPATHNAME)));

		FileUtil.writeToFile(Config.DEVELOPMENT_FEATURE_TRIGGERSNETWORK_ART, f.extractTriggerNetworkFeatures(TrnTstArticlesReader
				.readArticles(Config.DEVELOPMENT_ARTICALE_FILEPATHNAME)));
		
//		FileUtil.writeToFile(Config.BAL_TRAINING_FEATURE_TRIGGERSNETWORK, f.extractTriggerNetworkFeatures(TrnTstArticlesReader
//				.readArticles(Config.BAL_TRAINING_ARTICALE_FILEPATHNAME)));
		
	}

}
