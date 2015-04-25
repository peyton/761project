#!/bin/bash
testPath=./kenlm/bin/query
modelOut=/mnt/expSpace
#filePath=${1}
order=${1}
#outPath=${3}


#sudo ${testPath} ${modelOut}/${order}gram.bin < ${filePath} 
sudo ${testPath} ${modelOut}/${order}gram.bin #< ${filePath} > ${outPath}
