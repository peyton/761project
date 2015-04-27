import sys
import subprocess
import dataProcess as dp
import cPickle as cp

def writeFile(inFile, targetFile):
    fTarget = open(targetFile, "wb")
    docs = dp.getDocuments(inFile)
    for doc in docs:
        fTarget.write("%s\n" % " ".join(doc))
    fTarget.close()

inFile = sys.argv[1]
outFile = sys.argv[2]

writeFile(inFile, outFile)
