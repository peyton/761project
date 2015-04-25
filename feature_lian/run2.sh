#!/bin/bash
dataPath=../data
featurePath=../features
Bal=${1}
trainFile=trainingSet${1}.dat
testFile=developmentSet.dat
order=5
python genLMFeat.py ${dataPath}/${trainFile} ${featurePath}/${order}Gram${Bal}Train.cp ${order}
python genLMFeat.py ${dataPath}/${testFile} ${featurePath}/${order}Gram${Bal}Test.cp ${order}

order=4
python genLMFeat.py ${dataPath}/${trainFile} ${featurePath}/${order}Gram${Bal}Train.cp ${order}
python genLMFeat.py ${dataPath}/${testFile} ${featurePath}/${order}Gram${Bal}Test.cp ${order}

order=3
python genLMFeat.py ${dataPath}/${trainFile} ${featurePath}/${order}Gram${Bal}Train.cp ${order}
python genLMFeat.py ${dataPath}/${testFile} ${featurePath}/${order}Gram${Bal}Test.cp ${order}
