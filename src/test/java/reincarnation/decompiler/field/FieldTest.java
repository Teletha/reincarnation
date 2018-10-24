/*
 * Copyright (C) 2016 Nameless Production Committee
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://opensource.org/licenses/mit-license.php
 */
package reincarnation.decompiler.field;

import org.junit.jupiter.api.Test;

import reincarnation.Code;
import reincarnation.CodeVerifier;
import reincarnation.Debuggable;

/**
 * @version 2018/10/23 15:37:11
 */
class FieldTest extends CodeVerifier {

    @Test
    public void IntField() {
        verify(new IntField());
    }

    /**
     * @version 2018/10/23 15:37:59
     */
    private static class IntField implements Code.IntParam {

        private int field = 10;

        @Override
        public int run(int value) {
            return field;
        }
    }

    @Test
    public void IntFieldWithExpresison() {
        verify(new IntFieldWithExpresison());
    }

    /**
     * @version 2018/10/23 15:37:56
     */
    private static class IntFieldWithExpresison implements Code.IntParam {

        private int field = 10;

        @Override
        public int run(int value) {
            return field + value;
        }
    }

    @Test
    public void LongField() {
        verify(new LongField());
    }

    /**
     * @version 2018/10/23 15:38:06
     */
    private static class LongField implements Code.LongParam {

        private long field = 9876543210L;

        @Override
        public long run(long value) {
            return field;
        }
    }

    @Test
    public void FloatField() {
        verify(new FloatField());
    }

    /**
     * @version 2018/10/23 15:38:25
     */
    private static class FloatField implements Code.FloatParam {

        private float field = 3.1415f;

        @Override
        public float run(float value) {
            return field;
        }
    }

    @Test
    public void DoubleField() {
        verify(new DoubleField());
    }

    /**
     * @version 2018/10/23 15:38:32
     */
    private static class DoubleField implements Code.DoubleParam {

        private double field = 3.14159265358979323846264338327950288419716939937510d;

        @Override
        public double run(double value) {
            return field;
        }
    }

    @Test
    public void BooleanField() {
        verify(new BooleanField());
    }

    /**
     * @version 2018/10/23 15:38:41
     */
    private static class BooleanField implements Code.BooleanParam {

        private boolean field = false;

        @Override
        public boolean run(boolean value) {
            return field;
        }
    }

    @Test
    public void Extend() {
        verify(new ExtendChild());
    }

    /**
     * @version 2018/10/23 15:38:52
     */
    private static class ExtendBase {

        public int field = 10;
    }

    /**
     * @version 2018/10/23 15:38:49
     */
    private static class ExtendChild extends ExtendBase implements Code.IntParam {

        @Override
        public int run(int value) {
            return value + field;
        }
    }

    @Test
    public void Override() {
        verify(new OverrideChild());
    }

    /**
     * @version 2018/10/23 15:39:01
     */
    private static class OverrideBase {

        protected int field = 10;
    }

    /**
     * @version 2018/10/23 15:38:58
     */
    private static class OverrideChild extends OverrideBase implements Code.IntParam {

        protected int field = 5;

        @Override
        public int run(int value) {
            return value + this.field;
        }
    }

    @Test
    void accessHidingFieldFromThis() {
        verify(new Parent());
    }

    @Test
    void accessNestedHidingFieldFromThis() {
        verify(new Child());
    }

    @Test
    void accessNestedHidingFieldFromInstance() {
        verify(new Code.Int() {

            @Debuggable
            @Override
            public int run() {
                Child child = new Child();

                return child.hide + ((Parent) child).hide + ((Ancestor) child).hide + child.onlyAncestor;
            }
        });
    }

    /**
     * @version 2018/10/23 15:39:12
     */
    private static class Ancestor {

        public int hide = 10;

        public int ancestorAndChild = 3;

        public int onlyAncestor = 101;
    }

    /**
     * @version 2018/10/23 15:39:09
     */
    private static class Parent extends Ancestor implements Code.IntParam {

        public int hide = 5;

        @Override
        public int run(int hide) {
            return hide + this.hide + super.hide;
        }
    }

    /**
     * @version 2018/10/24 9:15:18
     */
    private static class Child extends Parent {

        public int hide = 1;

        public int ancestorAndChild = 7;

        /**
         * {@inheritDoc}
         */
        @Override
        public int run(int hide) {
            return hide + this.hide + super.hide + ((Ancestor) this).hide + ancestorAndChild + super.ancestorAndChild;
        }
    }

    @Test
    public void Wrapper() {
        verify(new Wrapper());
    }

    /**
     * @version 2018/10/23 15:39:17
     */
    private static class Wrapper implements Code.IntParam {

        private int field = 10;

        @Override
        public int run(int value) {
            setWrapper(value);
            return field;
        }

        private void setWrapper(Integer value) {
            this.field = value;
        }
    }
}
