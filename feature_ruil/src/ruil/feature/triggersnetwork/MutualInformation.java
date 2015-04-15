package ruil.feature.triggersnetwork;

/*
 * Implementation of point-wise mutual information
 * 1. PMI http://www.lrec-conf.org/proceedings/lrec2006/pdf/242_pdf.pdf
 */
public class MutualInformation {

	/*
	 * m is total number of tokens in corpus C
	 */
	public static double caculatePMI(double c_wi, double c_wj, double c_wiwj,
			double c_total) {
		if(c_wiwj==0){
			return 0;
		}
		
		return Math.log(c_wiwj) + Math.log(c_total) - Math.log(c_wi)
				- Math.log(c_wj);
	}
	
}
