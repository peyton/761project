package ruil.training;

public class Predict {

	public double[] val;
	public short pLabel;

	public Predict(double[] val) {
		this.val = val;

		pLabel = 0;
		for (int i = 1; i < val.length; i++) {
			if (val[i] > val[pLabel]) {
				pLabel = (short) i;
			}
		}
	}

}
