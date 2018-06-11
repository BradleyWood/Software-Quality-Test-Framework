package org.sqtf.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *  If you need to destroy any resource after testing, you can mark a public
 *  instance method with {@code @After}. This method will execute after
 *  each test in the test class
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface After {
}
