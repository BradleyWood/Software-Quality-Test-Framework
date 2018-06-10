package org.sqtf.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * If you need to setup any resource prior to testing, you can mark a public
 * instance method with {@code @Before}. This method will execute prior
 * to each test in the test class
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Before {
}
