/*
 * Copyright (C) 2024 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.decompiler.operator;

import reincarnation.CodeVerifier;
import reincarnation.CrossDecompilerTest;
import reincarnation.TestCode;

class FloatIncrementTest extends CodeVerifier {

    @CrossDecompilerTest
    void sequence() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                value++;
                ++value;
                value++;
                return ++value;
            }
        });
    }

    @CrossDecompilerTest
    void incrementFieldInMethodCall() {
        verify(new TestCode.Float() {

            private float index = 0;

            @Override
            public float run() {
                return call(index++);
            }

            private float call(float value) {
                return index + value * 10;
            }
        });
    }

    @CrossDecompilerTest
    void decrementFieldInMethodCall() {
        verify(new TestCode.Float() {

            private float index = 0;

            @Override
            public float run() {
                return call(index--);
            }

            private float call(float value) {
                return index + value * 10;
            }
        });
    }

    @CrossDecompilerTest
    void preincrementFieldInMethodCall() {
        verify(new TestCode.Float() {

            private float index = 0;

            @Override
            public float run() {
                return call(++index);
            }

            private float call(float value) {
                return index + value * 10;
            }
        });
    }

    @CrossDecompilerTest
    void predecrementFieldInMethodCall() {
        verify(new TestCode.Float() {

            private float index = 0;

            @Override
            public float run() {
                return call(--index);
            }

            private float call(float value) {
                return index + value * 10;
            }
        });
    }

    @CrossDecompilerTest
    void incrementVariableInMethodCall() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                float sum1 = sum(value++, value++, value++);
                float sum2 = sum(++sum1, ++sum1, ++sum1);
                return sum(++sum2, sum2++, ++sum2);
            }

            private float sum(float a, float b, float c) {
                return a + b + c;
            }
        });
    }

    @CrossDecompilerTest
    void decrementVariableInMethodCall() {
        verify(new TestCode.FloatParam() {

            @Override
            public float run(float value) {
                float sum1 = sum(value--, value--, value--);
                float sum2 = sum(--sum1, --sum1, --sum1);
                return sum(--sum2, sum2--, --sum2);
            }

            private float sum(float a, float b, float c) {
                return a + b + c;
            }
        });
    }

    @CrossDecompilerTest
    void incrementFieldInFieldAccess() {
        verify(new TestCode.Float() {

            private float index = 1;

            private float count = 2;

            @Override
            public float run() {
                index = count++;

                return count + index * 10;
            }
        });
    }

    @CrossDecompilerTest
    void decrementFieldInFieldAccess() {
        verify(new TestCode.Float() {

            private float index = 1;

            private float count = 2;

            @Override
            public float run() {
                index = count--;

                return count + index * 10;
            }
        });
    }

    @CrossDecompilerTest
    void preincrementFieldInFieldAccess() {
        verify(new TestCode.Float() {

            private float index = 1;

            private float count = 2;

            @Override
            public float run() {
                index = ++count;

                return count + index * 10;
            }
        });
    }

    @CrossDecompilerTest
    void predecrementFieldInFieldAccess() {
        verify(new TestCode.Float() {

            private float index = 1;

            private float count = 2;

            @Override
            public float run() {
                index = --count;

                return count + index * 10;
            }
        });
    }
}