package metricsTool.codeSmells;

import metricsTool.beans.ClassBean;
import metricsTool.metrics.CKMetrics;

public class ClassDataShouldBePrivateRule {

	public boolean isClassDataShouldBePrivate(ClassBean pClass) {
		
		if(CKMetrics.getNOPA(pClass) > 10)
			return true;
		
		return false;
	}
}
