# [LOOK FOR NEW MAINTAINER]

# build-timestamp-plugin
Adding `BUILD_TIMESTAMP` to jenkins env vars,

## Daylight Saving Time
DST is not enabled by default.
If your location uses DST, you can enable it by setting the `timezone`
to the correct `city` such as `America/New_York`.
Set the timezone to corresponding city, such as `America/New_York` to display Daylight Saving Time format when Daylight Saving Time is enabled.

## More vars and formats
Add more var names and formats if you need.

## Optional date/time shift
For additional variables you can define shift (days, hours, minutes) which will be added to build timestamp.
This allows to run build plan with previous day specified as parameter.

Export build timestamps to build env variables.

# Configure
`BUILD_TIMESTAMP` exported by default, and you can add more variables
with different format patterns in Global Configure page.  
![](docs/images/global-config.png)


# Using timestamps in Maven/Gradle/Shell
They are available in Maven build and Gradle build as built-in
properties/variables as well.

Maven:  
As defined property in pom, equals to

``` syntaxhighlighter-pre
System.getProperty('BUILD_TIMESTAMP')
```

Gradle:

``` syntaxhighlighter-pre
System.getenv('BUILD_TIMESTAMP')
```

Shell:

``` syntaxhighlighter-pre
"$BUILD_TIMESTAMP"
```

# Open Issues
[View these issues in Jira](https://issues.jenkins.io/issues/?jql=resolution%20is%20EMPTY%20and%20component%3D21120)

