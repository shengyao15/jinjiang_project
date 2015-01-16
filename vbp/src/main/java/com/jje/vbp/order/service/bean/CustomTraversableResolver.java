package com.jje.vbp.order.service.bean;

import javax.validation.Path;
import javax.validation.Path.Node;
import javax.validation.TraversableResolver;

public class CustomTraversableResolver implements TraversableResolver {

	public boolean isReachable(Object traversableObject, Node traversableProperty, Class<?> rootBeanType,
			Path pathToTraversableObject, java.lang.annotation.ElementType elementType) {
		return true;
	}

	public boolean isCascadable(Object traversableObject, Node traversableProperty, Class<?> rootBeanType,
			Path pathToTraversableObject, java.lang.annotation.ElementType elementType) {
		return true;
	}
}
