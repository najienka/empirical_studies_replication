package metricsTool.parser;

import java.util.Collection;

import org.eclipse.jdt.core.dom.MethodDeclaration;

import metricsTool.beans.ClassBean;
import metricsTool.beans.MethodBean;

public class InvocationParser {
	
	public static MethodBean parse(String pInvocationName, Collection<MethodDeclaration> pMethods,
			String pBelongingClassName) {
		MethodBean methodBean = new MethodBean();
		methodBean.setName(pInvocationName);

		for(MethodDeclaration methodInClass: pMethods) {
			if(methodInClass.getName().toString().equals(pInvocationName)) {
				methodBean.setBelongingClass(new ClassBean(pBelongingClassName));
				methodBean.setParameters(methodInClass.parameters());
			}
		}
		
		return methodBean;
	}

}
