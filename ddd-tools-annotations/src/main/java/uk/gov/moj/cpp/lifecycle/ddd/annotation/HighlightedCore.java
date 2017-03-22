package uk.gov.moj.cpp.lifecycle.ddd.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;


/**
 * Identifies proposed hihglighted core for use in DDD refactoring / distillation
 */
@Retention(RUNTIME)
@Target({TYPE, FIELD})
public @interface HighlightedCore {
    String value();
}
