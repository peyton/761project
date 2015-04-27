package ruil.feature.triggersnetwork;

import java.util.ArrayList;
import java.util.HashMap;

import ruil.invertedindex.InMemoryInvertedIndex;
import ruil.invertedindex.SentenceIndex;
import ruil.util.StopWords;

public class TriggersNetwork {
	InMemoryInvertedIndex index;
	int L = 4;
	int U = 15;
	double e=0.0001;

	public TriggersNetwork(InMemoryInvertedIndex index) {
		this.index = index;
	}

	public double averageWeightOfEdges(ArrayList<Integer> counts, ArrayList<String> tokens, HashMap<String, Double> mymap) {
		double avgWeight = 0;
		int count = 0;
		for (int i = 0; i < tokens.size(); i++) {
			String wi = tokens.get(i);
			if (StopWords.contains(wi)) {
				continue;
			}

			int leftH=i-(L-1);
			int leftL=i-(U-1);
			
			for(int j=leftL;j<=leftH;j++){
				if(j<0){
					continue;
				}

				String wj = tokens.get(j);
				if (StopWords.contains(wj)) {
					continue;
				}

				double c_wi = index.getWordCount(wi);
				double c_wj = index.getWordCount(wj);
				double c_wiwj = 0;
				String key=wi+"-"+wj;
				if(mymap.containsKey(key)){
					c_wiwj=mymap.get(key);
				}else{
					c_wiwj=index.countCoocurrence(wi, wj, L, U);
					mymap.put(key, c_wiwj);
				}
				
				long c_total = index.getTotalWordCount();
				
				if(c_wi==0&&c_wj==0){
					c_wi=1;
					c_wj=1;
					c_wiwj=1;
				}else if(c_wi==0){
					c_wiwj=e;
					c_wi=1;
					continue;
				}else if(c_wj==0){
					c_wiwj=e;
					c_wj=1;
					continue;
				}
				
				if (wi.equals(wj)) {
					c_wj = 1;
					c_wiwj = 1;

					double weight = MutualInformation.caculatePMI(c_wi, c_wj,
							c_wiwj, c_total);
					avgWeight += weight;
					count++;
				} else {
					if (c_wiwj == 0) {
						c_wiwj=e;
					}
					double weight = MutualInformation.caculatePMI(c_wi, c_wj,
							c_wiwj, c_total);
					avgWeight += weight;
					count++;
				}
			}

		}

		counts.add(count);
		return avgWeight/tokens.size();
	}

	public static void main(String[] args) {
		String sentence = "THIS IS A. B. C. NEWS NIGHTLINE";
		String[] seg = sentence.split(" ");
		ArrayList<String> tokens = new ArrayList<String>();
		for (int i = 0; i < seg.length; i++) {
			tokens.add(seg[i]);
		}

		TriggersNetwork fT = new TriggersNetwork(
				SentenceIndex.getInstance());
		//System.out.println(fT.averageWeightOfEdges(tokens));
	}

}
