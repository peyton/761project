import sys
import dataProcess as dp
import nltk
from nltk.stem.lancaster import LancasterStemmer
import math
import numpy as np
import render as rd

#get type-token ratio
def getTypeTokenRatio(docArr):
    st = LancasterStemmer()
    ratio = []
    for doc in docArr:
        tokenCount = 0
        dic = set()
        for line in doc:
            words = nltk.word_tokenize(line)
            for word in words:
                dic.add(st.stem(word))
                tokenCount += 1
        r = float(len(dic))/float(tokenCount)/math.sqrt(tokenCount)*10
        ratio.append(r)
        #print r
    return ratio


def getNgramModel(N, trainCorpus):


if __name__ =="__main__":
    trainFile = sys.argv[1]
    labelFile = sys.argv[2]
    testFile = sys.argv[3]
    docs = dp.getDocuments(trainFile)
    label = dp.getLabel(labelFile)
    ratio = getTypeTokenRatio(docs)
    nT = []
    nF = []
    for i in range(len(ratio)):
        if label[i] == 0:
            nF.append(ratio[i])
        else:
            nT.append(ratio[i])
    #hT = np.histogram(nT, bins = 18, range = (0.0,1.0), density = True)
    #hF = np.histogram(nF, bins = 18, range = (0.0,1.0), density = True)
    rd.renderCompareHist(nT, nF, "token-type rate distribution", "propopation", "value", "token-type Distribution")   





