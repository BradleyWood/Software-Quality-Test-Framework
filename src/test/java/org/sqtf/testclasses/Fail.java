package org.sqtf.testclasses;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Marks that we expect a sqtf test to fail
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Fail {
}
