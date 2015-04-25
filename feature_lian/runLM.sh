lmPath=./kenlm/bin/lmplz
binPath=./kenlm/bin/build_binary
trainData=../data/LM-train-100MW.txt
modelOut=/mnt/expSpace
order=${1}

sudo ${execPath} -o ${order} -S 80% -T /mnt/ < ${trainData} > ${modelOut}/${order}gram.arpa --skip_symbols

sudo ${binPath} ${modelOut}/${order}gram.arpa ${modelOut}/${order}gram.bin
