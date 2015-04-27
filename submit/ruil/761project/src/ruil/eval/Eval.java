package ruil.eval;

import java.io.FileNotFoundException;
import java.util.List;

import ruil.util.Config;
import ruil.util.FileUtil;

public class Eval {

	public static void main(String[] args) throws FileNotFoundException {
		List<String> line = FileUtil
				.readFile(Config.TESTING_PREDICT);
		double sum = 0;
		int count=0;
		for (int i = 1; i < line.size(); i++) {
			if(line.get(i).equals("")){
				continue;
			}
			String[] seg = line.get(i).split(" ");
			System.out.println(seg[2]+" "+seg[1]+" "+seg[0]);
			double cur = Double.parseDouble(seg[1]);
			sum += log2(cur);
			count++;
		}
		
		System.out.println("Perplexity: "+sum/count);
	}

	public static double log2(double n) {
		return (Math.log(n) / Math.log(2));
	}
}
