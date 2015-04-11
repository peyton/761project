import sys
import re
from nltk.stem.lancaster import LancasterStemmer

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


if __name__ == "__main__":
    trainFile = sys.argv[1]
    labelFile = sys.argv[2]
    testFile = sys.argv[3]
    docs = getDocuments(trainFile)
    label = getLabel(labelFile)
    fOutT = open("docsTrue", 'w')
    fOutF = open("docsFake", 'w')
    print "len Docs", len(docs)
    print "len Label", len(label)
    for i in range(len(label)):
        if label[i] == 0:
            fOutF.write("\n".join(docs[i]))
            fOutF.write("\n~~~~\n")
        else:
            fOutT.write("\n".join(docs[i]))
            fOutT.write("\n~~~~\n")

