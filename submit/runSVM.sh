#!/bin/bash

featPath=/home/ubuntu/761project/submit/tmpDir/tmpFeat
svmPath=/home/ubuntu/libsvm-3.20/
modelPath=/home/ubuntu/761project/submit/svmModel

#./svm-scale -l -1 -u 1 -s range1 ${featPath}/trainingSetFeaturesBalanced > ${featPath}/trainingSetFeaturesBalanced.scale
${svmPath}/svm-scale -r ./range1 ${featPath}/testingSetFeatures > ${featPath}/testingSetFeatures.scale
#./svm-train -b 1 -c 8.0 -g 0.5 ${featPath}/trainingSetFeaturesBalanced.scale
#./svm-predict -b 1 ${featPath}/trainingSetFeaturesBalanced.scale ${featPath}/trainingSetFeaturesBalanced.scale.model ${featPath}/trainingSetFeaturesBalanced.predict
${svmPath}/svm-predict -b 1 ${featPath}/testingSetFeatures.scale ${modelPath}/trainingSetFeaturesBalanced.scale.model ${featPath}/testingSetFeatures.predict
