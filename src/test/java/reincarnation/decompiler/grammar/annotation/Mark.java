/*
 * Copyright (C) 2023 Nameless Production Committee
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://opensource.org/licenses/mit-license.php
 */
package reincarnation.decompiler.grammar.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Mark {

    int intValue() default 0;

    long longValue() default 0;

    float floatValue() default 0;

    double doubleValue() default 0;

    boolean booleanValue() default false;

    char charValue() default ' ';

    short shortValue() default 0;

    byte byteValue() default 0;

    String stringValue() default "";

    Symbol enumValue() default Symbol.A;

    Class classValue() default Object.class;

    Retention annotationValue() default @Retention(RetentionPolicy.SOURCE);

    int[] arrayIntValue() default {};
}