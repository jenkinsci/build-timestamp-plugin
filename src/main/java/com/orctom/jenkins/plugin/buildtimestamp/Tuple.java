package com.orctom.jenkins.plugin.buildtimestamp;

import hudson.Extension;
import hudson.model.AbstractDescribableImpl;
import hudson.model.Descriptor;
import org.kohsuke.stapler.DataBoundConstructor;

/**
 * key value tuple
 * Created by hao on 12/16/15.
 */
public class Tuple extends AbstractDescribableImpl<Tuple> {

	private String key;
	private String value;
	private String shiftExpression;

	@DataBoundConstructor
	public Tuple(String key, String value, String shiftExpression) {
		this.key = key;
		this.value = value;
		this.shiftExpression = shiftExpression;
	}

	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}

	public String getShiftExpression() {
		return shiftExpression;
	}

	@Extension
	public static class DescriptorImpl extends Descriptor<Tuple> {
		public String getDisplayName() { return ""; }
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Tuple tuple = (Tuple) o;

		if (!key.equals(tuple.key)) return false;
		if (!value.equals(tuple.value)) return false;
		return shiftExpression.equals(tuple.shiftExpression);
	}

	@Override
	public int hashCode() {
		int result = key.hashCode();
		result = 31 * result + value.hashCode();
		result = 31 * result + shiftExpression.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return "[" + key + " -> " + value + " (" + shiftExpression + ")]";
	}
}
