#!/bin/bash

tmpPath=./tmpDir
featPath=./tmpDir/tmpFeat
trFeatPath=${featPath}/testingSetFeatures
nGramOutPath=${featPath}/nonBalTest
testFile=${1}
outFile=${2}


echo "initing dir..."
rm -rf ${tmpPath}/*
mkdir ${featPath}
mkdir ${trFeatPath}
mkdir ${nGramOutPath}


echo "generating nGram features..."
#convert sentence of documents into a single line
docTmp=${tmpPath}/tmpCMUDoc.txt
python ./genLMFeatCMU.py ${testFile} ${docTmp}
#generating nGramFeatureFile output to tmpPath
#./runTest.sh ${docTmp} 3 ${nGramOutPath}
#./runTest.sh ${docTmp} 4 ${featPath}
#./runTest.sh ${docTmp} 5 ${featPath}
#./runTest.sh ${docTmp} 6 ${featPath}
#./runTest.sh ${docTmp} 7 ${featPath}

echo "generating typeToken features..."
python ./featureExtract.py ${testFile} ${nGramOutPath}/typeToken.txt

echo "generating trigger network features..."
cd ~/761project/submit/ruil/761project/
javac -d bin @source.txt
cd bin
java -Xmx6g -cp .:bin:**/*.class ruil.feature.FeatureExtraction ${testFile}

echo "training SVM...."


echo "generating result..."

