import sys
import subprocess
import dataProcess as dp
import cPickle as cp

def genFeat(inFile, order, targetFile):
    feats = {'perpIncOov':[], 'perpExcOov':[]}
    docs = dp.getDocuments(inFile)
    for doc in docs:
        proc = subprocess.Popen(["./genLMFeat.sh", str(order)], stdin=subprocess.PIPE, stdout=subprocess.PIPE)
        proc.stdin.write("\n".join(doc))
        proc.stdin.close()
        lines = proc.stdout.read()#.split("\n")[-5:-3]
        lines = lines.split('\n')[-5:-3]
        pIncOov = lines[0].split('\t')[-1]
        pExcOov = lines[1].split('\t')[-1]
        print "p including oovs", pIncOov
        print "p excluding oovs", pExcOov
        print "----"
        feats['perpIncOov'].append(pIncOov)
        feats['perpExcOov'].append(pExcOov)
    cp.dump(feats, open(targetFile, "wb"))


inFile = sys.argv[1]
order = 5
outFile = sys.argv[2]

genFeat(inFile, order, outFile)
