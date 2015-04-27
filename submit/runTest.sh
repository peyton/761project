#!/bin/bash
#this bash should run on andrew machine since all the bin file it depends runs under linux file system
trainFile=LM-train-100MW
trainFilePath=../../data/${trainFile}.txt
testFile=${1}
kGram=${2}
outPath=${3}
modelPath=/mnt/
path=~/761project/feature_lian/CMU-Cam-Tool/bin/
tmpPath=./tmpDir/rawFeat

#test on reviews-test data-set
genFeat(){
    testFile=$1
    while read line
    do
        echo "${line}" > ${tmpPath}
        str=$(sudo echo "perplexity -include_unks -text ${tmpPath}" | ${path}/evallm -binary ${modelPath}/${trainFile}_${kGram}.binlm | grep "Entropy")
        lenDoc=$(echo "${line}" | wc -w)
        echo "${str},docLen = ${lenDoc}" >> ${outPath}/cmu${kGram}GramRaw.txt
    done < ${testFile}
}

echo "generating feature for ${1} gram"
genFeat ${testFile}

