lmexe=./kenlm/lm/lmplz
data=~/761project/data/LM-train-100MW.txt
order=$1
outPath=~/761project/model/

${lmexe} -o ${order} < ${data} > ${outPath}${1}gram.arpa --skip_symbols
