package com.orctom.jenkins.plugin.buildtimestamp;

import hudson.Extension;
import hudson.model.AbstractProject;
import hudson.model.Descriptor;
import hudson.tasks.BuildWrapper;
import hudson.tasks.BuildWrapperDescriptor;
import hudson.util.ComboBoxModel;
import hudson.util.FormValidation;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.StaplerRequest;

import java.text.SimpleDateFormat;
import java.util.*;

import static com.orctom.jenkins.plugin.buildtimestamp.BuildTimestampPlugin.DEFAULT_PATTERN;

/**
 * wrapper
 * Created by hao on 12/16/15.
 */
public class BuildTimestampWrapper extends BuildWrapper {

	@Extension
	public static final class DescriptorImpl extends BuildWrapperDescriptor {

		private boolean enableBuildTimestamp = true;
		private String timezone = TimeZone.getDefault().getID();
		private String pattern = DEFAULT_PATTERN;
		private Set<Tuple> extraProperties = new HashSet<Tuple>();

		public DescriptorImpl() {
			load();
		}

		@Override
		public boolean isApplicable(AbstractProject<?, ?> abstractProject) {
			return false;
		}

		/**
		 * This human readable name is used in the configuration screen.
		 */
		public String getDisplayName() {
			return "";
		}

		@Override
		public boolean configure(StaplerRequest req, JSONObject formData) throws Descriptor.FormException {
			JSONObject data = formData.getJSONObject("enableBuildTimestamp");

			if (isNullJSONObject(data)) {
				enableBuildTimestamp = false;
			} else {
				enableBuildTimestamp = true;

				timezone = data.getString("timezone");
				pattern = data.getString("pattern");

				Object extraPropertyValues = data.get("extraProperties");
				if (null != extraPropertyValues) {
					extraProperties = extractExtraProperties(extraPropertyValues);
				} else {
					extraProperties = new HashSet<Tuple>();
				}
			}

			save();
			return super.configure(req, formData);
		}

		private boolean isNullJSONObject(JSONObject data) {
			return null == data || data.isNullObject() || data.isEmpty();
		}

		private Set<Tuple> extractExtraProperties(Object extraPropertyValues) {
			Set<Tuple> properties = new HashSet<Tuple>();
			if (extraPropertyValues instanceof JSONArray) {
				JSONArray array = (JSONArray) extraPropertyValues;
				for (Object item : array) {
					addProperty(properties, item);
				}
			} else if (extraPropertyValues instanceof JSONObject) {
				addProperty(properties, extraPropertyValues);
			}

			return properties;
		}

		private void addProperty(Set<Tuple> properties, Object obj) {
			JSONObject data = (JSONObject) obj;
			String key = data.getString("key");
			String value = data.getString("value");
			String shiftExpression = data.getString("shiftExpression");
			if (isVariableNameValid(key) && isPatternValid(value) && ShiftExpressionHelper.isShiftExpressionValid(shiftExpression)) {
				properties.add(new Tuple(key, value, shiftExpression));
			}
		}

		private boolean isVariableNameValid(String name) {
			return null != name && 0 != name.trim().length() && !name.matches(".*\\W.*");
		}

		private boolean isPatternValid(String pattern) {
			if (null == pattern || 0 == pattern.trim().length()) {
				return false;
			}
			try {
				new SimpleDateFormat(pattern);
				return true;
			} catch (Exception e) {
				return false;
			}
		}

		private String getConfiguredTimezone(String timezoneParam) {
			if (null == timezoneParam || 0 == timezoneParam.trim().length()) {
				return TimeZone.getDefault().getID();
			}

			return TimeZone.getTimeZone(timezoneParam).getID();
		}

		public FormValidation doCheckPattern(@QueryParameter("pattern") String patternParam,
											 @QueryParameter("timezone") String timezoneParam) {
			String configuredTimezone = getConfiguredTimezone(timezoneParam);
			String patternStr = DEFAULT_PATTERN;
			if (null != patternParam && 0 != patternParam.trim().length()) {
				patternStr = patternParam.trim();
			}

			try {
				SimpleDateFormat df = new SimpleDateFormat(patternStr);
				df.setTimeZone(TimeZone.getTimeZone(configuredTimezone));
				return FormValidation.ok("Using timezone: %s; Sample timestamp: %s", configuredTimezone, df.format(new Date()));
			} catch (Exception e) {
				return FormValidation.error("Invalid pattern");
			}
		}

		public FormValidation doCheckKey(@QueryParameter("key") String key) {
			if (isVariableNameValid(key)) {
				return FormValidation.ok();
			}
			return FormValidation.error("Invalid variable name");
		}

		public FormValidation doCheckValue(@QueryParameter("value") String value) {
			if (isPatternValid(value)) {
				return FormValidation.ok();
			}
			return FormValidation.error("Invalid pattern");
		}

		public FormValidation doCheckShiftExpression(@QueryParameter("shiftExpression") String shiftExpression) {
			if (ShiftExpressionHelper.isShiftExpressionValid(shiftExpression)) {
				return FormValidation.ok();
			}
			return FormValidation.error("Invalid time shift expression");
		}

		public ComboBoxModel doFillTimezoneItems() {
			ComboBoxModel items = new ComboBoxModel();
			Collections.addAll(items, TimeZone.getAvailableIDs());
			return items;
		}

		public boolean isEnableBuildTimestamp() {
			return enableBuildTimestamp;
		}

		public String getPattern() {
			return pattern;
		}

		public String getTimezone() {
			return timezone;
		}

		public Set<Tuple> getExtraProperties() {
			return extraProperties;
		}
	}
}
