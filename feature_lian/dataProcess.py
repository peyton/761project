import sys
import re
from nltk.stem.lancaster import LancasterStemmer
import numpy as np
import nltk

def getLabel(fileName):
    labels = []
    with open(fileName) as f1:
        for line in f1:
            line = line.strip()
            labels.append(int(line))
    return labels

# return an array of docs with
def getDocuments(fileName):
    docs = []
    with open(fileName) as f1:
        curDoc = []
        for line in f1:
            if line[0] == '~':
                if curDoc:
                    docs.append(curDoc)
                curDoc = []
            else:
                line = line.strip()
                line = line.strip("</s>")
                line = line.strip("<s>")
                #substitute the <UNK> with unknown
                line = re.sub('<UNK>', 'BLABLA', line)
                curDoc.append(line)
        docs.append(curDoc)
    return docs

def getVocab(docArr):
    st = LancasterStemmer()
    vocab = {}
    for doc in docArr:
        for line in doc:
            words = nltk.word_tokenize(line)
            for word in words:
                e = st.stem(word)
                if not e in vocab:
                    vocab[e] = len(vocab)
    return vocab
    
#return a non sparse matrix of doc-vocab id, the value represents the frequency
def getDocMat(docArr, vocab):
    st = LancasterStemmer()
    X = []
    for doc in docArr:
        curDocArr = [0 for i in range(len(vocab))]
        for line in doc:
            words = nltk.word_tokenize(line)
            for word in words:
                e = st.stem(word)
                if not e in vocab:
                    continue
                curDocArr[vocab[e]] += 1
            X.append(curDocArr)
    return np.array(X)

    
# return an array of docs with
def getDocumentsForNGram(fileName):
    docs = []
    with open(fileName) as f1:
        curDoc = []
        for line in f1:
            if line[0] == '~':
                if curDoc:
                    docs.append(" ".join(curDoc))
                curDoc = []
            else:
                line = line.strip()
                line = line[:-4]
                #line = line.strip("<s>")
                #substitute the <UNK> with unknown
                line = re.sub('<UNK>', 'BLABLA', line)
                curDoc.append(line)
        docs.append(" ".join(curDoc))
    return docs



if __name__ == "__main__":
    trainFile = sys.argv[1]
    labelFile = sys.argv[2]
    testFile = sys.argv[3]
    trainOut = "%sForNGram"%trainFile
    testOut = "%sForNGram"%testFile
    docs = getDocumentsForNGram(trainFile)
    fOut = open(trainOut, "w")
    for doc in docs:
        fOut.write("%s\n"%doc)
    fOut.close()
    docs = getDocumentsForNGram(testFile)
    fOut = open(testOut, "w")
    for doc in docs:
        fOut.write("%s\n"%doc)
    fOut.close()
    #label = getLabel(labelFile)
    #docs = getDocuments(trainFile)
    #label = getLabel(labelFile)
    #fOutT = open("docsTrue", 'w')
    #fOutF = open("docsFake", 'w')
    #print "len Docs", len(docs)
    #print "len Label", len(label)
    #for i in range(len(label)):
    #    if label[i] == 0:
    #        fOutF.write("\n".join(docs[i]))
    #        fOutF.write("\n~~~~\n")
    #    else:
    #        fOutT.write("\n".join(docs[i]))
    #        fOutT.write("\n~~~~\n")


