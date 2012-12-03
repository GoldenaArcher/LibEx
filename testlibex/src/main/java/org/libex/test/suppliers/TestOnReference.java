package org.libex.test.suppliers;

import static com.google.common.collect.Lists.newArrayList;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.util.List;

import junit.framework.Assert;

import org.junit.experimental.theories.ParameterSignature;
import org.junit.experimental.theories.ParameterSupplier;
import org.junit.experimental.theories.ParametersSuppliedBy;
import org.junit.experimental.theories.PotentialAssignment;
import org.libex.test.suppliers.TestOnReference.TestOnReferenceSupplier;

import com.google.common.base.Strings;

/**
 * @author John Butler
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@ParametersSuppliedBy(TestOnReferenceSupplier.class)
public @interface TestOnReference {
	/**
	 * The class from which to retrieve the fields. Generally this is the test
	 * class.
	 */
	Class<?> type();

	/**
	 * Optional. The name of a static field in {@code type} that is an instance
	 * of {@code type}. This will be used to retrieve instance fields for
	 * testing. This may remain unset if all fields to be tested are static.
	 */
	String instance() default "";

	/**
	 * The names of the static or instance fields to be used for testing. If all
	 * names are of static fields {@code instance} does not need to be set.
	 */
	String[] fields();

	public static class TestOnReferenceSupplier extends ParameterSupplier {

		@Override
		public List<PotentialAssignment> getValueSources(ParameterSignature sig) {
			List<PotentialAssignment> result = newArrayList();

			try {
				TestOnReference testOn = sig.getAnnotation(TestOnReference.class);
				if (testOn != null) {
					Class<?> type = testOn.type();
					Object instance = null;

					if (!Strings.isNullOrEmpty(testOn.instance())) {
						Field field = type.getDeclaredField(testOn.instance());
						field.setAccessible(true);

						instance = field.get(null);
					}

					for (String name : testOn.fields()) {
						Field field = type.getDeclaredField(name);
						field.setAccessible(true);

						result.add(PotentialAssignment.forValue(name, field.get(instance)));
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				Assert.fail("Unable to retrieve field");
			}

			return result;
		}
	}
}
