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
            perp.append(float(words[0].split()[2]))
            entr.append(float(words[1].split()[2]))

    dic = {}
    dic['Entropy'] = entr
    dic['perplexity'] = perp
    cPickle.dump(dic, open(os.path.join(dirName, "%s.cp" % outName), "wb"))
    fOut = open(os.path.join(dirName, "%s.txt"%outName), "wb")
    for i in range(len(entr)):
        fOut.write("%f %f\n"%(perp[i], entr[i]))
    fOut.close()

rootdir = '.'

for subdir, dirs, files in os.walk(rootdir):
    for file in files:
        try:
            print file
            outName = file[-9] + "GramCMU"
            outName += "Train" if "train" in file else "test"
            saveFile(subdir, file, outName)
        except IndexError:
            continue
