import sys
import dataProcess as dp
import cPickle as cp

inFile = sys.argv[1]
outFile = sys.argv[2]
featName = sys.argv[3]

#lazy conversion
feats = dp.getFeature(inFile)
cp.dump({featName:feats}, open(outFile, 'wb'))
