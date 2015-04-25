import sys
import cPickle
#transform the data cpickle

fileName = sys.argv[1]

with open("%s.txt"%fileName) as f1:
    perp = [] #perplexity
    entr = []
    for line in f1:
        words = line.strip().split(",")
        perp.append(float(words[0].split()[2]))
        entr.append(float(words[1].split()[2]))

dic = {}
dic['Entropy'] = entr
dic['perplexity'] = perp
cPickle.dump(dic, open("%s.cp" % fileName, "wb"))
print entr
print perp

