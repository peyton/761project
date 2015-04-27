#!/bin/bash

tmpPath=./tmpDir
featPath=./tmpDir/tmpFeat
nGramOutPath=${featPath}/nonBalTest
testFile=${1}
outFile=${2}


echo "initing dir..."
rm -rf ${tmpPath}/*
mkdir ${featPath}
mkdir ${nGramOutPath}

echo "generating nGram features..."
#convert sentence of documents into a single line
docTmp=${tmpPath}/tmpCMUDoc.txt
python ./genLMFeatCMU.py ${testFile} ${docTmp}
#generating nGramFeatureFile output to tmpPath
./runTest.sh ${docTmp} 3 ${featPath}
./runTest.sh ${docTmp} 4 ${featPath}
./runTest.sh ${docTmp} 5 ${featPath}
./runTest.sh ${docTmp} 6 ${featPath}
./runTest.sh ${docTmp} 7 ${featPath}
python raw2Real.py ${featPath} ${nGramOutPath}

echo "generating typeToken features..."
#python ./featureExtract.py ${testFile} ${nGramOutPath}/typeToken.txt

echo "generating trigger network features..."
cd ~/761project/submit/ruil/761project/
javac -d bin @source.txt
cd bin
java -Xmx6g -cp .:bin:**/*.class ruil.feature.FeatureExtraction ${testFile}

echo "classifying using SVM...."
cd ~/761project/submit/
./runSVM.sh

echo "generating result..."
cd ~/761project/submit/ruil/761project/bin
java -cp .:bin:**/*.class ruil.eval.Eval
