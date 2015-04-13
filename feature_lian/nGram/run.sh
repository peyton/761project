#!/bin/bash
trainFile=LM-100MW-Tool
trainFilePath=../intermediateData/${trainFile}.txt
testfile=developmentSet.dat
testfilePath=../data/${testfile}
path=../CMU-Cam-Tool/
kGram=${1} #use k gram
#build vocab, part1 and part2
#cat ${trainFilePath} | ${path}/text2wfreq | ${path}/wfreq2vocab -top 10000 > ${trainFile}.vocab
#Generate a k-gram of the training text, based on this vocabulary, convert it into language model
#cat ${trainFilePath} | ${path}/text2idngram -n ${kGram} -vocab ${trainFile}.vocab > ${trainFile}.idngram
#${path}/idngram2lm -vocab ${trainFile}.vocab -idngram ${trainFile}.idngram -context context.ccs -binary ${trainFile}.lm -arpa ${trainFile}.arpa
cat ${trainFilePath} | ${path}/text2idngram -n ${kGram} -vocab ${trainFile}.vocab | ${path}/idngram2lm -vocab ${trainFile}.vocab -idngram - -context context.ccs -binary ${trainFile}.lm -arpa ${trainFile}.arpa
#test on reviews-test data-set
#echo "${path}/perplexity -probs ${testfile}.probs -text ${testfilePath}" | ${path}/evallm -arpa ${trainFile}.arpa -context context.ccs
