package org.sqtf.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * If you need to setup any resource prior to testing, you can mark a public
 * instance method with {@code @Before}. This method will execute prior
 * to each test in the test class
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Before {
}
