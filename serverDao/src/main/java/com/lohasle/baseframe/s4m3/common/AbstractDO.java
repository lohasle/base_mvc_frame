package com.lohasle.baseframe.s4m3.common;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * 描述对象
 */
public abstract class AbstractDO implements Serializable {
	

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
}
