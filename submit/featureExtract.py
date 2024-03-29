import sys
import dataProcess as dp
import nltk
from nltk.stem.lancaster import LancasterStemmer
import math
import numpy as np
import lda
import nltk


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

#def getNgramModel(N, trainCorpus):
    

def getLDAFeature(docs, vocab, nTopic, nWord):
    X = dp.getDocMat(docs, vocab)
    topics = trainLDA(X, np.array(sorted(vocab, key=vocab.__getitem__)), nTopic, nWord)
    for topic in topics:
        print topic

def trainLDA(X, vocab, nTopic, nWord):
    model = lda.LDA(n_topics=nTopic, n_iter=1000, random_state=1)
    model.fit(X)
    topic_word = model.topic_word_
    topics = []
    for i, topic_dist in enumerate(topic_word):
        topic_words = vocab[np.argsort(topic_dist)][:-nWord:-1]
        topics.append(topic_words)
	#print('Topic {}: {}'.format(i, ' '.join(topic_words)))
    return topics


if __name__ =="__main__":
    trainFile = sys.argv[1]
    trainOut = sys.argv[2]
    docs = dp.getDocuments(trainFile)
    ############for ratio#########
    try:
        ratio = getTypeTokenRatio(docs)
    except LookupError:
        nltk.download("all")
        ratio = getTypeTokenRatio(docs)
    #output it into a single file
    fOut = open(trainOut, "w")
    for r in ratio:
        fOut.write("%f\n"%r)
    fOut.close()

