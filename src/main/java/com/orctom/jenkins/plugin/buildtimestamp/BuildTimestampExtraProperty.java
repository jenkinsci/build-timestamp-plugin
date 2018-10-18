package com.orctom.jenkins.plugin.buildtimestamp;

import hudson.Extension;
import hudson.model.AbstractDescribableImpl;
import hudson.model.Descriptor;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;

/**
 * key value BuildTimestampExtraProperty
 * Created by hao on 12/16/15.
 */
public class BuildTimestampExtraProperty extends AbstractDescribableImpl<BuildTimestampExtraProperty> {

	private String key;
	private String value;
	private String shiftExpression;

	@DataBoundConstructor
	public BuildTimestampExtraProperty(String key, String value, String shiftExpression) {
		this.key = key;
		this.value = value;
		this.shiftExpression = shiftExpression;
	}

	public String getKey() {
		return key;
	}

	@DataBoundSetter
	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	@DataBoundSetter
	public void setValue(String value) {
		this.value = value;
	}

	public String getShiftExpression() {
		return shiftExpression;
	}

	@DataBoundSetter
	public void setShiftExpression(String shiftExpression) {
		this.shiftExpression = shiftExpression;
	}

	@Extension
	@Symbol("buildTimestampExtraProperties")
	public static class DescriptorImpl extends Descriptor<BuildTimestampExtraProperty> {
		public String getDisplayName() { return ""; }
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		BuildTimestampExtraProperty buildTimestampExtraProperty = (BuildTimestampExtraProperty) o;

		if (!key.equals(buildTimestampExtraProperty.key)) return false;
		if (!value.equals(buildTimestampExtraProperty.value)) return false;
		return shiftExpression.equals(buildTimestampExtraProperty.shiftExpression);
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
