package ruil.util;

public class Config {
	public static final String CORPUS_FILEPATHNAME="/home/ubuntu/761project/data/LM-train-100MW.txt";
	
	public static final String TRAINING_ARTICALE_FILEPATHNAME="/home/ubuntu/761project/data/trainingSet.dat";
	public static final String TRAINING_ARTICALE_LABEL_FILEPATHNAME="/home/ubuntu/761project/data/trainingSetLabels.dat";
	
	public static final String BAL_TRAINING_ARTICALE_FILEPATHNAME="/home/ubuntu/761project/data/trainingSetLabelsBalanced.dat";
	public static final String BAL_TRAINING_LABEL_FILEPATHNAME="/home/ubuntu/761project/data/trainingSetLabelsBalanced.dat";

	public static final String TRAINING_FEATURE_TRIGGERSNETWORK_ART="dummy";
	public static final String TRAINING_FEATURE_TRIGGERSNETWORK_SEN="/home/ubuntu/761project/submit/tmpDir/trainingSetFeatures_triggerNetwork_sen";
	public static final String TRAINING_FEATURE_SELFTRIGGER="/home/ubuntu/761project/submit/tmpDir/tmpFeat/trainingSetFeatures_selfTrigger";
	public static final String TRAINING_FEATURE="/home/ubuntu/761project/submit/tmpDir/trainingSetFeatures";
	
	public static final String BAL_TRAINING_FEATURE_TRIGGERSNETWORK="/home/ubuntu/761project/submit/tmpDir/trainingSetFeaturesBalanced_triggerNetwork";
	public static final String BAL_TRAINING_FEATURE_SELFTRIGGER="/home/ubuntu/761project/submit/tmpDir/trainingSetFeaturesBalanced_selfTrigger";
	public static final String BAL_TRAINING_FEATURE="/home/ubuntu/761project/submit/tmpDir/tmpFeat/trainingSetFeaturesBalanced";
	
	public static final String BAL_TRAINING_FEATURE_NGRAM="/home/ubuntu/761project/data/BalTrain";
	public static final String BAL_TRAINING_FEATURE_NGRAM_MERGE="/home/ubuntu/761project/submit/tmpDir/tmpFeat/bal_train_merge";
	
	
	public static final String DEVELOPMENT_ARTICALE_FILEPATHNAME="/home/ubuntu/761project/data/developmentSet.dat";
	public static final String DEVELOPMENT_FEATURE_SELFTRIGGER="/home/ubuntu/761project/submit/tmpDir/tmpFeat/developmentSetFeatures_selfTrigger";
	public static final String DEVELOPMENT_ARTICALE_LABEL_FILEPATHNAME="/home/ubuntu/761project/data/developmentSetLabels.dat";
	
	public static final String DEVELOPMENT_FEATURE_TRIGGERSNETWORK_ART="dummy2";
	public static final String DEVELOPMENT_FEATURE_TRIGGERSNETWORK_SEN="/home/ubuntu/761project/submit/tmpDir/tmpFeat/developmentSetFeatures_triggerNetwork_sen";
	public static final String DEVELOPMENT_FEATURE="/home/ubuntu/761project/submit/tmpDir/tmpFeat/developmentSetFeatures";
	//nonBal
	public static final String TRAINING_FEATURE_NGRAM="/home/ubuntu/761project/data/UBalTrain";
	public static final String TRAINING_FEATURE_NGRAM_MERGE="/home/ubuntu/761project/submit/tmpDir/tmpFeat/train_merge";
	
	public static final String TRAINING_PREDICT="/home/ubuntu/761project/submit/training_predict";
	
	
	public static final String DEVELOPMENT_FEATURE_NGRAM="/home/ubuntu/761project/data/UBalDev";
	public static final String DEVELOPMENT_FEATURE_NGRAM_MERGE="/home/ubuntu/761project/submit/tmpDir/tmpFeat/development_merge";
	public static final String DEVELOPMENT_PREDICT="/home/ubuntu/761project/submit/dev_predict";
	
	public static final String TESTING_FEATURE_TRIGGERSNETWORK_SEN="/home/ubuntu/761project/submit/tmpDir/tmpFeat/testingSetFeatures_triggerNetwork_sen";
	public static final String TESTING_FEATURE_SELFTRIGGER="/home/ubuntu/761project/submit/tmpDir/tmpFeat/testingSetFeatures_selfTrigger";
	public static final String TESTING_FEATURE="/home/ubuntu/761project/submit/tmpDir/tmpFeat/testingSetFeatures";
	public static final String TESTING_FEATURE_NGRAM="/home/ubuntu/761project/submit/tmpDir/tmpFeat/nonBalTest";
	public static final String TESTING_FEATURE_NGRAM_MERGE="/home/ubuntu/761project/submit/tmpDir/tmpFeat/test_merge";
	
	public static final String TESTING_PREDICT_INPUT="dummy3";
	public static final String TESTING_PREDICT="/home/ubuntu/761project/submit/tmpDir/tmpFeat/testingSetFeatures.predict";
}
