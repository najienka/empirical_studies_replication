package metricsTool.codeSmells;

import metricsTool.beans.ClassBean;
import metricsTool.metrics.CKMetrics;

public class ComplexClassRule {

	public boolean isComplexClass(ClassBean pClass) {

		if(CKMetrics.getMcCabeMetric(pClass) > 200)
				return true;

		return false;
	}
}