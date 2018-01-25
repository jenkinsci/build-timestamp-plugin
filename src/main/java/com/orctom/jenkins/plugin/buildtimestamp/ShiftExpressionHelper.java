package com.orctom.jenkins.plugin.buildtimestamp;

import org.apache.commons.lang.StringUtils;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShiftExpressionHelper {
    public static final Pattern shiftExpressionPattern = Pattern.compile("^([+-]) (\\d+) days (\\d+) hours (\\d+) minutes$");

    public static boolean isShiftExpressionValid(String shiftExpression) {
        return shiftExpression == null || shiftExpression == "" || shiftExpressionPattern.matcher(shiftExpression).matches();
    }

    public static Calendar doShift(Calendar timestamp, String shiftExpression) {
        Calendar timestamp2 = (Calendar)timestamp.clone();
        if(!StringUtils.isEmpty(shiftExpression)) {
            Matcher m = shiftExpressionPattern.matcher(shiftExpression);
            if(m.matches()) {
                int sign = "-".equals(m.group(1)) ? -1 : 1;
                int days = Integer.valueOf(m.group(2)) * sign;
                int hours = Integer.valueOf(m.group(3)) * sign;
                int minutes = Integer.valueOf(m.group(4)) * sign;

                timestamp2.add(Calendar.DAY_OF_MONTH, days);
                timestamp2.add(Calendar.HOUR_OF_DAY, hours);
                timestamp2.add(Calendar.MINUTE, minutes);
            } else {

            }
        }

        return timestamp2;
    }
}
