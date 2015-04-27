1. Extract all features
2. Run FeatureStandarization
3. Run SVM
 ./svm-scale -l -1 -u 1 -s range1 E:\temp\trainingSetFeatures > E:\temp\trainingSetFeatures.scale
 ./svm-scale -r range1 E:\temp\developmentSetFeatures > E:\temp\developmentSetFeatures.scale
 ./svm-train -b 1 -c 2 -g 2 E:\temp\trainingSetFeatures.scale
 ./svm-predict -b 1 E:\temp\developmentSetFeatures.scale E:\temp\trainingSetFeatures.scale.model E:\temp\developmentSetFeatures.predict


C:\Users\RUILIU.PC.CS.CMU.EDU\Downloads\libsvm-3.20\windows\svm-scale.exe -l -1 -u 1 -s range1 E:\temp\trainingSetFeatures > E:\temp\trainingSetFeatures.scale
C:\Users\RUILIU.PC.CS.CMU.EDU\Downloads\libsvm-3.20\windows\svm-scale.exe -r range1 E:\temp\developmentSetFeatures > E:\temp\developmentSetFeatures.scale
C:\Users\RUILIU.PC.CS.CMU.EDU\Downloads\libsvm-3.20\windows\svm-train.exe -b 1 -c 8.0 -g 0.5 E:\temp\trainingSetFeatures.scale
C:\Users\RUILIU.PC.CS.CMU.EDU\Downloads\libsvm-3.20\windows\svm-predict.exe -b 1 E:\temp\trainingSetFeatures.scale E:\temp\trainingSetFeatures.scale.model E:\temp\trainingSetFeatures.predict
C:\Users\RUILIU.PC.CS.CMU.EDU\Downloads\libsvm-3.20\windows\svm-predict.exe -b 1 E:\temp\developmentSetFeatures.scale E:\temp\trainingSetFeatures.scale.model E:\temp\developmentSetFeatures.predict

4. Run weka