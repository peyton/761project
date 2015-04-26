#!/bin/bash
#this bash should run on andrew machine since all the bin file it depends runs under linux file system
trainFile=LM-train-100MW
trainFilePath=../../data/${trainFile}.txt
Bal=${2} #if use balance data enter Bal
testPath=/mnt/intermediateData/cmu${Bal}
fA=${testPath}Test.dat
fB=${testPath}Train.dat
modelPath=/mnt/
outPath=~/761project/features/cmufeat
path=../CMU-Cam-Tool/bin/
kGram=${1} #use k gram
method=-good_turing
#build vocab, part1 and part2
#sudo cat ${trainFilePath} | ${path}/text2wfreq | ${path}/wfreq2vocab -top 20000 > ${outPath}/${trainFile}_${kGram}.vocab
#Generate a k-gram of the training text, based on this vocabulary, convert it into language model
#sudo cat ${trainFilePath} | ${path}/text2idngram -n ${kGram} -vocab ${outPath}/${trainFile}_${kGram}.vocab > ${outPath}/${trainFile}_${kGram}.idngram
#sudo ${path}/idngram2lm ${method} -idngram ${outPath}/${trainFile}_${kGram}.idngram -context context.ccs -n ${kGram} -four_byte_counts -vocab ${outPath}/${trainFile}_${kGram}.vocab -binary ${outPath}/${trainFile}_${kGram}.binlm  -buffer 100000000000 
#test on reviews-test data-set
genFeat(){
    testFile=$1
    post=$2
    while read line
    do
        echo "${line}" > tmp${kGram}
        str=$(sudo echo "perplexity -include_unks -text tmp${kGram}" | ${path}/evallm -binary ${modelPath}/${trainFile}_${kGram}.binlm | grep "Entropy")
        lenDoc=$(echo "${line}" | wc -w)
        echo "${str},docLen = ${lenDoc}" >> ${outPath}/cmu${Bal}${kGram}Gram${post}.txt
    done < ${testFile}
}

genFeat ${fA} Test
genFeat ${fB} Train


