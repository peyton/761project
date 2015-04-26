#!/bin/python
import sys
import subprocess
from sklearn.cluster import KMeans
import cPickle
import numpy as np
from sklearn.metrics.pairwise import pairwise_distances
import random
import sklearn.svm as svm
import sklearn
import pickle
import gzip


#load data from file, which is in pickle form
def getFeats(fileName):
    dics = cPickle.load(open(fileName, 'rb'))
    arr = []
    for key, val in dics.iteritems():
        print fileName, key, len(val)
        arr.append(val)
    return arr

#train the SVM model and save it to outname
def trainSVM(trainData, label, outName, cValue):
    #svmModel = svm.SVC(C=cValue, probability=True, kernel=sklearn.metrics.pairwise.chi2_kernel)
    svmModel = svm.SVC(C=cValue, probability=True, kernel='rbf', degree=6)
    svmModel.fit(np.array(trainData).T, np.array(label))
    cPickle.dump(svmModel, open(outName, "wb"), cPickle.HIGHEST_PROTOCOL)
    return svmModel

#test SVM, and calculate precison & recall
#verify model using testLabel(true label)
def testSVM(svmModel, testData):
    X = np.array(testData).T
    predLabel = svmModel.predict(X)
    return predLabel

def calPrec(predLabel, realLabel):
    arr = [1 if predLabel[i] == realLabel[i] else 0 for i in range(len(predLabel))]
    return float(sum(arr))/float(len(arr))

def getFeatNames(featureFile):
    featNames = []
    with open(featureFile) as f1:
        for line in f1:
            featNames.append(line.strip())
    return featNames

def getLabel(labelName):
    labels = []
    with open(labelName) as f1:
        for line in f1:
            labels.append(int(line.strip()))
    return labels

featureList = sys.argv[1]
labelTrainName = sys.argv[2]
labelTestName = sys.argv[3]
modelOut = sys.argv[4] #output of trained file
CValue = float(sys.argv[5])

featNames = getFeatNames(featureList)
featTrain = []
featTest = []
trainLabel = getLabel(labelTrainName)
testLabel = getLabel(labelTestName)

for name in featNames:
    featTrain += getFeats("%sTrain.cp"%name)
    featTest += getFeats("%sTest.cp"%name)

#TODO choose kernel
model = trainSVM(featTrain, trainLabel, modelOut, CValue)
pLabel = testSVM(model, featTrain)
print "precision is", calPrec(pLabel, trainLabel)

pLabel = testSVM(model, featTest)
print "precision is", calPrec(pLabel, testLabel)

