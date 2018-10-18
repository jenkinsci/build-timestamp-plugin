package com.orctom.jenkins.plugin.buildtimestamp;

import com.orctom.jenkins.plugin.buildtimestamp.BuildTimestampWrapper.DescriptorImpl;
import hudson.EnvVars;
import hudson.Extension;
import hudson.model.EnvironmentContributor;
import hudson.model.Run;
import hudson.model.TaskListener;
import jenkins.model.Jenkins;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.orctom.jenkins.plugin.buildtimestamp.BuildTimestampPlugin.DEFAULT_PROPERTY;

/**
 * BuildTimestamp Environment Contributor
 * Created by hao on 12/16/15.
 */
@Extension
public class BuildTimestampEnvironmentContributor extends EnvironmentContributor {

	@Override
	public void buildEnvironmentFor(Run run, EnvVars envVars, TaskListener listener)
			throws IOException, InterruptedException {
		if (null == envVars.get(DEFAULT_PROPERTY)) {
			try {
				Map<String, String> timestampProperties = buildTimestamp(run.getTimestamp());
				envVars.putAll(timestampProperties);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private Map<String, String> buildTimestamp(Calendar timestamp) {
		Map<String, String> timestampProperties = new HashMap<String, String>();
		DescriptorImpl descriptor = getDescriptorImpl();
		if (descriptor.isEnableBuildTimestamp()) {
			TimeZone timeZone = TimeZone.getTimeZone(descriptor.getTimezone());
			String pattern = descriptor.getPattern();

			setTimestamp(timestampProperties, DEFAULT_PROPERTY, format(timestamp, timeZone, pattern, ""));

			Set<BuildTimestampExtraProperty> extraProperties = descriptor.getExtraProperties();
			for (BuildTimestampExtraProperty property : extraProperties) {
				setTimestamp(timestampProperties, property.getKey(), format(timestamp, timeZone, property.getValue(), property.getShiftExpression()));
			}
		}
		return timestampProperties;
	}

	private void setTimestamp(Map<String, String> timestampProperties, String key, String value) {
		timestampProperties.put(key, value);
		System.setProperty(key, value);
	}

	private String format(Calendar timestamp, TimeZone timeZone, String pattern, String shiftExpression) {
		Calendar timestamp2 = ShiftExpressionHelper.doShift(timestamp, shiftExpression);

		SimpleDateFormat format = new SimpleDateFormat(pattern);
		format.setTimeZone(timeZone);
		return format.format(timestamp2.getTime());
	}

	public DescriptorImpl getDescriptorImpl() {
		return (DescriptorImpl) Jenkins.getInstance().getDescriptorOrDie(BuildTimestampWrapper.class);
	}
}
