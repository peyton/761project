#!/bin/bash
file=wsg-1MW
path=../bin/
#build vocab, part1 and part2
#cat ${file}.txt | ${path}/text2wfreq | ${path}/wfreq2vocab -top 10000 > ${file}.vocab
#Generate a 3-gram of the training text, based on this vocabulary, convert it into language model
#cat ${file}.txt | ${path}/text2idngram -n 3 -vocab ${file}.vocab | \
#   ${path}/idngram2lm -vocab ${file}.vocab -idngram - \
#  -binary ${file}.lm -arpa ${file}.arpa
cat ${file}.txt | ${path}/text2idngram -vocab ${file}.vocab > ${file}.idngram
${path}/idngram2lm -idngram ${file}.idngram -vocab ${file}.vocab -arpa ${file}.arpa
#test on reviews-test data-set
#echo "perplexity -text REVIEWS-TEST.txt" | evallm -binary lm
