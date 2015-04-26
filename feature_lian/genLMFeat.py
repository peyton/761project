import sys
import subprocess
import dataProcess as dp
import cPickle as cp
import math

def genFeat(inFile, order, targetFile):
    feats = {'perpIncOov':[], 'perpExcOov':[], 'entroInc':[], 'entroExc':[], 'normalInc':[], 'normalExc':[]}
    #feats = {'normalInc':[], 'normalExc':[]}
    docs = dp.getDocuments(inFile)
    for doc in docs:
        proc = subprocess.Popen(["./genLMFeat.sh", str(order)], stdin=subprocess.PIPE, stdout=subprocess.PIPE)
        docLength = float(len(" ".join(doc).split(" ")))
        proc.stdin.write("\n".join(doc))
        proc.stdin.close()
        lines = proc.stdout.read()#.split("\n")[-5:-3]
        lines = lines.split('\n')[-5:-3]
        pIncOov = float(lines[0].split('\t')[-1])
        pExcOov = float(lines[1].split('\t')[-1])
        print doc
        print "p including oovs", pIncOov
        print "p excluding oovs", pExcOov
        print "nDoc", docLength
        print "----"
        feats['perpIncOov'].append(pIncOov)
        feats['perpExcOov'].append(pExcOov)
        eInc = math.log(pIncOov, 2)
        eExc = math.log(pExcOov, 2)
        feats['entroInc'].append(eInc)
        feats['entroExc'].append(eExc)
        feats['normalInc'].append(eInc/docLength)
        feats['normalExc'].append(eExc/docLength)
    cp.dump(feats, open(targetFile, "wb"))


inFile = sys.argv[1]
outFile = sys.argv[2]
order = sys.argv[3]

genFeat(inFile, order, outFile)
