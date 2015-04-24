execPath=./feature_lian/kenlm/bin/lmplz
trainData=./data/LM-train-100MW.txt
modelOut=./model/
order=${1}

sudo ${execPath} -o ${order} -S 80% -T /mnt/ < ${trainData} > ${modelOut}/${order}gram.arpa --skip_symbols
