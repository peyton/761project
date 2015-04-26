#!/bin/bash
dataPath=../data
featurePath=../features
Bal=${2}
trainFile=trainingSet${Bal}.dat
testFile=developmentSet.dat
order=${1}
python genLMFeat.py ${dataPath}/${trainFile} ${featurePath}/${order}Gram${Bal}Train.cp ${order}
python genLMFeat.py ${dataPath}/${testFile} ${featurePath}/${order}Gram${Bal}Test.cp ${order}

