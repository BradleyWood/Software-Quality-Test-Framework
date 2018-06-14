package org.sqtf.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * If you need to parameterize a test, you can mark your test method with
 * {@code @Parameters} to automatically load data from a csv file.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Parameters {

    /**
     * Each test can be optionally named and easily contain some of the test data.
     * <br>
     *
     * To include the some of the test data in the name, the template string must
     * a string of the following format {@code "$<idx>"}.
     *
     * <br>
     *
     * For example you can annotate your test method as follows
     * {@code @Parameters(source = "test.csv", name="Test $0, $1, $2")}
     *
     * @return The string template for the test name
     */
    String name() default "";

    /**
     *
     * Marks the location of the test data
     *
     * @return The the source of the data
     */
    String source();

}
