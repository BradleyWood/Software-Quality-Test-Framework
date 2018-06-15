# Software-Quality-Test-Framework

A lightweight software testing framework that can be
integrated with maven projects.
<br></br>

## How to define a test

Tests must be publicly accessible non-static methods with no arguments.
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

## Running from the command-line

The first argument should always be a relative path to the test class root folder.
The next argument is optional and denotes the output directory for the detailed
test reports.

To view the results graphically run with
```
-display
```

## Parameterized tests

Parameterized tests can be easily implemented and accept test data from various
sources including methods and csv files. The test data is automatically converted
to match the parameter types of the test. Data that cannot be converted to match
the parameter types of the test will result in test failure.


#### Use test data from a CSV File

```java
@Test
@Parameters(source = "testData/add_csv_data.csv")
public void parameterizedAdd(int a, int b, int expected) {
    Assert.assertEquals(expected, a + b);
}
```

#### Use test data from local static or instance methods

```java
@Test
@Parameters(source = "dataGenerator")
public void parameterizedAdd(int a, int b, int expected) {
    Assert.assertEquals(expected, a + b);
}

public Collection methodSource() {
    List<Object[]> lst = new ArrayList<>();
    lst.add(Arrays.asList(0, 0, 0));
    lst.add(Arrays.asList(10, 20, 30));
    lst.add(Arrays.asList(100, 200, 300));
    return lst;
}
```


## Maven integration


```xml
<dependency>
    <groupId>com.github.bradleywood</groupId>
    <artifactId>sqtf-core</artifactId>
    <version>1.0</version>
</dependency>
```

Configure the surefire plugin

```xml
<plugin>
    <artifactId>maven-surefire-plugin</artifactId>
    <version>2.20.1</version>
    <dependencies>
        <dependency>
            <groupId>com.github.bradleywood</groupId>
            <artifactId>sqtf-provider</artifactId>
            <version>1.0</version>
        </dependency>
    </dependencies>
</plugin>
```
