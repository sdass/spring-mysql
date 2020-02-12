package com.subra.springmysql.exception;

import lombok.Getter;
import lombok.Setter;
import net.bytebuddy.implementation.bind.annotation.Super;

@Getter @Setter
public class ResourceNotFoundException extends RuntimeException {
	private String resourceName;
    private String fieldName;
    private Object fieldValue;
    
	public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
		super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));
		this.resourceName = resourceName; this.fieldName =fieldName; this.fieldValue = fieldValue;
	}
}
