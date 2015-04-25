import os
import cPickle
rootdir = '.'

def cp2Txt(fileName, subDir, source):
    dic = cPickle.load(open(source, "rb"))
    arr = []
    outFile = open(os.path.join(subDir, "%s.txt"%fileName), "wb")
    for key,val in dic.iteritems():
        arr.append(val)
    for i in range(len(arr[0])):
        feat = [str(arr[j][i]) for j in range(len(arr))]
        outFile.write("%s\n"%(" ".join(feat)))
    outFile.close()  

for subdir, dirs, files in os.walk(rootdir):
    for file in files:
        parts = os.path.splitext(file)
        name = parts[0]
        extension = parts[1]
        if extension == ".cp":
            print os.path.join(subdir, file)
            cp2Txt(name, subdir, os.path.join(subdir, file))
