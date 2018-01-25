# build-timestamp-plugin [![Build Status](https://jenkins.ci.cloudbees.com/buildStatus/icon?job=plugins/build-timestamp-plugin)](https://jenkins.ci.cloudbees.com/job/plugins/job/build-timestamp-plugin/)
Adding `BUILD_TIMESTAMP` to jenkins env vars,

See also: https://wiki.jenkins-ci.org/display/JENKINS/Build+Timestamp+Plugin

## Daylight Saving Time
Set the timezone to corresponding city, such as `America/New_York` to display Daylight Saving Time format when Daylight Saving Time is enabled.

## More vars and formats
Add more var names and formats if you need.

## Optional date/time shift
For additional variables you can define shift (days, hours, minutes) which will be added to build timestamp.
This allows to run build plan with previous day specified as parameter.
