package ruil.feature.triggersnetwork;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import ruil.util.StopWords;

public class SelfTriggers {
	
	public static double extract(ArrayList<ArrayList<String>> articles) {
		HashSet<String> set=new HashSet<String>();
		int count=0;
		for(int i=0;i<articles.size();i++){
			set.addAll(articles.get(i));
			count+=articles.get(i).size();
		}
	
		return set.size()*1.0/count;
	}
	
	public static double extract_withoutSW(ArrayList<ArrayList<String>> articles) {
		HashSet<String> set=new HashSet<String>(); 
		int count=1;
		for (int i = 0; i < articles.size(); i++) {
			ArrayList<String> tokens=articles.get(i);
			for(int j=0;j<tokens.size();j++){
				if(!StopWords.contains(tokens.get(j))){
					set.add(tokens.get(j));
					count++;
				}
			}
		}
		
		return (set.size()+1)*1.0/count;
	}
	
	public static double normalizedEntropy(ArrayList<ArrayList<String>> articles) {

		int count = 0;
		HashSet<String> uniqueContentWords = new HashSet<String>();
		HashMap<String, Integer> wordMap = new HashMap<String, Integer>();
		for (int i = 0; i < articles.size(); i++) {
			ArrayList<String> tokens = articles.get(i);
			for (int j = 0; j < tokens.size(); j++) {
				String key = tokens.get(j);
				if (!StopWords.contains(key)) {
					uniqueContentWords.add(key);
					if (wordMap.containsKey(key)) {
						wordMap.put(key, wordMap.get(key) + 1);
					} else {
						wordMap.put(key, 1);
					}
					count++;
				}
			}
		}

		double result = 0;
		Iterator<String> iterator = wordMap.keySet().iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			double p = wordMap.get(key) * 1.0 / count;
			result += -p * Math.log(p);
		}

		if (uniqueContentWords.size() == 0) {
			return 0;
		}
		return result / uniqueContentWords.size();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
