package ruil.feature;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import ruil.util.Config;
import ruil.util.FileUtil;
import ruil.util.TrnTstArticlesReader;

public class FeatureStandarization {

	static ArrayList<Double> extractAvgFeatures(
			ArrayList<ArrayList<Double>> scores) {
		ArrayList<Double> results = new ArrayList<Double>();
		for (int i = 0; i < scores.size(); i++) {
			ArrayList<Double> curScores = scores.get(i);
			if (curScores==null||curScores.size() == 0) {
				results.add(0.0);
			} else {
				double sum = 0;
				for (int j = 0; j < curScores.size(); j++) {
					sum += curScores.get(j);
				}
				sum /= curScores.size();
				results.add(sum);
			}
		}
		return results;
	}

	static ArrayList<Double> extractMinFeatures(
			ArrayList<ArrayList<Double>> scores) {
		ArrayList<Double> results = new ArrayList<Double>();
		for (int i = 0; i < scores.size(); i++) {
			ArrayList<Double> curScores = scores.get(i);
			if (curScores.size() == 0) {
				results.add(0.0);
			} else {
				double min = curScores.get(0);
				for (int j = 1; j < curScores.size(); j++) {
					if (min > curScores.get(j)) {
						min = curScores.get(j);
					}
				}
				results.add(min);
			}
		}
		return results;
	}

	static ArrayList<Double> extractMaxFeatures(
			ArrayList<ArrayList<Double>> scores) {
		ArrayList<Double> results = new ArrayList<Double>();
		for (int i = 0; i < scores.size(); i++) {
			ArrayList<Double> curScores = scores.get(i);
			if (curScores.size() == 0) {
				results.add(0.0);
			} else {
				double max = curScores.get(0);
				for (int j = 1; j < curScores.size(); j++) {
					if (max < curScores.get(j)) {
						max = curScores.get(j);
					}
				}
				results.add(max);
			}
		}
		return results;
	}
	
	static void normalize(String triggersNetworkSenFilePathName, String selfTriggerFilePathName,
			String ngramFilePathName, String labelFilePathName, String outputFeatureFilePathName)
			throws FileNotFoundException {
		/* 
		 * Trigger Network Sentence
		 */
		List<String> senlines = FileUtil.readFile(triggersNetworkSenFilePathName);

		ArrayList<ArrayList<Double>> senTriggerNetworkScores = new ArrayList<ArrayList<Double>>();
		for (int i = 0; i < senlines.size(); i++) {
			ArrayList<Double> curScores = new ArrayList<Double>();
			String line = senlines.get(i);
			if (!line.equals("")) {
				String[] seg = line.split(" ");
				for (int j = 0; j < seg.length; j++) {
					curScores.add(Double.parseDouble(seg[j].substring(seg[j]
							.indexOf(":") + 1)));
				}
			}
			senTriggerNetworkScores.add(curScores);
		}

		ArrayList<Double> senScoreAvg = FeatureStandarization
				.extractAvgFeatures(senTriggerNetworkScores);
		
		/*
		 * Self Trigger
		 */
		ArrayList<Double[]> selfTriggerScores=new ArrayList<Double[]>();
		List<String> lines = FileUtil.readFile(selfTriggerFilePathName);
		for (int i = 0; i < lines.size(); i++) {
			String[] scoreStrs=lines.get(i).split(" ");
			Double[] scores=new Double[scoreStrs.length];
			for(int j=0;j<scores.length;j++){
				scores[j]=Double.parseDouble(scoreStrs[j]);
			}
			selfTriggerScores.add(scores);
		}
		

		/*
		 *  n-Gram
		 */
		List<String> ngramLines = FileUtil.readFile(ngramFilePathName);
		ArrayList<Double[]> ngramScores=new ArrayList<Double[]>();
		for (int i = 0; i < ngramLines.size(); i++) {
			String[] scoreStrs=ngramLines.get(i).split(" ");
			Double[] scores=new Double[scoreStrs.length];
			for(int j=0;j<scores.length;j++){
				scores[j]=Double.parseDouble(scoreStrs[j]);
			}
			ngramScores.add(scores);
		}
		
		ArrayList<Short> labels = TrnTstArticlesReader
				.readLabels(labelFilePathName);

		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < labels.size(); i++) {
			builder.append(labels.get(i));
			for(int j=0;j<ngramScores.get(i).length;j++){
				builder.append(" "+(j+1)+":" + ngramScores.get(i)[j]);
			}
			builder.append(" "+(ngramScores.get(i).length+1)+":" + senScoreAvg.get(i));
			builder.append(" "+(ngramScores.get(i).length+2)+":" + selfTriggerScores.get(i)[0]);
			builder.append(" "+(ngramScores.get(i).length+3)+":" + selfTriggerScores.get(i)[1]);
			builder.append(" "+(ngramScores.get(i).length+4)+":" + selfTriggerScores.get(i)[2]);
			builder.append("\n");
		}

		FileUtil.writeToFile(outputFeatureFilePathName, builder);
	}
	
	static void normalizeTest(String triggersNetworkSenFilePathName, String selfTriggerFilePathName,
			String ngramFilePathName, String outputFeatureFilePathName)
			throws FileNotFoundException {
		/* 
		 * Trigger Network Sentence
		 */
		List<String> senlines = FileUtil.readFile(triggersNetworkSenFilePathName);

		ArrayList<ArrayList<Double>> senTriggerNetworkScores = new ArrayList<ArrayList<Double>>();
		for (int i = 0; i < senlines.size(); i++) {
			ArrayList<Double> curScores = new ArrayList<Double>();
			String line = senlines.get(i);
			if (!line.equals("")) {
				String[] seg = line.split(" ");
				for (int j = 0; j < seg.length; j++) {
					curScores.add(Double.parseDouble(seg[j].substring(seg[j]
							.indexOf(":") + 1)));
				}
			}
			senTriggerNetworkScores.add(curScores);
		}

		ArrayList<Double> senScoreAvg = FeatureStandarization
				.extractAvgFeatures(senTriggerNetworkScores);
		
		/*
		 * Self Trigger
		 */
		ArrayList<Double[]> selfTriggerScores=new ArrayList<Double[]>();
		List<String> lines = FileUtil.readFile(selfTriggerFilePathName);
		for (int i = 0; i < lines.size(); i++) {
			String[] scoreStrs=lines.get(i).split(" ");
			Double[] scores=new Double[scoreStrs.length];
			for(int j=0;j<scores.length;j++){
				scores[j]=Double.parseDouble(scoreStrs[j]);
			}
			selfTriggerScores.add(scores);
		}
		

		/*
		 *  n-Gram
		 */
		List<String> ngramLines = FileUtil.readFile(ngramFilePathName);
		ArrayList<Double[]> ngramScores=new ArrayList<Double[]>();
		for (int i = 0; i < ngramLines.size(); i++) {
			String[] scoreStrs=ngramLines.get(i).split(" ");
			Double[] scores=new Double[scoreStrs.length];
			for(int j=0;j<scores.length;j++){
				scores[j]=Double.parseDouble(scoreStrs[j]);
			}
			ngramScores.add(scores);
		}
	

		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < selfTriggerScores.size(); i++) {
			builder.append(1);
			for(int j=0;j<ngramScores.get(i).length;j++){
				builder.append(" "+(j+1)+":" + ngramScores.get(i)[j]);
			}
			builder.append(" "+(ngramScores.get(i).length+1)+":" + senScoreAvg.get(i));
			builder.append(" "+(ngramScores.get(i).length+2)+":" + selfTriggerScores.get(i)[0]);
			builder.append(" "+(ngramScores.get(i).length+3)+":" + selfTriggerScores.get(i)[1]);
			builder.append(" "+(ngramScores.get(i).length+4)+":" + selfTriggerScores.get(i)[2]);
			builder.append("\n");
		}

		FileUtil.writeToFile(outputFeatureFilePathName, builder);
	}
	
	static void normalize(String triggersNetworkSenFilePathName, String triggersNetworkArtFilePathName, String selfTriggerFilePathName,
			String ngramFilePathName, String labelFilePathName, String outputFeatureFilePathName)
			throws FileNotFoundException {
		/* 
		 * Trigger Network Sentence
		 */
		List<String> senlines = FileUtil.readFile(triggersNetworkSenFilePathName);

		ArrayList<ArrayList<Double>> senTriggerNetworkScores = new ArrayList<ArrayList<Double>>();
		for (int i = 0; i < senlines.size(); i++) {
			ArrayList<Double> curScores = new ArrayList<Double>();
			String line = senlines.get(i);
			if (!line.equals("")) {
				String[] seg = line.split(" ");
				for (int j = 0; j < seg.length; j++) {
					curScores.add(Double.parseDouble(seg[j].substring(seg[j]
							.indexOf(":") + 1)));
				}
			}
			senTriggerNetworkScores.add(curScores);
		}

		ArrayList<Double> senScoreAvg = FeatureStandarization
				.extractAvgFeatures(senTriggerNetworkScores);
		ArrayList<Double> scoreMax = FeatureStandarization
				.extractMaxFeatures(senTriggerNetworkScores);
		ArrayList<Double> scoreMin = FeatureStandarization
				.extractMinFeatures(senTriggerNetworkScores);
		
		/*
		 * Trigger Network Article
		 */
		List<String> artlines = FileUtil.readFile(triggersNetworkArtFilePathName);

		ArrayList<ArrayList<Double>> artTriggerNetworkScores = new ArrayList<ArrayList<Double>>();
		for (int i = 0; i < artlines.size(); i++) {
			ArrayList<Double> curScores = new ArrayList<Double>();
			String line = artlines.get(i);
			if (!line.equals("")) {
				String[] seg = line.split(" ");
				for (int j = 0; j < seg.length; j++) {
					curScores.add(Double.parseDouble(seg[j].substring(seg[j]
							.indexOf(":") + 1)));
				}
			}
			artTriggerNetworkScores.add(curScores);
		}

		ArrayList<Double> artScoreAvg = FeatureStandarization
				.extractAvgFeatures(artTriggerNetworkScores);
		
		
		/*
		 * Self Trigger
		 */
		ArrayList<Double[]> selfTriggerScores=new ArrayList<Double[]>();
		List<String> lines = FileUtil.readFile(selfTriggerFilePathName);
		for (int i = 0; i < lines.size(); i++) {
			String[] scoreStrs=lines.get(i).split(" ");
			Double[] scores=new Double[scoreStrs.length];
			for(int j=0;j<scores.length;j++){
				scores[j]=Double.parseDouble(scoreStrs[j]);
			}
			selfTriggerScores.add(scores);
		}
		

		/*
		 *  n-Gram
		 */
		List<String> ngramLines = FileUtil.readFile(ngramFilePathName);
		ArrayList<Double[]> ngramScores=new ArrayList<Double[]>();
		for (int i = 0; i < ngramLines.size(); i++) {
			String[] scoreStrs=ngramLines.get(i).split(" ");
			Double[] scores=new Double[scoreStrs.length];
			for(int j=0;j<scores.length;j++){
				scores[j]=Double.parseDouble(scoreStrs[j]);
			}
			ngramScores.add(scores);
		}
		
		ArrayList<Short> labels = TrnTstArticlesReader
				.readLabels(labelFilePathName);

		StringBuilder builder = new StringBuilder();
		
//		for (int i = 0; i < labels.size(); i++) {
//			builder.append(labels.get(i));
//			//builder.append(" 1:" + senScoreAvg.get(i));
//			builder.append(" 1:" + artScoreAvg.get(i));
//			//builder.append(" 2:" + Log(scoreMax.get(i)));
//			//builder.append(" 3:" + Log(scoreMin.get(i)));
////			builder.append(" 3:" + selfTriggerScores.get(i)[0]);
////			builder.append(" 4:" + selfTriggerScores.get(i)[1]);
////			builder.append(" 5:" + selfTriggerScores.get(i)[2]);
//			builder.append("\n");
//		}
//		
		for (int i = 0; i < labels.size(); i++) {
			builder.append(labels.get(i));
			for(int j=0;j<ngramScores.get(i).length;j++){
				builder.append(" "+(j+1)+":" + ngramScores.get(i)[j]);
			}
			builder.append(" "+(ngramScores.get(i).length+1)+":" + senScoreAvg.get(i));
			builder.append(" "+(ngramScores.get(i).length+2)+":" + selfTriggerScores.get(i)[0]);
			//builder.append(" "+(ngramScores.get(i).length+3)+":" + selfTriggerScores.get(i)[1]);
			//builder.append(" "+(ngramScores.get(i).length+4)+":" + artScoreMax.get(i));
			//builder.append(" "+(ngramScores.get(i).length+5)+":" + artScoreMin.get(i));
			//builder.append(" "+(ngramScores.get(i).length+3)+":" + selfTriggerScores.get(i)[2]);
			builder.append("\n");
		}

		FileUtil.writeToFile(outputFeatureFilePathName, builder);
	}
	

	public static void main(String[] args) throws FileNotFoundException {
		FeatureStandarization.normalize(
				Config.TRAINING_FEATURE_TRIGGERSNETWORK_SEN,
				Config.TRAINING_FEATURE_SELFTRIGGER,
				Config.TRAINING_FEATURE_NGRAM_MERGE,
				Config.TRAINING_ARTICALE_LABEL_FILEPATHNAME,
				Config.TRAINING_FEATURE);
		
		FeatureStandarization.normalize(
				Config.BAL_TRAINING_FEATURE_TRIGGERSNETWORK,
				Config.BAL_TRAINING_FEATURE_SELFTRIGGER,
				Config.BAL_TRAINING_FEATURE_NGRAM_MERGE,
				Config.BAL_TRAINING_LABEL_FILEPATHNAME,
				Config.BAL_TRAINING_FEATURE);

		FeatureStandarization.normalize(
				Config.DEVELOPMENT_FEATURE_TRIGGERSNETWORK_SEN,
				Config.DEVELOPMENT_FEATURE_SELFTRIGGER,
				Config.DEVELOPMENT_FEATURE_NGRAM_MERGE,
				Config.DEVELOPMENT_ARTICALE_LABEL_FILEPATHNAME,
				Config.DEVELOPMENT_FEATURE);
		
		
//		FeatureStandarization.normalize(
//				Config.TRAINING_FEATURE_TRIGGERSNETWORK_SEN,
//				Config.TRAINING_FEATURE_TRIGGERSNETWORK_ART,
//				Config.TRAINING_FEATURE_SELFTRIGGER,
//				Config.TRAINING_FEATURE_NGRAM_MERGE,
//				Config.TRAINING_ARTICALE_LABEL_FILEPATHNAME,
//				Config.TRAINING_FEATURE);
//		

//
//		FeatureStandarization.normalize(
//				Config.DEVELOPMENT_FEATURE_TRIGGERSNETWORK_SEN,
//				Config.DEVELOPMENT_FEATURE_TRIGGERSNETWORK_ART,
//				Config.DEVELOPMENT_FEATURE_SELFTRIGGER,
//				Config.DEVELOPMENT_FEATURE_NGRAM_MERGE,
//				Config.DEVELOPMENT_ARTICALE_LABEL_FILEPATHNAME,
//				Config.DEVELOPMENT_FEATURE);
	}

}
