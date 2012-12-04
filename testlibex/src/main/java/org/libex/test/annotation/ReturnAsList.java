package org.libex.test.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Indication that a test supplier should return the results as a single
 * PotentialAssigment containing a list of elements.
 * 
 * @author John Butler
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ReturnAsList {

}
