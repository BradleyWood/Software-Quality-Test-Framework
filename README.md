# Software-Quality-Test-Framework

A lightweight software testing framework that can be
integrated with maven projects.
<br></br>

## How to define a test

Tests must publicly accessible non-static methods with no arguments.
Tests must also be marked with @Test (org.sqtf.annotation.Test) annotation.

<br></br>

```java
@Test(expected = ArithmeticException.class)
public void divideByZeroTest() {
    int a = 5 / 0;
}
```
<br></br>

### @Test annotation

The Test annotation has two optional parameters: expected and timeout.
Expected denotes the expected exception type to be thrown and timeout
is an integer value measured in milliseconds. Tests that fail to complete
within the timeout will be terminated and marked as failure.
<br></br>

## How it works

Tests are dynamically loaded based on the specified test class root.
Reflection is used to find and invoke all test methods.
<br></br>

## Command line arguments

The first argument should always be a relative path to the test class root folder.
The next argument is optional and denotes the output directory for the detailed
test reports.

To view the results graphically run with
```
-display
```

## Maven integration

Currently you must use the maven exec plugin to run tests.

Warning: You will have to create a local repository

```
<repositories>
    <repository>
         <id>repo</id>
         <releases>
             <enabled>true</enabled>
                <checksumPolicy>ignore</checksumPolicy>
         </releases>
         <snapshots>
            <enabled>false</enabled>
         </snapshots>
         <url>file://${project.basedir}/repo</url>
    </repository>
</repositories>
```

```
<dependencies>
    <dependency>
        <groupId>org.sqtf</groupId>
        <artifactId>sqtf</artifactId>
        <version>1.0-SNAPSHOT</version>
    </dependency>
</dependencies>
```

Use the exec plugin to configure the testing process
```
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-surefire-plugin</artifactId>
    <version>2.20.1</version>
    <configuration>
        <skipTests>true</skipTests>
    </configuration>
</plugin>
<plugin>
    <groupId>org.codehaus.mojo</groupId>
    <artifactId>exec-maven-plugin</artifactId>
    <version>1.4.0</version>
    <executions>
        <execution>
            <id>sqtf</id>
            <phase>test</phase>
            <goals>
                <goal>java</goal>
            </goals>
        </execution>
    </executions>
    <configuration>
        <mainClass>org.sqtf.TestRunner</mainClass>
        <arguments>
            <argument>target/test-classes/</argument>
            <argument>target/sqtf-reports/</argument>
        </arguments>
    </configuration>
</plugin>
```

Run the install command to build and see test results. You can
view detailed reports in the /target/sqtf-reports/ folder.
```
mvn install
```
