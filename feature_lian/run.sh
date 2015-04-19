#!/bin/bash
#this bash should run on andrew machine since all the bin file it depends runs under linux file system
trainFile=LM-100MW-Tool
trainFilePath=./data/${trainFile}.txt
testfile=${2}  #developmentSet.dat_1
testfilePath=./data/${testfile}
path=../bin/
kGram=${1} #use k gram
method=-witten_bell
#build vocab, part1 and part2
#cat ${trainFilePath} | ${path}/text2wfreq | ${path}/wfreq2vocab -top 10000 > ${trainFile}.vocab
#Generate a k-gram of the training text, based on this vocabulary, convert it into language model
#cat ${trainFilePath} | ${path}/text2idngram -n ${kGram} -vocab ${trainFile}.vocab > ${trainFile}.idngram
#${path}/idngram2lm ${method} -idngram ${trainFile}.idngram -context context.ccs -n ${kGram} -four_byte_counts -vocab ${trainFile}.vocab -arpa ${trainFile}_${kGram}.arpa -cutoffs 4 4 4
#test on reviews-test data-set
while read line
do
    #echo "perplexity -text ${line}" | ${path}/evallm -arpa ${trainFile}.arpa -context context.ccs >> ${testfilePath}_out_${kGram}gram
    echo "${line}" > tmp
    echo "perplexity -text tmp" | ${path}/evallm -arpa ${trainFile}.arpa -context context.ccs | grep "Entropy" >> ${testfilePath}_${1}gram_out
done < ${testfilePath}

