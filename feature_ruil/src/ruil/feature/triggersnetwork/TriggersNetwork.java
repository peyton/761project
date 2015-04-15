package ruil.feature.triggersnetwork;

import java.util.ArrayList;

import ruil.invertedindex.InMemoryInvertedIndex;

public class TriggersNetwork {
	InMemoryInvertedIndex index;
	int L = 5;
	int U = 100;

	public TriggersNetwork() {
		index = InMemoryInvertedIndex.getInstance();
	}

	public double averageWeightOfEdges(ArrayList<String> tokens) {
		double avgWeight = 0;
		for (int i = 0; i < tokens.size(); i++) {
			for (int j = i + 1; j < tokens.size(); j++) {
				String wi = tokens.get(i);
				String wj = tokens.get(j);
				int c_wi = index.getWordCount(wi);
				int c_wj = index.getWordCount(wj);
				int c_wiwj = index.getCoocurrence(wi, wj, L, U);
				long c_total = index.getTotalWordCount();
				double weight = MutualInformation.caculatePMI(c_wi, c_wj,
						c_wiwj, c_total);
				avgWeight += weight;
			}
		}

		return avgWeight / tokens.size();
	}

	public static void main(String[] args) {
		String sentence="THIS IS A. B. C. NEWS NIGHTLINE";
		String[] seg=sentence.split(" ");
		ArrayList<String> tokens=new ArrayList<String>();
		for(int i=0;i<seg.length;i++){
			tokens.add(seg[i]);
		}
		
		TriggersNetwork fT=new TriggersNetwork();
		System.out.println(fT.averageWeightOfEdges(tokens));
	}

}
