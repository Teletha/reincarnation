/*
 * Copyright (C) 2024 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.decompiler.primitives;

import reincarnation.CodeVerifier;
import reincarnation.CompilableTest;
import reincarnation.TestCode;
import reincarnation.TestCode.Int;

class IntTest extends CodeVerifier {

    @CompilableTest
    void zero() {
        verify(new Int() {

            @Override
            public int run() {
                return 0;
            }
        });
    }

    @CompilableTest
    void one() {
        verify(new Int() {

            @Override
            public int run() {
                return 1;
            }
        });
    }

    @CompilableTest
    void two() {
        verify(new Int() {

            @Override
            public int run() {
                return 2;
            }
        });
    }

    @CompilableTest
    void three() {
        verify(new Int() {

            @Override
            public int run() {
                return 3;
            }
        });
    }

    @CompilableTest
    void four() {
        verify(new Int() {

            @Override
            public int run() {
                return 4;
            }
        });
    }

    @CompilableTest
    void five() {
        verify(new Int() {

            @Override
            public int run() {
                return 5;
            }
        });
    }

    @CompilableTest
    void six() {
        verify(new Int() {

            @Override
            public int run() {
                return 6;
            }
        });
    }

    @CompilableTest
    void seven() {
        verify(new Int() {

            @Override
            public int run() {
                return 7;
            }
        });
    }

    @CompilableTest
    void minusOne() {
        verify(new Int() {

            @Override
            public int run() {
                return -1;
            }
        });
    }

    @CompilableTest
    void minusTwo() {
        verify(new Int() {

            @Override
            public int run() {
                return -2;
            }
        });
    }

    @CompilableTest
    void minusThree() {
        verify(new Int() {

            @Override
            public int run() {
                return -3;
            }
        });
    }

    @CompilableTest
    void max() {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                return Integer.MAX_VALUE;
            }
        });
    }

    @CompilableTest
    void min() {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                return Integer.MIN_VALUE;
            }
        });
    }

    @CompilableTest
    void add() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value + 1;
            }
        });
    }

    @CompilableTest
    void addAssignable() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value += 2;
            }
        });
    }

    @CompilableTest
    void addAssignableOnParameter() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value(value += 2);
            }

            private int value(int value) {
                return value;
            }
        });
    }

    @CompilableTest
    void subtrrun() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value - 1;
            }
        });
    }

    @CompilableTest
    void subtractAssignable() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value -= 2;
            }
        });
    }

    @CompilableTest
    void subtractAssignableOnParameter() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value(value -= 2);
            }

            private int value(int value) {
                return value;
            }
        });
    }

    @CompilableTest
    void multiply() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value * 2;
            }
        });
    }

    @CompilableTest
    void multiplyAssignable() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value *= 2;
            }
        });
    }

    @CompilableTest
    void multipleAssignableOnParameter() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value(value *= 20000);
            }

            private int value(int value) {
                return value;
            }
        });
    }

    @CompilableTest
    void divide() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value / 2;
            }
        });
    }

    @CompilableTest
    void divideAssignable() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value /= 2;
            }
        });
    }

    @CompilableTest
    void divideAssignableOnParameter() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value(value /= 2);
            }

            private int value(int value) {
                return value;
            }
        });
    }

    @CompilableTest
    void modulo() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value % 2;
            }
        });
    }

    @CompilableTest
    void moduloAssignable() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value %= 2;
            }
        });
    }

    @CompilableTest
    void moduloAssignableOnParameter() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value(value %= 2);
            }

            private int value(int value) {
                return value;
            }
        });
    }

    @CompilableTest
    void bitFlag() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(int value) {
                return (value & 1) == 0;
            }
        });
    }

    @CompilableTest
    void bitAnd() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value & 0x010101;
            }
        });
    }

    @CompilableTest
    void bitAndAssignable() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value &= 0x010101;
            }
        });
    }

    @CompilableTest
    void bitAndAssignableOnParameter() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value(value &= 0x010101);
            }

            private int value(int value) {
                return value;
            }
        });
    }

    @CompilableTest
    void bitOr() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value | 0x010101;
            }
        });
    }

    @CompilableTest
    void bitOrAssignable() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value |= 0x010101;
            }
        });
    }

    @CompilableTest
    void bitOrAssignableOnParameter() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value(value |= 0x010101);
            }

            private int value(int value) {
                return value;
            }
        });
    }

    @CompilableTest
    void bitXor() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value ^ 0x010101;
            }
        });
    }

    @CompilableTest
    void bitXorAssignable() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value ^= 0x010101;
            }
        });
    }

    @CompilableTest
    void bitXorAssignableOnParameter() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value(value ^= 0x010101);
            }

            private int value(int value) {
                return value;
            }
        });
    }

    @CompilableTest
    void bitNot() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return ~value;
            }
        });
    }

    @CompilableTest
    void shiftLeft() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value << 1;
            }
        });
    }

    @CompilableTest
    void shiftLeftAssignable() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value <<= 1;
            }
        });
    }

    @CompilableTest
    void shiftLeftAssignableOnParameter() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value(value <<= 1);
            }

            private int value(int value) {
                return value;
            }
        });
    }

    @CompilableTest
    void shiftRight() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value >> 1;
            }
        });
    }

    @CompilableTest
    void shiftRightAssignable() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value >>= 1;
            }
        });
    }

    @CompilableTest
    void shiftRightAssignableOnParameter() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value(value >>= 1);
            }

            private int value(int value) {
                return value;
            }
        });
    }

    @CompilableTest
    void unsignedShiftRight() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value >>> 1;
            }
        });
    }

    @CompilableTest
    void unsignedShiftRightAssignable() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value >>>= 1;
            }
        });
    }

    @CompilableTest
    void unsignedShiftRightAssignableOnParameter() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value(value >>>= 1);
            }

            private int value(int value) {
                return value;
            }
        });
    }

    @CompilableTest
    void equal() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(int value) {
                return value == 0;
            }
        });
    }

    @CompilableTest
    void notEqual() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(int value) {
                return value != 0;
            }
        });
    }

    @CompilableTest
    void less() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(int value) {
                return value < 1;
            }
        });
    }

    @CompilableTest
    void lessEqual() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(int value) {
                return value <= 1;
            }
        });
    }

    @CompilableTest
    void greater() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(int value) {
                return value > 1;
            }
        });
    }

    @CompilableTest
    void greaterEqual() {
        verify(new TestCode.IntParamBoolean() {

            @Override
            public boolean run(int value) {
                return value >= 1;
            }
        });
    }

    @CompilableTest
    void postIncrement() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value++;
            }
        });
    }

    @CompilableTest
    void postIncrementValue() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                int next = value++;
                return value + next;
            }
        });
    }

    @CompilableTest
    void postIncrementLike() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value + 1;
            }
        });
    }

    @CompilableTest
    void postIncrementOnParameter() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value(value++);
            }

            private int value(int value) {
                return value;
            }
        });
    }

    @CompilableTest
    void preIncrement() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return ++value;
            }
        });
    }

    @CompilableTest
    void preIncrementInStatement() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return 2 * ++value;
            }
        });
    }

    @CompilableTest
    void preIncrementOnParameter() {
        verify(new TestCode.IntParam() {

            @Override
            public int run(int value) {
                return value(++value);
            }

            private int value(int value) {
                return value;
            }
        });
    }

    @CompilableTest
    void incrementStatiFieldInFieldAccess() {
        verify(new IncrementStaticField());
    }

    /**
     * @version 2018/10/05 0:16:26
     */
    private static class IncrementStaticField implements TestCode.Int {

        private static int index = 1;

        private static int count = 2;

        @Override
        public int run() {
            index = count++;
            return count + index * 10;
        }
    }

    @CompilableTest
    void decrementStatiFieldInFieldAccess() {
        verify(new DecrementStaticField());
    }

    /**
     * @version 2018/10/05 0:16:12
     */
    private static class DecrementStaticField implements TestCode.Int {

        private static int index = 1;

        private static int count = 2;

        @Override
        public int run() {
            index = count--;

            return count + index * 10;
        }
    }

    @CompilableTest
    void preincrementStatiFieldInFieldAccess() {
        verify(new PreincrementStaticField());
    }

    /**
     * @version 2018/10/05 0:16:08
     */
    private static class PreincrementStaticField implements TestCode.Int {

        private static int index = 1;

        private static int count = 2;

        @Override
        public int run() {
            index = ++count;

            return count + index * 10;
        }
    }

    @CompilableTest
    void predecrementStatiFieldInFieldAccess() {
        verify(new PredecrementStaticField());
    }

    /**
     * @version 2018/10/05 0:16:17
     */
    private static class PredecrementStaticField implements TestCode.Int {

        private static int index = 1;

        private static int count = 2;

        @Override
        public int run() {
            index = --count;

            return count + index * 10;
        }
    }

    @CompilableTest
    void classEquality() {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                return int.class == int.class;
            }
        });
    }

    @CompilableTest
    void arrayClassEquality() {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                int[] array = {};
                return int[].class == array.getClass();
            }
        });
    }
}