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
            #perp.append(float(words[0].split()[2])/docLen)
            #entr.append(float(words[1].split()[2])/docLen)
            perp.append(float(words[0].split()[2]))
            entr.append(float(words[1].split()[2]))
            
    dic = {}
    dic['EntropyNorm'] = entr
    dic['perplexityNorm'] = perp
    cPickle.dump(dic, open(os.path.join(dirName,"gen", "%s.cp" % outName), "wb"))
    fOut = open(os.path.join(dirName,"gen","%s.txt"%outName), "wb")
    for i in range(len(entr)):
        fOut.write("%f %f\n"%(perp[i], entr[i]))
    fOut.close()

rootdir = '.'

for subdir, dirs, files in os.walk(rootdir):
    for file in files:
        try:
            print file
            outName = os.path.splitext(file)[0]
            #outName = outName[:3] + "N" + outName[3:]
            saveFile(subdir, file, outName)
        except IndexError:
            continue
