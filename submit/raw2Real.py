import sys
import cPickle
import os
#transform the data cpickle

def saveFile(dirName, fileName, subDir):
    with open(os.path.join(dirName, fileName)) as f1:
        perp = [] #perplexity
        entr = []
        for line in f1:
            words = line.strip().split(",")
            docLen = float(words[2].split()[2])
            perp.append(float(words[0].split()[2]))
            entr.append(float(words[1].split()[2]))
            
    dic = {}
    dic['EntropyNorm'] = entr
    dic['perplexityNorm'] = perp
    fOut = open(outName, "wb")
    for i in range(len(entr)):
        fOut.write("%f %f\n"%(perp[i], entr[i]))
    fOut.close()

rootdir = sys.argv[1]
outPath = sys.argv[2]

files =  os.listdir(rootdir)
for file in files:
    if os.path.isfile(os.path.join(rootdir, file)):
        try:
            print os.path.join(rootdir, file)
            outName = os.path.join(outPath, file)
            saveFile(rootdir, file, outName)
        except IndexError:
            continue
